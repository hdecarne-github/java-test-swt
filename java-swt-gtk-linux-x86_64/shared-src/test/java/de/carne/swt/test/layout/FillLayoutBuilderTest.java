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
package de.carne.swt.test.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.junit.Assert;
import org.junit.Test;

import de.carne.swt.layout.FillLayoutBuilder;

/**
 * Test {@linkplain FillLayoutBuilder} class.
 */
public class FillLayoutBuilderTest {

	/**
	 * Test {@linkplain FillLayoutBuilder} class.
	 */
	@Test
	public void testFillLayoutBuilder() {
		FillLayout layout1 = FillLayoutBuilder.layout().margin(42, 43).spacing(44).get();

		Assert.assertEquals(layout1.type, SWT.HORIZONTAL);
		Assert.assertEquals(layout1.marginWidth, 42);
		Assert.assertEquals(layout1.marginHeight, 43);
		Assert.assertEquals(layout1.spacing, 44);

		FillLayout layout2 = FillLayoutBuilder.layout(SWT.VERTICAL).get();

		Assert.assertEquals(layout2.type, SWT.VERTICAL);
		Assert.assertEquals(layout2.marginWidth, 0);
		Assert.assertEquals(layout2.marginHeight, 0);
		Assert.assertEquals(layout2.spacing, 0);
	}

}
