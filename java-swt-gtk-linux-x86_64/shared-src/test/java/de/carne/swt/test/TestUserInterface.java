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
package de.carne.swt.test;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;

import de.carne.swt.ResourceException;
import de.carne.swt.graphics.ImageResourcePool;
import de.carne.swt.layout.FillLayoutBuilder;
import de.carne.swt.layout.RowLayoutBuilder;
import de.carne.swt.widgets.CompositeBuilder;
import de.carne.swt.widgets.CoolBarBuilder;
import de.carne.swt.widgets.MenuBuilder;
import de.carne.swt.widgets.TabFolderBuilder;
import de.carne.swt.widgets.ToolBarBuilder;
import de.carne.swt.widgets.UserInterface;
import de.carne.util.Late;

/**
 * Test user interface.
 */
public class TestUserInterface extends UserInterface<Shell> {

	private final Late<ImageResourcePool> imagePoolHolder = new Late<>();

	private final Late<Shell> rootHolder = new Late<>();
	private final Late<TabFolder> tabsHolder = new Late<>();

	@Override
	protected void build(Shell root) throws ResourceException {
		this.imagePoolHolder.set(new ImageResourcePool(root.getDisplay()));
		this.rootHolder.set(root);
		root.setImages(this.imagePoolHolder.get().getAll(Images.class, Images.IMAGES_A));
		root.setText(getClass().getSimpleName());

		TestUserController controller = new TestUserController();

		buildMenuBar(controller);
		this.tabsHolder.set(buildTabs(controller));

		FillLayoutBuilder.layout().margin(0, 0).spacing(0).apply(root);
	}

	private void buildMenuBar(TestUserController controller) throws ResourceException {
		MenuBuilder menu = MenuBuilder.menuBar(this.rootHolder);
		Image itemImage = this.imagePoolHolder.get().get(Images.class, Images.IMAGE_A_16);

		menu.addItem(SWT.CASCADE).withText("Menu A");
		menu.beginMenu();
		menu.addItem(SWT.PUSH).withText("Menu item A.1").withImage(itemImage);
		menu.onSelected(controller::onMenuItemSelected);
		menu.addItem(SWT.PUSH).withText("Menu item A.2").withImage(itemImage);
		menu.onSelected(controller::onMenuItemSelected);
		menu.endMenu();
		menu.addItem(SWT.CASCADE).withText("Menu B");
		menu.beginMenu();
		menu.addItem(SWT.PUSH).withText("Menu item B.1").withImage(itemImage);
		menu.onSelected(controller::onMenuItemSelected);
		menu.addItem(SWT.PUSH).withText("Menu item B.2").withImage(itemImage);
		menu.onSelected(controller::onMenuItemSelected);
		menu.endMenu();
	}

	private TabFolder buildTabs(TestUserController controller) throws ResourceException {
		TabFolderBuilder tabs = TabFolderBuilder.top(this.rootHolder.get(), SWT.NONE);
		Image itemImage = this.imagePoolHolder.get().get(Images.class, Images.IMAGE_A_16);

		tabs.addItem(SWT.NONE).withText("Tab 1").withImage(itemImage);
		tabs.withControl(buildTab1(tabs.get(), controller));
		return tabs.get();
	}

	private Control buildTab1(TabFolder tab, TestUserController controller) throws ResourceException {
		CompositeBuilder<Group> group = new CompositeBuilder<>(new Group(tab, SWT.NONE));
		CoolBarBuilder commands1 = CoolBarBuilder.horizontal(group, SWT.NONE);
		ToolBarBuilder commands1Tools = commands1.addToolBarChild(SWT.FLAT | SWT.HORIZONTAL);
		CoolBarBuilder commands2 = CoolBarBuilder.vertical(group, SWT.NONE);
		ToolBarBuilder commands2Tools = commands2.addToolBarChild(SWT.FLAT | SWT.VERTICAL);
		Image itemImage = this.imagePoolHolder.get().get(Images.class, Images.IMAGE_A_16);

		commands1.addItem(SWT.NONE);
		commands1Tools.addItem(SWT.PUSH).withImage(itemImage);
		commands1Tools.onSelected(controller::onMenuItemSelected);
		commands1Tools.addItem(SWT.PUSH).withImage(itemImage);
		commands1Tools.onSelected(controller::onMenuItemSelected);
		commands1.withControl(commands1Tools);
		commands1.pack();
		commands2.addItem(SWT.NONE);
		commands2Tools.addItem(SWT.PUSH).withImage(itemImage);
		commands2Tools.onSelected(controller::onMenuItemSelected);
		commands2Tools.addItem(SWT.PUSH).withImage(itemImage);
		commands2Tools.onSelected(controller::onMenuItemSelected);
		commands2.withControl(commands2Tools);
		commands2.pack();
		RowLayoutBuilder.layout(SWT.VERTICAL).apply(tab);
		RowLayoutBuilder.data().apply(commands1);
		RowLayoutBuilder.data().apply(commands2);
		return group.get();
	}

}
