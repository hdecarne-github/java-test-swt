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

import org.eclipse.swt.widgets.Shell;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.carne.test.swt.DisableIfThreadNotSWTCapable;
import de.carne.test.swt.tester.SWTTest;

/**
 * Test {@linkplain SWTTest} class - Full application test.
 */
@DisableIfThreadNotSWTCapable
class SWTTestTest extends SWTTest {

	@Test
	void testApplication() {
		Script script = script(SWTTestApplication::main);

		script.add(this::doCloseRoot);
		script.execute();

		Assertions.assertTrue(script.passed());
	}

	protected void doCloseRoot() {
		traceAction();

		Shell root = accessShell(SWTTestApplication.ROOT_TITLE).get();

		root.close();
	}

}
