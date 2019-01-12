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
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.ToolItem;

/**
 * Accessor class for {@linkplain ToolItem} objects.
 */
public class ToolItemAccessor extends ItemAccessor<ToolItem> {

	/**
	 * Constructs a new {@linkplain ToolItemAccessor} instance.
	 *
	 * @param toolItem the {@linkplain ToolItem} object to access.
	 */
	public ToolItemAccessor(@Nullable ToolItem toolItem) {
		super(toolItem);
	}

	/**
	 * Constructs a new {@linkplain ToolItemAccessor} instance.
	 *
	 * @param optionalToolItem the optional {@linkplain ToolItem} object to access.
	 */
	public ToolItemAccessor(Optional<ToolItem> optionalToolItem) {
		super(optionalToolItem);
	}

	/**
	 * Generate a selection event to the {@linkplain ToolItem}.
	 */
	public void select() {
		get().notifyListeners(SWT.Selection, new Event());
	}

}
