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

import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.junit.Assert;
import org.junit.Test;

import de.carne.swt.layout.FormLayoutBuilder;

/**
 * Test {@linkplain FormLayoutBuilder} class.
 */
public class FormLayoutBuilderTest {

	/**
	 * Test {@linkplain FormLayoutBuilder} class.
	 */
	@Test
	public void testFormLayoutBuilder() {
		FormLayout layout1 = FormLayoutBuilder.layout().margin(42, 43).spacing(44).get();

		Assert.assertEquals(42, layout1.marginWidth);
		Assert.assertEquals(43, layout1.marginHeight);
		Assert.assertEquals(0, layout1.marginLeft);
		Assert.assertEquals(0, layout1.marginTop);
		Assert.assertEquals(0, layout1.marginRight);
		Assert.assertEquals(0, layout1.marginBottom);
		Assert.assertEquals(44, layout1.spacing);

		FormLayout layout2 = FormLayoutBuilder.layout().margin(42, 43, 44, 45).get();

		Assert.assertEquals(0, layout2.marginWidth);
		Assert.assertEquals(0, layout2.marginHeight);
		Assert.assertEquals(42, layout2.marginLeft);
		Assert.assertEquals(43, layout2.marginTop);
		Assert.assertEquals(44, layout2.marginRight);
		Assert.assertEquals(45, layout2.marginBottom);
		Assert.assertEquals(0, layout2.spacing);

		FormData data1 = FormLayoutBuilder.data().preferredSize(42, 43).get();

		Assert.assertEquals(42, data1.width);
		Assert.assertEquals(43, data1.height);

		FormData data2 = FormLayoutBuilder.data().left(42).top(43).right(44).bottom(45).get();

		verifyAttachment(data2.left, 42, 100, 0);
		verifyAttachment(data2.top, 43, 100, 0);
		verifyAttachment(data2.right, 44, 100, 0);
		verifyAttachment(data2.bottom, 45, 100, 0);

		FormData data3 = FormLayoutBuilder.data().left(42, 43).top(44, 45).right(46, 47).bottom(48, 49).get();

		verifyAttachment(data3.left, 42, 100, 43);
		verifyAttachment(data3.top, 44, 100, 45);
		verifyAttachment(data3.right, 46, 100, 47);
		verifyAttachment(data3.bottom, 48, 100, 49);

		FormData data4 = FormLayoutBuilder.data().left(42, 43, 44).top(45, 46, 47).right(48, 49, 50).bottom(51, 52, 53)
				.get();

		verifyAttachment(data4.left, 42, 43, 44);
		verifyAttachment(data4.top, 45, 46, 47);
		verifyAttachment(data4.right, 48, 49, 50);
		verifyAttachment(data4.bottom, 51, 52, 53);
	}

	private void verifyAttachment(FormAttachment attachment, int numerator, int denominator, int offset) {
		Assert.assertEquals(numerator, attachment.numerator);
		Assert.assertEquals(denominator, attachment.denominator);
		Assert.assertEquals(offset, attachment.offset);
	}

}
