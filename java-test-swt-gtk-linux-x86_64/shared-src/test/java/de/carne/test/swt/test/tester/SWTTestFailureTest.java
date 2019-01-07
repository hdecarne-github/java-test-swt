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

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.carne.swt.test.app.TestAppMain;
import de.carne.test.swt.DisableIfThreadNotSWTCapable;
import de.carne.test.swt.tester.SWTTest;

/**
 * Test {@linkplain SWTTest} class.
 */
@DisableIfThreadNotSWTCapable
class SWTTestFailureTest extends SWTTest {

	@Test
	public void testFailure() {
		Assertions.assertThrows(AssertionError.class, () -> {
			script(new TestAppMain()).add(this::checkFailure).execute();
		});
	}

	private void checkFailure() {
		traceAction();
		accessShell("unknown").get().close();
	}

}
