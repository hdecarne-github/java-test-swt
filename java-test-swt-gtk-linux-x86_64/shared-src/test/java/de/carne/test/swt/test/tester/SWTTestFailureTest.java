/*
 * Copyright (c) 2017-2022 Holger de Carne and contributors, All Rights Reserved.
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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.carne.test.swt.DisableIfThreadNotSWTCapable;
import de.carne.test.swt.tester.SWTTest;

/**
 * Test {@linkplain SWTTest} class - Test failure.
 */
@DisableIfThreadNotSWTCapable
class SWTTestFailureTest extends SWTTest {

	@Test
	void testFailure() {
		Script script = script(SWTTestApplication::main);

		script.args(getClass().getSimpleName()).add(this::doFailure);
		Assertions.assertThrows(AssertionError.class, () -> {
			script.execute();
		});

		Assertions.assertFalse(script.passed());
	}

	private void doFailure() {
		traceAction();

		accessShell("unknown").get().close();
	}

}
