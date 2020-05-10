/*
 * Copyright (c) 2017-2020 Holger de Carne and contributors, All Rights Reserved.
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
package de.carne.test.swt.test.tester.accessor;

import java.util.Optional;

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.Decorations;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.carne.test.swt.tester.accessor.Accessor;
import de.carne.test.swt.tester.accessor.ButtonAccessor;
import de.carne.test.swt.tester.accessor.CompositeAccessor;
import de.carne.test.swt.tester.accessor.ControlAccessor;
import de.carne.test.swt.tester.accessor.CoolBarAccessor;
import de.carne.test.swt.tester.accessor.CoolItemAccessor;
import de.carne.test.swt.tester.accessor.DecorationsAccessor;
import de.carne.test.swt.tester.accessor.ItemAccessor;
import de.carne.test.swt.tester.accessor.MenuAccessor;
import de.carne.test.swt.tester.accessor.MenuItemAccessor;
import de.carne.test.swt.tester.accessor.ShellAccessor;
import de.carne.test.swt.tester.accessor.ToolBarAccessor;
import de.carne.test.swt.tester.accessor.ToolItemAccessor;

/**
 * Test {@linkplain Accessor} class and derived ones.
 */
class AccessorTest {

	@Test
	void testAccessor() {
		Object value = new Object();
		Optional<Object> optional = Optional.ofNullable(value);
		Accessor<Object> accessor1 = new Accessor<>(value);
		Accessor<Object> accessor2 = new Accessor<>(optional);
		Accessor<Object> accessor3 = new Accessor<>(accessor1);

		testAccessors(accessor1, accessor2, accessor3);
	}

	@Test
	void testControlAccessor() {
		Control value = null;
		Optional<Control> optional = Optional.ofNullable(value);
		ControlAccessor<Control> accessor1 = new ControlAccessor<>(value);
		ControlAccessor<Control> accessor2 = new ControlAccessor<>(optional);
		ControlAccessor<Control> accessor3 = new ControlAccessor<>(accessor1);

		testAccessors(accessor1, accessor2, accessor3);

		Assertions.assertEquals(Optional.empty(), accessor1.accessEnabled().getOptional());
	}

	@Test
	void testButtonAccessor() {
		Button value = null;
		Optional<Button> optional = Optional.ofNullable(value);
		ButtonAccessor accessor1 = new ButtonAccessor(value);
		ButtonAccessor accessor2 = new ButtonAccessor(optional);
		ButtonAccessor accessor3 = new ButtonAccessor(accessor1);

		testAccessors(accessor1, accessor2, accessor3);
	}

	@Test
	void testCompositeAccessor() {
		Composite value = null;
		Optional<Composite> optional = Optional.ofNullable(value);
		CompositeAccessor<Composite> accessor1 = new CompositeAccessor<>(value);
		CompositeAccessor<Composite> accessor2 = new CompositeAccessor<>(optional);
		CompositeAccessor<Composite> accessor3 = new CompositeAccessor<>(accessor1);

		testAccessors(accessor1, accessor2, accessor3);

		Assertions.assertEquals(0, accessor1.children().count());
		Assertions.assertEquals(Optional.empty(), accessor1.accessButton(0).getOptional());
	}

	@Test
	void testDecorationsAccessor() {
		Decorations value = null;
		Optional<Decorations> optional = Optional.ofNullable(value);
		DecorationsAccessor<Decorations> accessor1 = new DecorationsAccessor<>(value);
		DecorationsAccessor<Decorations> accessor2 = new DecorationsAccessor<>(optional);
		DecorationsAccessor<Decorations> accessor3 = new DecorationsAccessor<>(accessor1);

		testAccessors(accessor1, accessor2, accessor3);
	}

	@Test
	void testShellAccessor() {
		Shell value = null;
		Optional<Shell> optional = Optional.ofNullable(value);
		ShellAccessor accessor1 = new ShellAccessor(value);
		ShellAccessor accessor2 = new ShellAccessor(optional);
		ShellAccessor accessor3 = new ShellAccessor(accessor1);

		testAccessors(accessor1, accessor2, accessor3);

		Assertions.assertEquals(Optional.empty(), accessor1.accessMenuBar().accessItem(item -> true).getOptional());
	}

	@Test
	void testCoolBarAccessor() {
		CoolBar value = null;
		Optional<CoolBar> optional = Optional.ofNullable(value);
		CoolBarAccessor accessor1 = new CoolBarAccessor(value);
		CoolBarAccessor accessor2 = new CoolBarAccessor(optional);
		CoolBarAccessor accessor3 = new CoolBarAccessor(accessor1);

		testAccessors(accessor1, accessor2, accessor3);

		Assertions.assertEquals(0, accessor1.items().count());
		Assertions.assertEquals(Optional.empty(), accessor1.accessItem(item -> true).getOptional());
		Assertions.assertEquals(Optional.empty(), accessor1.accessItem(0).getOptional());
	}

	@Test
	void testToolBarAccessor() {
		ToolBar value = null;
		Optional<ToolBar> optional = Optional.ofNullable(value);
		ToolBarAccessor accessor1 = new ToolBarAccessor(value);
		ToolBarAccessor accessor2 = new ToolBarAccessor(optional);
		ToolBarAccessor accessor3 = new ToolBarAccessor(accessor1);

		testAccessors(accessor1, accessor2, accessor3);

		Assertions.assertEquals(0, accessor1.items().count());
		Assertions.assertEquals(Optional.empty(), accessor1.accessItem(item -> true).getOptional());
		Assertions.assertEquals(Optional.empty(), accessor1.accessItem(0).getOptional());
	}

	@Test
	void testItemAccessor() {
		Item value = null;
		Optional<Item> optional = Optional.ofNullable(value);
		ItemAccessor<Item> accessor1 = new ItemAccessor<>(value);
		ItemAccessor<Item> accessor2 = new ItemAccessor<>(optional);
		ItemAccessor<Item> accessor3 = new ItemAccessor<>(accessor1);

		testAccessors(accessor1, accessor2, accessor3);
	}

	@Test
	void testCoolItemAccessor() {
		CoolItem value = null;
		Optional<CoolItem> optional = Optional.ofNullable(value);
		CoolItemAccessor accessor1 = new CoolItemAccessor(value);
		CoolItemAccessor accessor2 = new CoolItemAccessor(optional);
		CoolItemAccessor accessor3 = new CoolItemAccessor(accessor1);

		testAccessors(accessor1, accessor2, accessor3);
	}

	@Test
	void testMenuItemAccessor() {
		MenuItem value = null;
		Optional<MenuItem> optional = Optional.ofNullable(value);
		MenuItemAccessor accessor1 = new MenuItemAccessor(value);
		MenuItemAccessor accessor2 = new MenuItemAccessor(optional);
		MenuItemAccessor accessor3 = new MenuItemAccessor(accessor1);

		testAccessors(accessor1, accessor2, accessor3);
	}

	@Test
	void testToolItemAccessor() {
		ToolItem value = null;
		Optional<ToolItem> optional = Optional.ofNullable(value);
		ToolItemAccessor accessor1 = new ToolItemAccessor(value);
		ToolItemAccessor accessor2 = new ToolItemAccessor(optional);
		ToolItemAccessor accessor3 = new ToolItemAccessor(accessor1);

		testAccessors(accessor1, accessor2, accessor3);
	}

	@Test
	void testMenuAccessor() {
		Menu value = null;
		Optional<Menu> optional = Optional.ofNullable(value);
		MenuAccessor accessor1 = new MenuAccessor(value);
		MenuAccessor accessor2 = new MenuAccessor(optional);
		MenuAccessor accessor3 = new MenuAccessor(accessor1);

		testAccessors(accessor1, accessor2, accessor3);

		Assertions.assertEquals(0, accessor1.items().count());
	}

	private <T> void testAccessors(Accessor<T> accessor1, Accessor<T> accessor2, Accessor<T> accessor3) {
		Assertions.assertEquals(accessor1.getOptional(), accessor2.getOptional());
		Assertions.assertEquals(accessor1.getOptional(), accessor3.getOptional());
	}

}
