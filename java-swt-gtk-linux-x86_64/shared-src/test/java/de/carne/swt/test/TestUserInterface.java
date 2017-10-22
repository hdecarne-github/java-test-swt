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
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;

import de.carne.swt.ResourceException;
import de.carne.swt.graphics.ImageResourcePool;
import de.carne.swt.layout.GridLayoutBuilder;
import de.carne.swt.layout.RowLayoutBuilder;
import de.carne.swt.widgets.CompositeBuilder;
import de.carne.swt.widgets.ControlBuilder;
import de.carne.swt.widgets.CoolBarBuilder;
import de.carne.swt.widgets.MenuBuilder;
import de.carne.swt.widgets.ShellBuilder;
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
	private final Late<Label> status = new Late<>();

	/**
	 * Set status text.
	 *
	 * @param status The status text to set.
	 */
	public void setStatus(String status) {
		this.status.get().setText(status);
	}

	@Override
	protected void build(Shell root) throws ResourceException {
		this.imagePoolHolder.set(new ImageResourcePool(root.getDisplay()));
		this.rootHolder.set(root);

		TestUserController controller = new TestUserController(this);
		ShellBuilder shell = new ShellBuilder(root);

		shell.withText(getClass().getSimpleName())
				.withImages(this.imagePoolHolder.get().getAll(Images.class, Images.IMAGES_A));
		shell.onDisposed(() -> {
			controller.onShellDisposed();
			this.imagePoolHolder.get().disposeAll();
		});
		shell.onShellActivated(controller::onShellActivated);
		buildMenuBar(controller);

		TabFolder tabs = buildTabs(controller);

		this.status.set(new Label(root, SWT.HORIZONTAL));
		GridLayoutBuilder.layout(1).margin(2, 2, 2, 2).spacing(2, 2).apply(root);
		GridLayoutBuilder.data().align(SWT.FILL, SWT.FILL).grab(true, true).apply(tabs);
		GridLayoutBuilder.data().align(SWT.FILL, SWT.BOTTOM).grab(true, false).apply(this.status);
		setStatus("UI ready...");
	}

	private void buildMenuBar(TestUserController controller) throws ResourceException {
		MenuBuilder menu = MenuBuilder.menuBar(this.rootHolder);
		Image itemImage = this.imagePoolHolder.get().get(Images.class, Images.IMAGE_A_16);

		menu.addItem(SWT.CASCADE).withText("Menu A");
		menu.beginMenu();
		menu.addItem(SWT.PUSH).withText("Menu item A.1").withImage(itemImage);
		menu.onSelected(controller::onCommandItemSelected);
		menu.addItem(SWT.PUSH).withText("Menu item A.2").withImage(itemImage);
		menu.onSelected(controller::onCommandItemSelected);
		menu.endMenu();
		menu.addItem(SWT.CASCADE).withText("Menu B");
		menu.beginMenu();
		menu.addItem(SWT.PUSH).withText("Menu item B.1").withImage(itemImage);
		menu.onSelected(controller::onCommandItemSelected);
		menu.addItem(SWT.PUSH).withText("Menu item B.2").withImage(itemImage);
		menu.onSelected(controller::onCommandItemSelected);
		menu.endMenu();
	}

	private TabFolder buildTabs(TestUserController controller) throws ResourceException {
		TabFolderBuilder tabs = TabFolderBuilder.top(this.rootHolder.get(), SWT.NONE);
		Image itemImage = this.imagePoolHolder.get().get(Images.class, Images.IMAGE_A_16);

		tabs.addItem(SWT.NONE).withText("Tab 1").withImage(itemImage);
		tabs.withControl(buildTab1(tabs.get(), controller));
		tabs.addItem(SWT.NONE).withText("Tab 2").withImage(itemImage);
		tabs.withControl(buildTab2(tabs.get(), controller));
		return tabs.get();
	}

	private Control buildTab1(TabFolder tab, TestUserController controller) throws ResourceException {
		CompositeBuilder<Group> group = new CompositeBuilder<>(new Group(tab, SWT.NONE));
		CoolBarBuilder commands = CoolBarBuilder.horizontal(group, SWT.NONE);
		ToolBarBuilder commandsTools = commands.addToolBarChild(SWT.FLAT | SWT.HORIZONTAL);
		ControlBuilder<Label> separator1 = group.addLabelChild(SWT.SEPARATOR | SWT.HORIZONTAL);
		Image itemImage = this.imagePoolHolder.get().get(Images.class, Images.IMAGE_A_16);

		commands.addItem(SWT.NONE);
		commandsTools.addItem(SWT.PUSH).withImage(itemImage);
		commandsTools.onSelected(controller::onCommandItemSelected);
		commandsTools.addItem(SWT.PUSH).withImage(itemImage);
		commandsTools.onSelected(controller::onCommandItemSelected);
		commands.withControl(commandsTools);
		commands.pack();
		RowLayoutBuilder.layout(SWT.VERTICAL).margin(0, 0).spacing(0).apply(tab);
		RowLayoutBuilder.data().apply(commands);
		RowLayoutBuilder.data().apply(separator1);
		return group.get();
	}

	private Control buildTab2(TabFolder tab, TestUserController controller) throws ResourceException {
		CompositeBuilder<Group> group = new CompositeBuilder<>(new Group(tab, SWT.NONE));
		CoolBarBuilder commands = CoolBarBuilder.vertical(group, SWT.NONE);
		ToolBarBuilder commandsTools = commands.addToolBarChild(SWT.FLAT | SWT.VERTICAL);
		ControlBuilder<Label> separator1 = group.addLabelChild(SWT.SEPARATOR | SWT.VERTICAL);
		Image itemImage = this.imagePoolHolder.get().get(Images.class, Images.IMAGE_A_16);

		commands.addItem(SWT.NONE);
		commandsTools.addItem(SWT.PUSH).withImage(itemImage);
		commandsTools.onSelected(controller::onCommandItemSelected);
		commandsTools.addItem(SWT.PUSH).withImage(itemImage);
		commandsTools.onSelected(controller::onCommandItemSelected);
		commands.withControl(commandsTools);
		commands.pack();
		RowLayoutBuilder.layout().margin(2, 2, 2, 2).spacing(2).wrap(true).apply(tab);
		RowLayoutBuilder.data().apply(commands);
		RowLayoutBuilder.data().apply(separator1);
		return group.get();
	}

}
