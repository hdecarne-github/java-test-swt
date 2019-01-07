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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

/**
 * Accessor class for {@linkplain Menu} objects.
 */
public class MenuAccessor extends Accessor<Menu> {

	/**
	 * Constructs a new {@linkplain MenuAccessor} instance.
	 *
	 * @param menu the {@linkplain Menu} object to access.
	 */
	public MenuAccessor(Menu menu) {
		super(menu);
	}

	/**
	 * Gets all {@linkplain MenuItem}s of this {@linkplain Menu}.
	 * <p>
	 * Calling this function is equivalent to calling {@code items(Integer.MAX_VALUE, true)}.
	 * </p>
	 *
	 * @return all {@linkplain MenuItem}s of this {@linkplain Menu}.
	 * @see #items(int, boolean)
	 */
	public Stream<MenuItem> items() {
		return items(Integer.MAX_VALUE, true);
	}

	/**
	 * Gets all {@linkplain MenuItem}s of this {@linkplain Menu}.
	 *
	 * @param maxDepth the maximum number of sub-levels to search for items.
	 * @param depthsFirst whether to collect depths first or not.
	 * @return all {@linkplain MenuItem}s of this {@linkplain Menu}.
	 */
	public Stream<MenuItem> items(int maxDepth, boolean depthsFirst) {
		return collectItems(new ArrayList<>(), get(), 0, maxDepth, depthsFirst).stream();
	}

	private List<MenuItem> collectItems(List<MenuItem> items, Menu menu, int depth, int maxDepth, boolean depthsFirst) {
		if (depth <= maxDepth) {
			@NonNull MenuItem[] menuItems = menu.getItems();

			if (depthsFirst) {
				for (MenuItem menuItem : menuItems) {
					items.add(menuItem);
					collectItems(items, menuItem, depth + 1, maxDepth, depthsFirst);
				}
			} else {
				for (MenuItem menuItem : menuItems) {
					items.add(menuItem);
				}
				for (MenuItem menuItem : menuItems) {
					collectItems(items, menuItem, depth + 1, maxDepth, depthsFirst);
				}
			}
		}
		return items;
	}

	private List<MenuItem> collectItems(List<MenuItem> items, MenuItem item, int depth, int maxDepth,
			boolean depthsFirst) {
		if (depth <= maxDepth) {
			Menu itemMenu = item.getMenu();

			if (itemMenu != null) {
				collectItems(items, itemMenu, depth, maxDepth, depthsFirst);
			}
		}
		return items;
	}

	/**
	 * Convenience function which gets a specific {@linkplain MenuItem}.
	 * <p>
	 * A test failure is signaled if either none or more than one matching {@linkplain MenuItem} exists.
	 * </p>
	 *
	 * @param predicate the match criteria to use.
	 * @return the found {@linkplain MenuItem}.
	 */
	public MenuItemAccessor accessMenuItem(Predicate<MenuItem> predicate) {
		return Accessor.accessUnique(items().filter(predicate), MenuItemAccessor::new);
	}

}
