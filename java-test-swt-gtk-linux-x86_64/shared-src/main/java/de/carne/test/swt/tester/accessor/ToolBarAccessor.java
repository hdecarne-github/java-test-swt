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
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

/**
 * Accessor class for {@linkplain ToolBar} objects.
 */
public class ToolBarAccessor extends ControlAccessor<ToolBar> {

	/**
	 * Constructs a new {@linkplain ToolBarAccessor} instance.
	 *
	 * @param toolBar the {@linkplain ToolBar} object to access.
	 */
	public ToolBarAccessor(@Nullable ToolBar toolBar) {
		super(toolBar);
	}

	/**
	 * Constructs a new {@linkplain ToolBarAccessor} instance.
	 *
	 * @param toolBarHolder the optional {@linkplain ToolBar} object to access.
	 */
	public ToolBarAccessor(Optional<ToolBar> toolBarHolder) {
		super(toolBarHolder);
	}

	/**
	 * Wraps a {@linkplain ToolBar} object for further accessor based processing.
	 *
	 * @param optionalToolBar the optional {@linkplain ToolBar} object to wrap.
	 * @return the wrapped optional {@linkplain ToolBar} object.
	 */
	public static ToolBarAccessor wrapToolBar(Optional<ToolBar> optionalToolBar) {
		return new ToolBarAccessor(optionalToolBar);
	}

	/**
	 * Convenience function which gets a specific {@linkplain ToolItem} from this {@linkplain ToolBar}.
	 * <p>
	 * A test failure is signaled if the {@linkplain ToolItem} does not exist.
	 * </p>
	 *
	 * @param itemIndex the item index to get.
	 * @return the found {@linkplain ToolItem}.
	 */
	public ToolItemAccessor accessToolItem(int itemIndex) {
		Optional<? extends ToolBar> optionalToolBar = getOptional();

		return new ToolItemAccessor(optionalToolBar.isPresent() ? getToolItem(optionalToolBar.get(), itemIndex) : null);
	}

	private @Nullable ToolItem getToolItem(ToolBar toolBar, int itemIndex) {
		return (0 <= itemIndex && itemIndex < toolBar.getItemCount() ? toolBar.getItem(itemIndex) : null);
	}

}
