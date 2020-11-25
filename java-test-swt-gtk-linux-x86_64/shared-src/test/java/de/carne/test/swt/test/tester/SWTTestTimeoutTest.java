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
package de.carne.test.swt.test.tester;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.carne.boot.Application;
import de.carne.test.swt.DisableIfThreadNotSWTCapable;
import de.carne.test.swt.tester.SWTTest;

/**
 * Test {@linkplain SWTTest} class - Test timeout due to unexpected message box.
 */
@DisableIfThreadNotSWTCapable
class SWTTestTimeoutTest extends SWTTest {

	private static int TIMEOUT = 5000;

	@BeforeAll
	static void setTestTimeout() {
		System.setProperty(SWTTest.class.getPackage().getName() + ".TEST_TIMEOUT", Integer.toString(TIMEOUT));
	}

	@Test
	void testTimeout() {
		Script script = script(Application::run).args(getClass().getSimpleName()).add(this::openMessageBox);

		Assertions.assertThrows(AssertionError.class, () -> {
			script.execute();
		});
	}

	private void openMessageBox() {
		traceAction();

		MessageBox messageBox = new MessageBox(accessShell().get(), SWT.ICON_CANCEL | SWT.CANCEL);

		messageBox.setText(getClass().getSimpleName());
		messageBox.setMessage("Waiting for " + TIMEOUT + " ms timeout...");
		messageBox.open();
	}

}
