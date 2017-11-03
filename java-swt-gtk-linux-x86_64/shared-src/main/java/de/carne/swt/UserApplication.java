/*
 * Copyright (c) 2007-2017 Holger de Carne and contributors, All Rights Reserved.
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
package de.carne.swt;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import de.carne.check.Nullable;
import de.carne.swt.graphics.ResourceException;
import de.carne.swt.widgets.UserInterface;
import de.carne.util.Exceptions;
import de.carne.util.Late;
import de.carne.util.cmdline.CmdLineException;
import de.carne.util.cmdline.CmdLineProcessor;

/**
 * Base class for any kind of SWT based application.
 */
public abstract class UserApplication {

	private final Late<CmdLineProcessor> cmdLineHolder = new Late<>();
	private final Late<Display> displayHolder = new Late<>();

	private int status = 0;

	/**
	 * Run the application by setting up the user interface and executing the event loop.
	 *
	 * @param cmdLine The {@linkplain CmdLineProcessor} to invoke after the user interface has been set up.
	 * @return The application's exit code (as set by {@linkplain #setStatus(int)}).
	 * @throws ResourceException If a required resource is not available.
	 */
	public int run(CmdLineProcessor cmdLine) throws ResourceException {
		this.cmdLineHolder.set(cmdLine);

		Display display = this.displayHolder.set(setupDisplay());
		Shell shell = setupUserInterface(display).root();

		shell.open();
		try {
			cmdLine.process();
		} catch (CmdLineException e) {
			Exceptions.warn(e);
		}
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return this.status;
	}

	/**
	 * Setup the {@linkplain Display}.
	 *
	 * @return The application's {@linkplain Display}.
	 * @throws ResourceException If a required resource is not available.
	 */
	protected abstract Display setupDisplay() throws ResourceException;

	/**
	 * Setup the start {@linkplain Shell}.
	 *
	 * @param display The application's {@linkplain Display}.
	 * @return The application's start {@linkplain Shell}.
	 * @throws ResourceException If a required resource is not available.
	 */
	protected abstract UserInterface<Shell> setupUserInterface(Display display) throws ResourceException;

	/**
	 * Get the application's {@linkplain Display}.
	 *
	 * @return The application's {@linkplain Display}.
	 */
	public Display getDisplay() {
		return this.displayHolder.get();
	}

	/**
	 * Run a {@linkplain Runnable} on the UI thread.
	 * <p>
	 * The submitted {@linkplain Runnable} is invoked asynchronously and this function returns immediately.
	 *
	 * @param runnable The {@linkplain Runnable} to invoke.
	 * @see Display#asyncExec(Runnable)
	 */
	public void runNoWait(Runnable runnable) {
		this.displayHolder.get().asyncExec(runnable);
	}

	/**
	 * Run a {@linkplain Runnable} on the UI thread and wait for it to finish.
	 *
	 * @param runnable The {@linkplain Runnable} to invoke.
	 */
	public void runWait(Runnable runnable) {
		Display display = this.displayHolder.get();

		if (Thread.currentThread().equals(display.getThread())) {
			runnable.run();
		} else {
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
	}

	/**
	 * Invoke a {@linkplain Supplier} on the UI thread and wait for it to finish.
	 *
	 * @param <T> The {@linkplain Supplier} result type.
	 * @param supplier The {@linkplain Supplier} to invoke.
	 * @return The result of the {@linkplain Supplier#get()} call (may be {@code null}).
	 */
	@Nullable
	public <T> T runWait(Supplier<T> supplier) {
		Display display = this.displayHolder.get();
		T result;

		if (Thread.currentThread().equals(display.getThread())) {
			result = supplier.get();
		} else {
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
			result = resultHolder.get();
		}
		return result;
	}

	/**
	 * Set the application's exit status (as returned by {@linkplain #run(CmdLineProcessor)}).
	 *
	 * @param status The exit status to set.
	 */
	public void setStatus(int status) {
		this.status = status;
	}

}
