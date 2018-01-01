/*
 * Copyright (c) 2017-2018 Holger de Carne and contributors, All Rights Reserved.
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
package de.carne.test.swt.tester;

import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import de.carne.check.Nullable;

/**
 * Class providing access to the application's {@linkplain Menu} objects.
 */
public class MenuAccessor extends Accessor<Menu> {

	/**
	 * Construct {@linkplain MenuAccessor}.
	 *
	 * @param menu The {@linkplain Menu} object to wrap.
	 */
	public MenuAccessor(Menu menu) {
		super(menu);
	}

	/**
	 * Get the accessor for a specific {@linkplain MenuItem}.
	 * <p>
	 * If no menu item is found for the given name, this function signals a test failure.
	 *
	 * @param text The text of the {@linkplain MenuItem} to get.
	 * @return The accessor for the found object.
	 */
	public MenuItemAccessor item(String text) {
		MenuItem foundItem = Accessor.notNull(findMenuItem(get(), text), "Menu item not found: " + text);

		return new MenuItemAccessor(foundItem);
	}

	@Nullable
	private MenuItem findMenuItem(Menu menu, String text) {
		MenuItem foundItem = null;

		for (MenuItem item : menu.getItems()) {
			if (foundItem != null) {
				break;
			}
			if (text.equals(item.getText())) {
				foundItem = item;
			} else {
				Menu subMenu = item.getMenu();

				if (subMenu != null) {
					foundItem = findMenuItem(subMenu, text);
				}
			}
		}
		return foundItem;
	}

}
