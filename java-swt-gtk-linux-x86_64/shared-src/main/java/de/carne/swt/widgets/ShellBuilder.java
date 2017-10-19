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
package de.carne.swt.widgets;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;

/**
 * {@linkplain Shell} builder.
 */
public class ShellBuilder extends CompositeBuilder<Shell> {

	/**
	 * Construct {@linkplain ShellBuilder}.
	 *
	 * @param shell The {@linkplain Shell} widget to build up.
	 */
	public ShellBuilder(Shell shell) {
		super(shell);
	}

	/**
	 * Set the {@linkplain Shell}'s text.
	 *
	 * @param text The text to set.
	 * @return The updated builder.
	 * @see Shell#setText(String)
	 */
	public ShellBuilder withText(String text) {
		get().setText(text);
		return this;
	}

	/**
	 * Set {@linkplain Shell}'s images.
	 *
	 * @param images The {@linkplain Image}s to set.
	 * @return The updated builder.
	 * @see Shell#setImages(Image[])
	 */
	public ShellBuilder withImages(Image[] images) {
		get().setImages(images);
		return this;
	}

}
