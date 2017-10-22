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

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.Assert;

import de.carne.ApplicationMain;
import de.carne.swt.UserApplication;
import de.carne.swt.graphics.ResourceException;
import de.carne.swt.widgets.UserInterface;
import de.carne.util.Exceptions;
import de.carne.util.cmdline.CmdLineProcessor;

/**
 * Test application which runs the test user interface.
 */
public class TestUserApplicationMain extends UserApplication implements ApplicationMain {

	@Override
	public String name() {
		return getClass().getTypeName();
	}

	@Override
	public int run(String[] args) {
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

}
