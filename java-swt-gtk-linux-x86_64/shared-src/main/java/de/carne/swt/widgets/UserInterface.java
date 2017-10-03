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

import org.eclipse.swt.widgets.Composite;

import de.carne.swt.ResourceException;
import de.carne.swt.events.UserController;
import de.carne.util.Late;

/**
 * Base class for SWT based user interfaces.
 *
 * @param <W> The root widget's type.
 * @param <C> The user interface's controller type.
 */
public abstract class UserInterface<W extends Composite, C extends UserController> {

	private final Late<W> rootHolder = new Late<>();
	private final Late<C> controllerHolder = new Late<>();

	/**
	 * Setup the interface and accompanying objects.
	 *
	 * @param root The interface's root widget.
	 * @throws ResourceException If a required resource is not available.
	 */
	public void setup(W root) throws ResourceException {
		this.rootHolder.set(root);
		this.controllerHolder.set(build());
	}

	/**
	 * Get the interface's root widget.
	 *
	 * @return The interface's root widget.
	 */
	public W root() {
		return this.rootHolder.get();
	}

	/**
	 * Get the interface's controller.
	 *
	 * @return The interface's controller.
	 */
	public C controller() {
		return this.controllerHolder.get();
	}

	/**
	 * Build up the interface's widgets and initialize the interface's controller.
	 *
	 * @return The interface's controller.
	 * @throws ResourceException if a required resource is not available.
	 */
	protected abstract C build() throws ResourceException;

}
