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
package de.carne.swt.test.layout;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.junit.Assert;
import org.junit.Test;

import de.carne.swt.layout.GridLayoutBuilder;

/**
 * Test {@linkplain GridLayoutBuilder} class.
 */
public class GridLayoutBuilderTest {

	/**
	 * Test {@linkplain GridLayoutBuilder} class.
	 */
	@Test
	public void testGridLayoutBuilder() {
		GridLayout layout1 = GridLayoutBuilder.layout().margin(42, 43).spacing(44, 45).get();

		Assert.assertEquals(1, layout1.numColumns);
		Assert.assertFalse(layout1.makeColumnsEqualWidth);
		Assert.assertEquals(42, layout1.marginWidth);
		Assert.assertEquals(43, layout1.marginHeight);
		Assert.assertEquals(0, layout1.marginLeft);
		Assert.assertEquals(0, layout1.marginTop);
		Assert.assertEquals(0, layout1.marginRight);
		Assert.assertEquals(0, layout1.marginBottom);
		Assert.assertEquals(44, layout1.horizontalSpacing);
		Assert.assertEquals(45, layout1.verticalSpacing);

		GridLayout layout2 = GridLayoutBuilder.layout(42).margin(43, 44, 45, 46).get();

		Assert.assertEquals(42, layout2.numColumns);
		Assert.assertFalse(layout2.makeColumnsEqualWidth);
		Assert.assertEquals(5, layout2.marginWidth);
		Assert.assertEquals(5, layout2.marginHeight);
		Assert.assertEquals(43, layout2.marginLeft);
		Assert.assertEquals(44, layout2.marginTop);
		Assert.assertEquals(45, layout2.marginRight);
		Assert.assertEquals(46, layout2.marginBottom);

		GridLayout layout3 = GridLayoutBuilder.layout(42, true).get();

		Assert.assertEquals(42, layout3.numColumns);
		Assert.assertTrue(layout3.makeColumnsEqualWidth);
		Assert.assertEquals(5, layout3.marginWidth);
		Assert.assertEquals(5, layout3.marginHeight);
		Assert.assertEquals(0, layout3.marginLeft);
		Assert.assertEquals(0, layout3.marginTop);
		Assert.assertEquals(0, layout3.marginRight);
		Assert.assertEquals(0, layout3.marginBottom);

		GridData data1 = GridLayoutBuilder.data().align(SWT.LEFT, SWT.BOTTOM).minimumSize(42, 43).preferredSize(44, 45)
				.indent(46, 47).span(48, 49).grab(true, true).exclude(true).get();

		Assert.assertEquals(SWT.LEFT, data1.horizontalAlignment);
		Assert.assertEquals(SWT.BOTTOM, data1.verticalAlignment);
		Assert.assertEquals(42, data1.minimumWidth);
		Assert.assertEquals(43, data1.minimumHeight);
		Assert.assertEquals(44, data1.widthHint);
		Assert.assertEquals(45, data1.heightHint);
		Assert.assertEquals(46, data1.horizontalIndent);
		Assert.assertEquals(47, data1.verticalIndent);
		Assert.assertEquals(48, data1.horizontalSpan);
		Assert.assertEquals(49, data1.verticalSpan);
		Assert.assertTrue(data1.grabExcessHorizontalSpace);
		Assert.assertTrue(data1.grabExcessVerticalSpace);
		Assert.assertTrue(data1.exclude);

		GridData data2 = GridLayoutBuilder.data().get();

		Assert.assertEquals(SWT.BEGINNING, data2.horizontalAlignment);
		Assert.assertEquals(GridData.CENTER, data2.verticalAlignment);
		Assert.assertEquals(0, data2.minimumWidth);
		Assert.assertEquals(0, data2.minimumHeight);
		Assert.assertEquals(SWT.DEFAULT, data2.widthHint);
		Assert.assertEquals(SWT.DEFAULT, data2.heightHint);
		Assert.assertEquals(0, data2.horizontalIndent);
		Assert.assertEquals(0, data2.verticalIndent);
		Assert.assertEquals(1, data2.horizontalSpan);
		Assert.assertEquals(1, data2.verticalSpan);
		Assert.assertFalse(data2.grabExcessHorizontalSpace);
		Assert.assertFalse(data2.grabExcessVerticalSpace);
		Assert.assertFalse(data2.exclude);
	}

}
