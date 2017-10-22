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
package de.carne.swt.test.graphics;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import de.carne.swt.graphics.CreateResourceException;
import de.carne.swt.graphics.ImageResourcePool;
import de.carne.swt.graphics.ResourceException;
import de.carne.swt.graphics.UnknownResourceException;
import de.carne.swt.test.Images;
import de.carne.test.swt.rules.SWTDevice;

/**
 * Test {@linkplain ImageResourcePool} class.
 */
public class ImageResourcePoolTest {

	/**
	 * Test's SWT display.
	 */
	@Rule
	public final SWTDevice<Display> display = new SWTDevice<>(() -> new Display());

	/**
	 * Test {@linkplain ImageResourcePool#get(Class, String)} with existing images.
	 *
	 * @throws ResourceException if a resource is not available
	 */
	@Test
	public void testGetSuccess() throws ResourceException {
		ImageResourcePool pool = new ImageResourcePool(this.display.get());
		Image singleImage = pool.get(Images.class, Images.IMAGE_A_16);

		Assert.assertNotNull(singleImage);
		pool.disposeAll();
	}

	/**
	 * Test {@linkplain ImageResourcePool#get(Class, String)} with unkown image.
	 *
	 * @throws ResourceException if a resource is not available
	 */
	@Test(expected = UnknownResourceException.class)
	public void testGetFailure1() throws ResourceException {
		ImageResourcePool pool = new ImageResourcePool(this.display.get());

		pool.get(Images.class, "unknown.png");
		pool.disposeAll();
	}

	/**
	 * Test {@linkplain ImageResourcePool#get(Class, String)} with invalid image.
	 *
	 * @throws ResourceException if a resource is not available
	 */
	@Test(expected = CreateResourceException.class)
	public void testGetFailure2() throws ResourceException {
		ImageResourcePool pool = new ImageResourcePool(this.display.get());

		pool.get(Images.class, "Images.class");
		pool.disposeAll();
	}

	/**
	 * Test {@linkplain ImageResourcePool#getAll(Class, String...)}.
	 *
	 * @throws ResourceException if a resource is not available
	 */
	@Test
	public void testGetAllSuccess() throws ResourceException {
		ImageResourcePool pool = new ImageResourcePool(this.display.get());
		Image[] multiImage = pool.getAll(Images.class, Images.IMAGE_A_16, Images.IMAGE_A_32);

		Assert.assertEquals(2, multiImage.length);
		Assert.assertNotNull(multiImage[0]);
		Assert.assertNotNull(multiImage[1]);
		pool.disposeAll();
	}

}
