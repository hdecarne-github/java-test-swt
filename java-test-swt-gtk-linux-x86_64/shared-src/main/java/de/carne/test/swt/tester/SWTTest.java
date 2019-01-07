/*
 * Copyright (c) 2017-2019 Holger de Carne and contributors, All Rights Reserved.
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

import java.time.Duration;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.jupiter.api.Assertions;

import de.carne.boot.ApplicationMain;
import de.carne.boot.logging.Log;
import de.carne.test.swt.tester.accessor.Accessor;
import de.carne.test.swt.tester.accessor.DecorationsAccessor;
import de.carne.test.swt.tester.accessor.ShellAccessor;
import de.carne.util.Late;
import de.carne.util.Strings;

/**
 * Base class for SWT application tests.
 * <p>
 * The test is performed by running the SWT application subject to testing within the context of the test class. The
 * {@code SWTTest} class provides the necessary functionality to execute all kinds of actions and checks against the
 * running application (see {@linkplain Script}). The latter is achieved by creating a separate script runner thread
 * which issues the necessary events.
 * </p>
 */
public abstract class SWTTest {

	private static final Log LOG = new Log();

	private final Late<MessageBoxMock> messageBoxMock = new Late<>();
	private final Late<FileDialogMock> fileDialogMock = new Late<>();
	private final Late<PrintDialogMock> printDialogMock = new Late<>();

	/**
	 * Constructs a new {@code SWTTest} instance.
	 */
	protected SWTTest() {
		this(true);
	}

	/**
	 * Constructs a new {@code SWTTest} instance.
	 *
	 * @param mockDialogs whether to mock native {@linkplain Dialog} instance ({@code true}) or not ({@code false}).
	 */
	protected SWTTest(boolean mockDialogs) {
		if (mockDialogs) {
			this.messageBoxMock.set(new MessageBoxMock());
			this.fileDialogMock.set(new FileDialogMock());
		}
	}

	/**
	 * Creates a {@linkplain Script} instance to be executed after the necessary actions have been added.
	 *
	 * @param application the SWT application to test.
	 * @return the created {@linkplain Script} instance.
	 * @see Script
	 */
	public Script script(ApplicationMain application) {
		return new Script(application);
	}

	/**
	 * This class defines the actual test setup/script:
	 * <ul>
	 * <li>the SWT application to run and to run the script actions against.</li>
	 * <li>the optional command line arguments submitted to the SWT application.</li>
	 * <li>one or more script actions performing the actual test actions and checks.</li>
	 * </ul>
	 * By invoking the {@linkplain #execute()} function the SWT application is started and the scripts actions are
	 * executed.
	 */
	public final class Script {

		private final ApplicationMain application;
		private String[] applicationArgs = new String[0];
		private final List<ScriptAction> actions = new LinkedList<>();

		Script(ApplicationMain application) {
			this.application = application;
		}

		/**
		 * Sets the command line arguments the SWT application should be invoked with.
		 * <p>
		 * If not set an empty command line is used to start the SWT application.
		 * </p>
		 *
		 * @param args the command line arguments to use.
		 * @return the updated script.
		 */
		public Script args(String[] args) {
			this.applicationArgs = args;
			return this;
		}

		/**
		 * Adds an action to be executed during the test.
		 * <p>
		 * The action will be executed on the UI thread.
		 * </p>
		 *
		 * @param action the action to add.
		 * @return the updated script.
		 */
		public Script add(Runnable action) {
			this.actions.add(new ScriptAction(action, false));
			return this;
		}

		/**
		 * Adds an action to be executed during the test.
		 * <p>
		 * The action will be executed on the UI thread.
		 * </p>
		 *
		 * @param action the action to add.
		 * @param async whether to execute the action asynchronously ({@code true}) or not ({@code false}).
		 * @return the updated script.
		 */
		public Script add(Runnable action, boolean async) {
			this.actions.add(new ScriptAction(action, async));
			return this;
		}

		/**
		 * Execute all script actions.
		 * <p>
		 * Invoking this function is equivalent to invoking {@code execute(false)}.
		 * </p>
		 *
		 * @see #execute(boolean)
		 */
		public void execute() {
			execute(false);
		}

		/**
		 * Execute all script actions.
		 * <p>
		 * Invoking this function is equivalent to invoking
		 * {@code execute(false, Duration.ofMillis(Timing.TEST_TIMEOUT))}.
		 * </p>
		 *
		 * @param ignoreRemaining whether to ignore any remaining application artifacts after execution and silently
		 * dispose them ({@code true}) or to signal a test failure ({@code false}).
		 */
		public void execute(boolean ignoreRemaining) {
			execute(ignoreRemaining, Duration.ofMillis(Timing.TEST_TIMEOUT));
		}

		/**
		 * Execute all script actions.
		 *
		 * @param ignoreRemaining whether to ignore any remaining application artifacts after execution and silently
		 * dispose them ({@code true}) or to signal a test failure ({@code false}).
		 * @param timeout timeout (in ms) after which the script execution will be stopped and the test considered
		 * failed.
		 */
		public void execute(boolean ignoreRemaining, Duration timeout) {
			runScript(this.application, this.applicationArgs, this.actions, ignoreRemaining, timeout);
		}

	}

	void runScript(ApplicationMain application, String[] applicationArgs, Iterable<ScriptAction> actions,
			boolean ignoreRemaining, Duration timeout) {
		try {
			ScriptRunnerThread scriptRunnerThread = new ScriptRunnerThread(actions, ignoreRemaining, timeout);

			scriptRunnerThread.setDaemon(true);
			scriptRunnerThread.start();

			LOG.info("Running application {0} {1}...", application.name(), Strings.join(applicationArgs, " "));

			int applicationStatus = application.run(applicationArgs);

			LOG.info("Application terminated normally (status {0})", applicationStatus);

			Timing wait = new Timing(scriptRunnerThread::join);

			while (scriptRunnerThread.isAlive()) {
				wait.step("Timeout exceeded while waiting for script runner thread to finish");
			}
			scriptRunnerThread.assertionStatus().ifPresent(error -> {
				throw error;
			});
		} catch (Exception e) {
			Assertions.fail("Uncaught exception: " + e.getClass().getName(), e);
		}
	}

	/**
	 * Traces the calling function in the test run's debug log.
	 */
	protected void traceAction() {
		if (LOG.isDebugLoggable()) {
			StackTraceElement[] stes = Thread.currentThread().getStackTrace();

			LOG.debug("Executing {0}", stes[2]);
		}
	}

	/**
	 * Gets the SWT {@linkplain Display}.
	 * <p>
	 * A test failure is signaled in case no SWT {@linkplain Display} exists. The latter is the case if the current
	 * thread is not the UI thread or if the {@linkplain Display} has either not yet been created or has already been
	 * disposed.
	 * </p>
	 *
	 * @return the SWT {@linkplain Display}.
	 */
	protected Display display() {
		return Accessor.accessNonNull(Display.findDisplay(Thread.currentThread()), "No Display found");
	}

	/**
	 * Gets all non disposed {@linkplain Shell} instances.
	 *
	 * @return all non disposed {@linkplain Shell} instances.
	 */
	protected Stream<Shell> shells() {
		return Arrays.stream(display().getShells());
	}

	/**
	 * Convenience function for single {@linkplain Shell} applications which gets this unique {@linkplain Shell}.
	 * <p>
	 * A test failure is signaled if either no {@linkplain Shell} exists or more than one.
	 * </p>
	 *
	 * @return the application's unique {@linkplain Shell}.
	 */
	protected ShellAccessor accessShell() {
		return Accessor.accessUnique(shells(), ShellAccessor::new);
	}

	/**
	 * Convenience function which gets a specific {@linkplain Shell} identified by it's text.
	 * <p>
	 * A test failure is signaled if either none or more than one {@linkplain Shell} with the given text exists.
	 * </p>
	 *
	 * @param text the text of the {@linkplain Shell} to get.
	 * @return the found {@linkplain Shell}.
	 */
	protected ShellAccessor accessShell(String text) {
		return Accessor.accessUnique(shells().filter(DecorationsAccessor.matchText(text)), ShellAccessor::new);
	}

	/**
	 * Gets the test's {@linkplain MessageBoxMock}.
	 * <p>
	 * Fails if dialog mocking is disabled (see {@linkplain #SWTTest(boolean)}).
	 * </p>
	 *
	 * @return the test's {@linkplain MessageBoxMock}.
	 */
	protected MessageBoxMock mockMessageBox() {
		return this.messageBoxMock.get();
	}

	/**
	 * Gets the test's {@linkplain FileDialogMock}.
	 * <p>
	 * Fails if dialog mocking is disabled (see {@linkplain #SWTTest(boolean)}).
	 * </p>
	 *
	 * @return the test's {@linkplain FileDialogMock}.
	 */
	protected FileDialogMock mockFileDialog() {
		return this.fileDialogMock.get();
	}

	/**
	 * Gets the test's {@linkplain PrintDialogMock}.
	 * <p>
	 * Fails if dialog mocking is disabled (see {@linkplain #SWTTest(boolean)}).
	 * </p>
	 *
	 * @return the test's {@linkplain PrintDialogMock}.
	 */
	protected PrintDialogMock mockPrintDialog() {
		return this.printDialogMock.get();
	}

}
