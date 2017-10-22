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

		Assert.assertEquals(SWT.HORIZONTAL, layout1.type);
		Assert.assertEquals(42, layout1.marginWidth);
		Assert.assertEquals(43, layout1.marginHeight);
		Assert.assertEquals(44, layout1.spacing);

		FillLayout layout2 = FillLayoutBuilder.layout(SWT.VERTICAL).get();

		Assert.assertEquals(SWT.VERTICAL, layout2.type);
		Assert.assertEquals(0, layout2.marginWidth);
		Assert.assertEquals(0, layout2.marginHeight);
		Assert.assertEquals(0, layout2.spacing);
	}

}
