/*
 * Copyright (c) 2007-2018 Holger de Carne and contributors, All Rights Reserved.
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
package de.carne.swt.test.graphics;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.carne.swt.graphics.CreateResourceException;
import de.carne.swt.graphics.ImageResourcePool;
import de.carne.swt.graphics.ResourceException;
import de.carne.swt.graphics.UnknownResourceException;
import de.carne.swt.test.Images;
import de.carne.test.swt.extension.SWTDisplayParameterResolver;

/**
 * Test {@linkplain ImageResourcePool} class.
 */
@ExtendWith(SWTDisplayParameterResolver.class)
class ImageResourcePoolTest {

	@Test
	void testGetSuccess(Display display) throws ResourceException {
		ImageResourcePool pool = new ImageResourcePool(display);
		Image singleImage = pool.get(Images.class, Images.IMAGE_A_16);

		Assertions.assertNotNull(singleImage);
		pool.disposeAll();
	}

	@Test
	void testGetFailure1(Display display) {
		ImageResourcePool pool = new ImageResourcePool(display);

		Assertions.assertThrows(UnknownResourceException.class, () -> {
			pool.get(Images.class, "unknown.png");
		});
		Assertions.assertThrows(CreateResourceException.class, () -> {
			pool.get(Images.class, "Images.class");
		});
		pool.disposeAll();
	}

	@Test
	void testGetAllSuccess(Display display) throws ResourceException {
		ImageResourcePool pool = new ImageResourcePool(display);
		Image[] multiImage = pool.getAll(Images.class, Images.IMAGE_A_16, Images.IMAGE_A_32);

		Assertions.assertEquals(2, multiImage.length);
		Assertions.assertNotNull(multiImage[0]);
		Assertions.assertNotNull(multiImage[1]);
		pool.disposeAll();
	}

}
