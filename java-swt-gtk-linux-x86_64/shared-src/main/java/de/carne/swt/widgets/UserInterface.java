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
package de.carne.swt.widgets;

import org.eclipse.swt.widgets.Composite;

import de.carne.swt.graphics.ResourceException;
import de.carne.util.Late;

/**
 * Base class for SWT based user interfaces.
 *
 * @param <R> The root widget's type.
 */
public abstract class UserInterface<R extends Composite> {

	private final Late<R> rootHolder = new Late<>();

	/**
	 * Set up the interface and accompanying objects.
	 *
	 * @param root The interface's root widget.
	 * @throws ResourceException if a required resource is not available.
	 */
	public void setup(R root) throws ResourceException {
		build(this.rootHolder.set(root));
	}

	/**
	 * Build up the interface's widgets and initialize accompanying objects.
	 *
	 * @param root The interface's root widget.
	 * @throws ResourceException if a required resource is not available.
	 */
	protected abstract void build(R root) throws ResourceException;

	/**
	 * Get the interface's root widget.
	 *
	 * @return The interface's root widget.
	 */
	public R root() {
		return this.rootHolder.get();
	}

}
