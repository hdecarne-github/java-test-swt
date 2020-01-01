/*
 * Copyright (c) 2017-2020 Holger de Carne and contributors, All Rights Reserved.
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

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import java.util.logging.Level;

import org.eclipse.swt.SWTError;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.jupiter.api.Assertions;
import org.opentest4j.AssertionFailedError;

import de.carne.boot.logging.Log;
import de.carne.boot.logging.LogLevel;
import de.carne.test.swt.platform.PlatformHelper;
import de.carne.util.Strings;

/**
 * {@linkplain Thread} class used to run the configured script actions against the SWT application.
 */
final class ScriptRunnerThread extends Thread {

	private static final Log LOG = new Log();

	private static final String ENV_SCREENSHOT_CMD = ScriptRunnerThread.class.getPackage().getName()
			+ ".SCREENSHOT_CMD";

	private final Thread displayThread;
	private final Iterable<ScriptAction> actions;
	private final boolean ignoreRemaining;
	private final Duration timeout;
	private final AtomicReference<AssertionError> assertionStatus = new AtomicReference<>();

	ScriptRunnerThread(Iterable<ScriptAction> actions, boolean ignoreRemaining, Duration timeout) {
		super(ScriptRunnerThread.class.getName());
		this.displayThread = Thread.currentThread();
		this.actions = actions;
		this.ignoreRemaining = ignoreRemaining;
		this.timeout = timeout;
	}

	public Optional<AssertionError> assertionStatus() {
		return Optional.ofNullable(this.assertionStatus.get());
	}

	@Override
	public void run() {
		LOG.info("{0} started", Thread.currentThread().getName());

		try {
			waitTrigger(this::displayAvailableTrigger);

			LOG.debug("Display is available on display thread; waiting for initial Shell...");

			waitTrigger(this::initialShellVisibleTrigger);

			LOG.debug("Initial Shell is visible; running actions...");

			Display display = getDisplay();
			List<String> remainingShellTexts;

			try {
				Assertions.assertTimeoutPreemptively(this.timeout, () -> runActions(display));

				LOG.debug("All actions processed; cleaning up...");
			} finally {
				remainingShellTexts = disposeRemaining(display, !this.ignoreRemaining);
			}
			if (!this.ignoreRemaining && !remainingShellTexts.isEmpty()) {
				Assertions.fail("Remaining Shells detected: " + Strings.join(remainingShellTexts, ", "));
			}
		} catch (InterruptedException e) {
			this.assertionStatus.set(new AssertionFailedError("Thread interrupted", e));
			Thread.currentThread().interrupt();
		} catch (Exception e) {
			this.assertionStatus.set(new AssertionFailedError("Uncaught exception: " + e.getClass().getName(), e));
		} catch (AssertionError e) {
			this.assertionStatus.set(e);
		}
	}

	private void runActions(Display display) throws InterruptedException {
		ScriptRunner scriptRunner = scriptRunner(display);

		for (ScriptAction action : this.actions) {
			Timing.step();
			action.run(scriptRunner);
		}
	}

	private List<String> disposeRemaining(Display display, boolean grabScreen) throws InterruptedException {
		List<String> remainingShellTexts = new ArrayList<>();
		boolean screenGrabbed = false;

		if (PlatformHelper.inNativeDialog(display)) {
			LOG.log((this.ignoreRemaining ? LogLevel.LEVEL_INFO : LogLevel.LEVEL_WARNING), null,
					"Closing native dialog");

			remainingShellTexts.add("<native dialog>");

			if (grabScreen) {
				grabScreen();
				screenGrabbed = true;
			}

			Timing closing = new Timing();

			while (PlatformHelper.closeNativeDialogs(display)) {
				closing.step("");
			}
		}
		if (!display.isDisposed()) {
			boolean grabScreen0 = grabScreen && !screenGrabbed;

			runWait(display, () -> disposeRemaining0(remainingShellTexts, display, grabScreen0));
		}
		return remainingShellTexts;
	}

	private List<String> disposeRemaining0(List<String> remainingShellTexts, Display display, boolean grabScreen) {
		if (!display.isDisposed()) {
			Shell[] shells = display.getShells();

			if (grabScreen && shells.length > 0) {
				grabScreen();
			}
			for (Shell shell : shells) {
				if (!shell.isDisposed()) {
					String shellText = shell.getText();

					LOG.log((this.ignoreRemaining ? LogLevel.LEVEL_INFO : LogLevel.LEVEL_WARNING), null,
							"Closing remaining Shell ''{0}''", shellText);

					remainingShellTexts.add(shellText);
					shell.close();
					shell.dispose();
				}
			}
			display.dispose();
		}
		return remainingShellTexts;
	}

	private static final Set<String> VALID_SCREENSHOT_CMDS = new HashSet<>();

	static {
		// macOS
		VALID_SCREENSHOT_CMDS.add("screencapture -c");
	}

	@SuppressWarnings("squid:S2076")
	private void grabScreen() {
		String screenshotCmd = System.getenv(ENV_SCREENSHOT_CMD);

		if (screenshotCmd != null && VALID_SCREENSHOT_CMDS.contains(screenshotCmd)) {
			LOG.info("Invoking screenshot command ''{0}''...", screenshotCmd);

			try {
				Process screenshotProcess = Runtime.getRuntime().exec(screenshotCmd);
				boolean screenshotProcessExited = screenshotProcess.waitFor(Timing.STEP_TIMEOUT, TimeUnit.MILLISECONDS);

				if (screenshotProcessExited) {
					int screenshotProcessStatus = screenshotProcess.exitValue();

					LOG.log((screenshotProcessStatus != 0 ? Level.WARNING : Level.INFO), null,
							"Screenshot command ''{0}'' finished (status {1})", screenshotCmd, screenshotProcessStatus);
				} else {
					LOG.warning("Screenshot command ''{0}'' not finished after {1} ms", screenshotCmd,
							Timing.STEP_TIMEOUT);
				}
			} catch (IOException e) {
				LOG.warning(e, "Screenshot command ''{0}'' failed with I/O error", screenshotCmd);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				LOG.warning(e, "Screenshot command ''{0}'' interupted", screenshotCmd);
			}
		} else {
			LOG.info("Environment variable {0} not set or not valid; ignoring screenshot request", ENV_SCREENSHOT_CMD);
		}
	}

	private Display getDisplay() {
		Display display = Display.findDisplay(this.displayThread);

		Assertions.assertNotNull(display, "No Display found");

		return Objects.requireNonNull(display);
	}

	private void waitTrigger(BooleanSupplier trigger) throws InterruptedException {
		Timing.step();

		Timing wait = new Timing();

		while (!trigger.getAsBoolean()) {
			wait.step("Timeout execeeded while waiting for trigger");
		}
	}

	private boolean displayAvailableTrigger() {
		return Display.findDisplay(this.displayThread) != null;
	}

	private boolean initialShellVisibleTrigger() {
		Display display = Display.findDisplay(this.displayThread);

		return display != null && !display.isDisposed() && runWait(display, () -> {
			Shell[] shells = (!display.isDisposed() ? display.getShells() : null);

			return shells != null && shells[0].isVisible();
		}).booleanValue();
	}

	private ScriptRunner scriptRunner(Display display) {
		return new ScriptRunner() {

			@Override
			public void runNoWait(Runnable runnable) {
				ScriptRunnerThread.this.runNoWait(display, runnable);
			}

			@Override
			public void runWait(Runnable runnable) {
				ScriptRunnerThread.this.runWait(display, runnable);
			}

			@Override
			public <T> T runWait(Supplier<T> supplier) {
				return ScriptRunnerThread.this.runWait(display, supplier);
			}

		};
	}

	void runNoWait(Display display, Runnable runnable) {
		display.asyncExec(runnable);
	}

	void runWait(Display display, Runnable runnable) {
		if (Thread.currentThread().equals(display.getThread())) {
			runnable.run();
		} else {
			checkNativeDialog(display);
			try {
				display.syncExec(runnable);
			} catch (RuntimeException | SWTError e) {
				Throwable cause = e.getCause();

				if (cause instanceof AssertionError) {
					throw (AssertionError) cause;
				}
				throw e;
			}
		}
	}

	<T> T runWait(Display display, Supplier<T> supplier) {
		final AtomicReference<T> resultHolder = new AtomicReference<>();

		if (Thread.currentThread().equals(display.getThread())) {
			resultHolder.set(supplier.get());
		} else {
			checkNativeDialog(display);
			try {
				display.syncExec(() -> resultHolder.set(supplier.get()));
			} catch (RuntimeException | SWTError e) {
				Throwable cause = e.getCause();

				if (cause instanceof AssertionError) {
					throw (AssertionError) cause;
				}
				throw e;
			}
		}
		return resultHolder.get();
	}

	private void checkNativeDialog(Display display) {
		if (PlatformHelper.inNativeDialog(display)) {
			Assertions.fail("Native dialog detected");
		}
	}

}
