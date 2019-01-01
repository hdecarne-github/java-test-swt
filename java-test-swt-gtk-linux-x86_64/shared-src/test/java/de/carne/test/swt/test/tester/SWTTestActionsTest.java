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
package de.carne.test.swt.test.tester;

import java.util.regex.Pattern;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.carne.test.swt.DisableIfThreadNotSWTCapable;
import de.carne.test.swt.tester.SWTTest;
import de.carne.test.swt.tester.accessor.DecorationsAccessor;
import de.carne.test.swt.tester.accessor.ItemAccessor;

/**
 * Test {@linkplain SWTTest} class.
 */
@DisableIfThreadNotSWTCapable
class SWTTestActionsTest extends SWTTest {

	@Test
	void testTester() {
		Script script = script(new SWTApp());

		script.add(this::testAccessors);
		script.add(this::selectAppQuit);
		script.execute();
	}

	private void testAccessors() {
		Pattern anyPattern = Pattern.compile(".*");

		// ShellAccessor
		Assertions.assertTrue(shells().allMatch(DecorationsAccessor.matchText(anyPattern)));

		// MenuAccessor
		Assertions.assertTrue(accessShell().accessMenuBar().items().allMatch(ItemAccessor.matchText(anyPattern)));
	}

	private void selectAppQuit() {
		accessShell().accessMenuBar().accessItem(SWTApp.TITLE_MENU_APP_QUIT).select();
	}

}
