/*
 * Copyright (c) 2017-2019 Holger de Carne and contributors, All Rights Reserved.
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
package de.carne.test.swt.tester.accessor;

import org.eclipse.swt.widgets.Shell;

/**
 * Class providing access to the application's {@linkplain Shell} objects.
 */
public class ShellAccessor extends DecorationsAccessor<Shell> {

	/**
	 * Constructs a new {@linkplain ShellAccessor}.
	 *
	 * @param shell the {@linkplain Shell} object to wrap.
	 */
	public ShellAccessor(Shell shell) {
		super(shell);
	}

	/**
	 * Gets this {@linkplain Shell}'s menu bar accessor.
	 *
	 * @return this {@linkplain Shell}'s menu bar accessor.
	 */
	public MenuAccessor menuBar() {
		return new MenuAccessor(Accessor.accessNonNull(get().getMenuBar(), "Menu bar not found"));
	}

}
