/*
 * Copyright (c) 2017-2018 Holger de Carne and contributors, All Rights Reserved.
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
package de.carne.test.swt.extension;

import org.eclipse.swt.widgets.Display;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Test {@linkplain SWTDisplayParameterResolver} class.
 */
@ExtendWith(SWTDisplayParameterResolver.class)
class SWTDisplayParameterResolverTest {

	@Test
	void test1stAccess(Display display) {
		Assertions.assertNotNull(display);
		Assertions.assertFalse(display.isDisposed());
	}

	@Test
	void test2ndAccess(Display display) {
		Assertions.assertNotNull(display);
		Assertions.assertFalse(display.isDisposed());
	}

}
