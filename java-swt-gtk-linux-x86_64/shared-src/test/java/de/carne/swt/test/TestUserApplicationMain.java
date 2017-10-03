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
package de.carne.swt.test;

import java.util.function.Supplier;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.Assert;

import de.carne.ApplicationMain;
import de.carne.check.Check;
import de.carne.swt.ResourceException;
import de.carne.swt.UserApplication;
import de.carne.swt.widgets.UserInterface;
import de.carne.util.Exceptions;
import de.carne.util.Late;
import de.carne.util.Threads;
import de.carne.util.cmdline.CmdLineProcessor;

/**
 * Test application that utilizes the features to be tested.
 */
public class TestUserApplicationMain extends UserApplication implements ApplicationMain {

	private static final Late<Supplier<Thread>> ROBOT_THREAD_SUPPLIER_HOLDER = new Late<>();

	private static final long SLEEP_STEP_TIMEOUT = 250;
	private static final long SLEEP_STEP_LIMIT = 40;

	/**
	 * @param robotThreadSupplier Invoked after application startup, but before UI setup.
	 */
	public static void setRobotThreadSupplier(Supplier<Thread> robotThreadSupplier) {
		ROBOT_THREAD_SUPPLIER_HOLDER.set(robotThreadSupplier);
	}

	@Override
	public String name() {
		return getClass().getTypeName();
	}

	@Override
	public int run(String[] args) {
		ROBOT_THREAD_SUPPLIER_HOLDER.get().get().start();

		int status;

		try {
			status = run(new CmdLineProcessor(name(), args));
		} catch (ResourceException e) {
			Assert.fail(Exceptions.toString(e));
			status = -1;
		}
		return status;
	}

	@Override
	protected Display setupDisplay() throws ResourceException {
		Display.setAppName(getClass().getTypeName());
		return new Display();
	}

	@Override
	protected UserInterface<Shell> setupUserInterface(Display display) throws ResourceException {
		TestUserInterface userInterface = new TestUserInterface();

		userInterface.setup(new Shell(display));
		return userInterface;
	}

	/**
	 * Wait for the startup shell to become ready.
	 */
	public void waitReady() {
		Display display = getDisplay();

		Check.assertTrue(!Thread.currentThread().equals(display.getThread()));

		Supplier<Boolean> readyCheck = () -> {
			Shell[] shells = display.getShells();

			return Boolean.valueOf(shells.length > 0 && shells[0].isVisible());
		};

		int sleepCount = 0;

		while (!Check.notNull(runWait(readyCheck)).booleanValue()) {
			Threads.sleep(SLEEP_STEP_TIMEOUT);
			sleepCount++;
			Assert.assertTrue(sleepCount < SLEEP_STEP_LIMIT);
		}
	}

	/**
	 * Close the application by closing all shells.
	 */
	public void close() {
		runWait(() -> {
			Display display = getDisplay();

			for (Shell shell : display.getShells()) {
				shell.close();
			}
		});
	}

}
