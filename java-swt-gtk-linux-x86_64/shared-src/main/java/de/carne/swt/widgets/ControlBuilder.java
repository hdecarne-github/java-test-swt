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

import java.util.function.Supplier;

import org.eclipse.swt.widgets.Control;

/**
 * {@linkplain Control} builder.
 *
 * @param <T> The actual control type.
 */
public class ControlBuilder<T extends Control> implements Supplier<T> {

	private final T control;

	/**
	 * Construct {@linkplain ControlBuilder}.
	 *
	 * @param control The control to build.
	 */
	public ControlBuilder(T control) {
		this.control = control;
	}

	/**
	 * Get the {@linkplain Control} the builder operates on.
	 *
	 * @return The {@linkplain Control} the builder operates on.
	 */
	@Override
	public T get() {
		return this.control;
	}

	/**
	 * Resize the {@linkplain Control} to it's preferred size.
	 *
	 * @return The updated builder.
	 * @see Control#pack()
	 */
	public ControlBuilder<T> pack() {
		this.control.pack(true);
		return this;
	}

	/**
	 * Set the {@linkplain Control}'s layout data.
	 *
	 * @param layoutData The layout data to set.
	 * @see Control#setLayoutData(Object)
	 */
	public void setLayoutData(Object layoutData) {
		this.control.setLayoutData(layoutData);
	}

}
