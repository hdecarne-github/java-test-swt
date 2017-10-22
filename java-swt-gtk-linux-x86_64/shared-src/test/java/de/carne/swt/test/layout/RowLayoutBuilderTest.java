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

		Assert.assertEquals(SWT.HORIZONTAL, layout1.type);
		Assert.assertEquals(42, layout1.marginWidth);
		Assert.assertEquals(43, layout1.marginHeight);
		Assert.assertEquals(3, layout1.marginLeft);
		Assert.assertEquals(3, layout1.marginTop);
		Assert.assertEquals(3, layout1.marginRight);
		Assert.assertEquals(3, layout1.marginBottom);
		Assert.assertEquals(44, layout1.spacing);
		Assert.assertFalse(layout1.wrap);
		Assert.assertFalse(layout1.pack);
		Assert.assertTrue(layout1.fill);
		Assert.assertTrue(layout1.center);
		Assert.assertTrue(layout1.justify);

		RowLayout layout2 = RowLayoutBuilder.layout(SWT.VERTICAL).margin(42, 43, 44, 45).get();

		Assert.assertEquals(SWT.VERTICAL, layout2.type);
		Assert.assertEquals(0, layout2.marginWidth);
		Assert.assertEquals(0, layout2.marginHeight);
		Assert.assertEquals(42, layout2.marginLeft);
		Assert.assertEquals(43, layout2.marginTop);
		Assert.assertEquals(44, layout2.marginRight);
		Assert.assertEquals(45, layout2.marginBottom);
		Assert.assertEquals(3, layout2.spacing);
		Assert.assertTrue(layout2.wrap);
		Assert.assertTrue(layout2.pack);
		Assert.assertFalse(layout2.fill);
		Assert.assertFalse(layout2.center);
		Assert.assertFalse(layout2.justify);

		RowData data1 = RowLayoutBuilder.data().size(42, 43).exclude(true).get();

		Assert.assertEquals(42, data1.width);
		Assert.assertEquals(43, data1.height);
		Assert.assertTrue(data1.exclude);

		RowData data2 = RowLayoutBuilder.data().get();

		Assert.assertEquals(SWT.DEFAULT, data2.width);
		Assert.assertEquals(SWT.DEFAULT, data2.height);
		Assert.assertFalse(data2.exclude);
	}

}
