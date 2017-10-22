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
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.junit.Assert;
import org.junit.Test;

import de.carne.swt.layout.RowLayoutBuilder;

/**
 * Test {@linkplain RowLayoutBuilder} class.
 */
public class RowLayoutBuilderTest {

	/**
	 * Test {@linkplain RowLayoutBuilder} class.
	 */
	@Test
	public void testRowLayoutBuilder() {
		RowLayout layout1 = RowLayoutBuilder.layout().margin(42, 43).spacing(44).wrap(false).pack(false).fill(true)
				.center(true).justify(true).get();

		Assert.assertEquals(layout1.type, SWT.HORIZONTAL);
		Assert.assertEquals(layout1.marginWidth, 42);
		Assert.assertEquals(layout1.marginHeight, 43);
		Assert.assertEquals(layout1.marginLeft, 3);
		Assert.assertEquals(layout1.marginTop, 3);
		Assert.assertEquals(layout1.marginRight, 3);
		Assert.assertEquals(layout1.marginBottom, 3);
		Assert.assertEquals(layout1.spacing, 44);
		Assert.assertFalse(layout1.wrap);
		Assert.assertFalse(layout1.pack);
		Assert.assertTrue(layout1.fill);
		Assert.assertTrue(layout1.center);
		Assert.assertTrue(layout1.justify);

		RowLayout layout2 = RowLayoutBuilder.layout(SWT.VERTICAL).margin(42, 43, 44, 45).get();

		Assert.assertEquals(layout2.type, SWT.VERTICAL);
		Assert.assertEquals(layout2.marginWidth, 0);
		Assert.assertEquals(layout2.marginHeight, 0);
		Assert.assertEquals(layout2.marginLeft, 42);
		Assert.assertEquals(layout2.marginTop, 43);
		Assert.assertEquals(layout2.marginRight, 44);
		Assert.assertEquals(layout2.marginBottom, 45);
		Assert.assertEquals(layout2.spacing, 3);
		Assert.assertTrue(layout2.wrap);
		Assert.assertTrue(layout2.pack);
		Assert.assertFalse(layout2.fill);
		Assert.assertFalse(layout2.center);
		Assert.assertFalse(layout2.justify);

		RowData data1 = RowLayoutBuilder.data().size(42, 43).exclude(true).get();

		Assert.assertEquals(data1.width, 42);
		Assert.assertEquals(data1.height, 43);
		Assert.assertTrue(data1.exclude);

		RowData data2 = RowLayoutBuilder.data().get();

		Assert.assertEquals(data2.width, SWT.DEFAULT);
		Assert.assertEquals(data2.height, SWT.DEFAULT);
		Assert.assertFalse(data2.exclude);
	}

}
