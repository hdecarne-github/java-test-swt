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

import java.util.Deque;
import java.util.LinkedList;
import java.util.function.Consumer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import de.carne.check.Nullable;
import de.carne.swt.events.EventConsumer;

/**
 * Menu builder.
 */
public final class MenuBuilder {

	private Deque<Menu> menuStack = new LinkedList<>();
	@Nullable
	private MenuItem currentItem = null;

	private MenuBuilder(Menu menu) {
		this.menuStack.push(menu);
	}

	/**
	 * Build a {@linkplain Shell}s menu bar.
	 *
	 * @param shell The {@linkplain Shell} to build the menu bar for.
	 * @return The new {@linkplain MenuBuilder}.
	 */
	public static MenuBuilder menuBar(Shell shell) {
		Menu menu = new Menu(shell, SWT.BAR);

		shell.setMenuBar(menu);
		return new MenuBuilder(menu);
	}

	/**
	 * Build a pop up menu.
	 *
	 * @param parent The {@linkplain Control} owning the the menu.
	 * @return The new {@linkplain MenuBuilder}.
	 */
	public static MenuBuilder popupMenu(Control parent) {
		return new MenuBuilder(new Menu(parent));
	}

	/**
	 * Get the {@linkplain Menu} the builder operates on at this state.
	 *
	 * @return The {@linkplain Menu} the builder operates on at this state.
	 */
	public Menu currentMenu() {
		return this.menuStack.peek();
	}

	/**
	 * Get the {@linkplain MenuItem} the builder operates on at this state.
	 *
	 * @return The {@linkplain MenuItem} the builder operates on at this state.
	 */
	public MenuItem currentItem() {
		return checkCurrentItem(this.currentItem);
	}

	/**
	 * Start building a new sub menu.
	 * <p>
	 * All subsequent calls will operate on the new sub menu.
	 *
	 * @return The updated {@linkplain MenuBuilder}.
	 */
	public MenuBuilder beginMenu() {
		Menu menu = new Menu(this.menuStack.peek());
		MenuItem item = this.currentItem;

		if (item != null) {
			item.setMenu(menu);
		}
		this.menuStack.push(menu);
		return this;
	}

	/**
	 * Finish building the current sub menu.
	 * <p>
	 * All subsequent calls will operate on the sub menu's parent.
	 *
	 * @return The updated {@linkplain MenuBuilder}.
	 */
	public MenuBuilder endMenu() {
		this.currentItem = null;
		this.menuStack.pop();

		Menu menu = this.menuStack.peek();

		if (menu != null) {
			int itemCount = menu.getItemCount();

			if (itemCount > 0) {
				this.currentItem = menu.getItem(itemCount - 1);
			}
		}
		return this;
	}

	/**
	 * Add a {@linkplain MenuItem} to the current {@linkplain Menu}.
	 * <p>
	 * All subsequent calls will operate on the new menu item.
	 *
	 * @param style The style of the {@linkplain MenuItem} to add.
	 * @return The updated {@linkplain MenuBuilder}.
	 */
	public MenuBuilder addItem(int style) {
		this.currentItem = new MenuItem(this.menuStack.peek(), style);
		return this;
	}

	/**
	 * Set the current menu item's text.
	 *
	 * @param text The text to set.
	 * @return The updated {@linkplain MenuBuilder}.
	 * @see MenuItem#setText(String)
	 */
	public MenuBuilder withText(String text) {
		checkCurrentItem(this.currentItem).setText(text);
		return this;
	}

	/**
	 * Set the current menu item's {@linkplain Image}.
	 *
	 * @param image The image to set.
	 * @return The updated {@linkplain MenuBuilder}.
	 * @see MenuItem#setImage(Image)
	 */
	public MenuBuilder withImage(Image image) {
		checkCurrentItem(this.currentItem).setImage(image);
		return this;
	}

	/**
	 * Set {@linkplain SelectionEvent} action.
	 * 
	 * @param action The action to set.
	 * @return The updated {@linkplain MenuBuilder}.
	 * @see MenuItem#addSelectionListener(org.eclipse.swt.events.SelectionListener)
	 */
	public MenuBuilder onSelected(Consumer<SelectionEvent> action) {
		MenuItem item = checkCurrentItem(this.currentItem);
		EventConsumer<SelectionEvent> listener = EventConsumer.selected(action);

		item.addListener(SWT.Selection, listener);
		item.addListener(SWT.DefaultSelection, listener);
		return this;
	}

	private static MenuItem checkCurrentItem(@Nullable MenuItem currentItem) {
		if (currentItem == null) {
			throw new IllegalStateException("Current menu item is not defined");
		}
		return currentItem;
	}

}
