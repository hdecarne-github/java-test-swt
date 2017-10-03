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

import org.junit.BeforeClass;
import org.junit.Test;

import de.carne.Application;
import de.carne.swt.UserApplication;
import de.carne.util.Threads;

/**
 * Test {@linkplain UserApplication} class.
 */
public class UserApplicationTest implements Runnable {

	/**
	 * Setup the necessary system properties.
	 */
	@BeforeClass
	public static void setUpBeforeClass() {
		System.setProperty("de.carne.Application.USE_LAUNCHER_CLASS_LOADER", Boolean.TRUE.toString());
		System.setProperty("de.carne.Application", "test");
	}

	/**
	 * Run and test {@linkplain TestUserApplicationMain}.
	 */
	@Test
	public void testSWTApplication() {
		TestUserApplicationMain.setRobotThreadSupplier(() -> new Thread(this));
		Application.main(new String[0]);
	}

	@Override
	public void run() {
		Threads.sleep(1000);

		TestUserApplicationMain application = Application.getMain(TestUserApplicationMain.class);

		application.waitReady();
		application.close();
	}

}
