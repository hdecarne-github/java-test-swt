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

import java.util.Optional;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Event;

/**
 * Accessor class for {@linkplain CoolItem} objects.
 */
public class CoolItemAccessor extends ItemAccessor<CoolItem> {

	/**
	 * Constructs a new {@linkplain CoolItemAccessor} instance.
	 *
	 * @param coolItem the {@linkplain CoolItem} object to access.
	 */
	public CoolItemAccessor(@Nullable CoolItem coolItem) {
		super(coolItem);
	}

	/**
	 * Constructs a new {@linkplain CoolItemAccessor} instance.
	 *
	 * @param optionalCoolItem the optional {@linkplain CoolItem} object to access.
	 */
	public CoolItemAccessor(Optional<CoolItem> optionalCoolItem) {
		super(optionalCoolItem);
	}

	/**
	 * Generate a selection event to the {@linkplain CoolItem}.
	 */
	public void select() {
		get().notifyListeners(SWT.Selection, new Event());
	}

}
