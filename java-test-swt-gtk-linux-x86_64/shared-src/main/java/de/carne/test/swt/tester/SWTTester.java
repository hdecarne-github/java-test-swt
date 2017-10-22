/*
 * Copyright (c) 2017 Holger de Carne and contributors, All Rights Reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package de.carne.test.swt.tester;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import de.carne.util.Exceptions;
import de.carne.util.SystemProperties;
import de.carne.util.Threads;

/**
 * Base class for SWT application tests.
 */
public abstract class SWTTester {

	private static final int STEP_TIMEOUT = SystemProperties.intValue(SWTTester.class.getName() + ".STEP_TIMEOUT", 250);
	private static final int STEP_COUNT_LIMIT = SystemProperties
			.intValue(SWTTester.class.getName() + ".STEP_COUNT_LIMIT", 20);
	private static final int DISPOSE_TIMEOUT = SystemProperties.intValue(SWTTester.class.getName() + ".DISPOSE_TIMEOUT",
			250);

	/**
	 * Start and run the SWT application subject to the test.
	 *
	 * @param args The test command line arguments.
	 * @see Runner#run()
	 */
	protected abstract void runSWTApplication(String[] args);

	/**
	 * Create a {@linkplain Runner} instance for test execution.
	 *
	 * @return The created {@linkplain Runner} instance.
	 */
	protected Runner runner() {
		return runner(new String[0]);
	}

	/**
	 * Create a {@linkplain Runner} instance for test execution.
	 *
	 * @param args The test command line arguments.
	 * @return The created {@linkplain Runner} instance.
	 */
	protected Runner runner(String[] args) {
		return new Runner(args);
	}

	/**
	 * Get the accessor for a specific {@linkplain Shell}.
	 * <p>
	 * If no shell is found for the given name this function signals a test failure.
	 *
	 * @param title The title of the {@linkplain Shell} to get.
	 * @return The accessor for the found object.
	 */
	public ShellAccessor getShell(String title) {
		Display display = Display.findDisplay(Thread.currentThread());
		Shell foundShell = null;

		for (Shell shell : display.getShells()) {
			if (title.equals(shell.getText())) {
				foundShell = shell;
				break;
			}
		}
		return new ShellAccessor(Accessor.notNull(foundShell, "Shell not found: " + title));
	}

	private void runWait(Display display, Runnable runnable) {
		final CountDownLatch latch = new CountDownLatch(1);

		display.asyncExec(() -> {
			try {
				runnable.run();
			} finally {
				latch.countDown();
			}
		});
		try {
			latch.await();
		} catch (InterruptedException e) {
			Exceptions.ignore(e);
			Thread.currentThread().interrupt();
		}
	}

	private <T> T runWait(Display display, Supplier<T> supplier) {
		final CountDownLatch latch = new CountDownLatch(1);
		final AtomicReference<T> resultHolder = new AtomicReference<>();

		display.asyncExec(() -> {
			try {
				resultHolder.set(supplier.get());
			} finally {
				latch.countDown();
			}
		});
		try {
			latch.await();
		} catch (InterruptedException e) {
			Exceptions.ignore(e);
			Thread.currentThread().interrupt();
		}
		return resultHolder.get();
	}

	private int ensureSleepLimit(int sleepCount) {
		if (sleepCount >= STEP_COUNT_LIMIT) {
			throw new IllegalStateException("Sleep/wait limit reached");
		}
		return sleepCount + 1;
	}

	private Display waitReady(Runner runner) {
		Threads.sleep(STEP_TIMEOUT);

		int sleepCount = 1;

		while (Display.findDisplay(runner.displayThread()) == null) {
			Threads.sleep(STEP_TIMEOUT);
			sleepCount = ensureSleepLimit(sleepCount);
		}

		Display display = Display.findDisplay(runner.displayThread());

		Supplier<Boolean> shellReady = () -> {
			Shell[] shells = display.getShells();

			return Boolean.valueOf(shells.length > 0 && shells[0].isVisible());
		};

		while (!runWait(display, shellReady).booleanValue()) {
			Threads.sleep(STEP_TIMEOUT);
			sleepCount = ensureSleepLimit(sleepCount);
		}
		return display;
	}

	private void disposeAll(Runner runner) {
		Display display = Display.findDisplay(runner.displayThread());

		if (display != null && !display.isDisposed()) {
			for (Shell shell : display.getShells()) {
				if (!shell.isDisposed()) {
					shell.close();
					shell.dispose();
				}
			}
		}
	}

	void runTester(Runner runner) {
		Thread testerThread = new Thread(() -> {
			Display display = waitReady(runner);

			for (Step step : runner) {
				if (step.runOnDisplayThread()) {
					runWait(display, step);
				} else {
					step.run();
				}
			}
			Threads.sleep(DISPOSE_TIMEOUT);
			runWait(display, () -> disposeAll(runner));
		}, getClass().getSimpleName());

		testerThread.setDaemon(true);
		testerThread.start();
		try {
			runSWTApplication(runner.args());
		} catch (Exception e) {
			Throwable cause = e.getCause();

			if (cause instanceof AssertionError) {
				throw (AssertionError) cause;
			}
			throw new AssertionError("Application failed with exception: " + e.getClass().getName(), e);
		}
	}

	final class Step implements Runnable {

		private final Runnable runnable;
		private final boolean runOnDisplayThread;

		Step(Runnable runnable, boolean runOnDisplayThread) {
			this.runnable = runnable;
			this.runOnDisplayThread = runOnDisplayThread;
		}

		public boolean runOnDisplayThread() {
			return this.runOnDisplayThread;
		}

		@Override
		public void run() {
			this.runnable.run();
		}

	}

	/**
	 * Class used to setup and perform the application test run.
	 */
	public class Runner implements Runnable, Iterable<Step> {

		private final Thread displayThread;
		private final String[] args;
		private final List<Step> steps = new LinkedList<>();

		Runner(String[] args) {
			this.displayThread = Thread.currentThread();
			this.args = args;
		}

		/**
		 * Get the UI thread.
		 *
		 * @return The UI thread.
		 */
		public Thread displayThread() {
			return this.displayThread;
		}

		/**
		 * Get the test command line as submitted to {@linkplain SWTTester#runner(String[])}.
		 *
		 * @return The test command line.
		 */
		public String[] args() {
			return this.args;
		}

		/**
		 * Add a check to be run during application execution.
		 *
		 * @param check The check to add.
		 * @return The updated {@linkplain Runner}.
		 */
		public Runner check(Runnable check) {
			this.steps.add(new Step(check, true));
			return this;
		}

		/**
		 * Sleep the submitted amount of time during application execution.
		 *
		 * @param millis The milliseconds to sleep.
		 * @return The updated {@linkplain Runner}.
		 */
		public Runner sleep(long millis) {
			this.steps.add(new Step(() -> Threads.sleep(millis), false));
			return this;
		}

		/**
		 * Start and run the application and invoke the added checks.
		 *
		 * @see #check(Runnable)
		 * @see SWTTester#runSWTApplication(String[])
		 */
		@Override
		public void run() {
			runTester(this);
		}

		@Override
		public Iterator<Step> iterator() {
			return this.steps.iterator();
		}

	}

}
