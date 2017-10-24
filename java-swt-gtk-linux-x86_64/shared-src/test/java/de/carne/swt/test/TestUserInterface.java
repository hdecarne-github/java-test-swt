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
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.Tree;
import org.junit.Assert;

import de.carne.swt.graphics.ImageResourcePool;
import de.carne.swt.graphics.ResourceException;
import de.carne.swt.layout.FormLayoutBuilder;
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
		shell.onDisposed(controller::onShellDisposeEvent);
		shell.onShellActivated(controller::onShellActivated);
		shell.onShellActivated(controller::onShellEventEvent);
		shell.onShellDeactivated(controller::onShellEvent);
		shell.onShellDeactivated(controller::onShellEventEvent);
		shell.onShellIconified(controller::onShellEvent);
		shell.onShellIconified(controller::onShellEventEvent);
		shell.onShellDeiconified(controller::onShellEvent);
		shell.onShellDeiconified(controller::onShellEventEvent);
		shell.onShellClosed(controller::onShellEvent);
		shell.onShellClosed(controller::onShellEventEvent);
		buildMenuBar(controller);

		TabFolder tabs = buildTabs(controller);

		this.status.set(new Label(root, SWT.HORIZONTAL));
		GridLayoutBuilder.layout(1).margin(2, 2, 2, 2).spacing(2, 2).apply(root);
		GridLayoutBuilder.data().align(SWT.FILL, SWT.FILL).grab(true, true).apply(tabs);
		GridLayoutBuilder.data().align(SWT.FILL, SWT.BOTTOM).grab(true, false).apply(this.status);
		setStatus("UI ready...");
		root.layout();
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

		Assert.assertNotNull(tabs.currentItem());

		tabs.withControl(buildTab1(tabs.get(), controller));
		tabs.addItem(SWT.NONE).withText("Tab 2").withImage(itemImage);
		tabs.withControl(buildTab2(tabs.get(), controller));
		tabs.addItem(SWT.NONE).withText("Tab 3").withImage(itemImage);
		tabs.withControl(buildTab3(tabs.get()));
		return tabs.get();
	}

	private Control buildTab1(TabFolder tab, TestUserController controller) throws ResourceException {
		CompositeBuilder<Group> group = new CompositeBuilder<>(new Group(tab, SWT.NONE));
		CoolBarBuilder commands = CoolBarBuilder.horizontal(group, SWT.NONE);
		ToolBarBuilder commandsTools = ToolBarBuilder.horizontal(commands, SWT.FLAT);
		ControlBuilder<Label> separator1 = group.addLabelChild(SWT.SEPARATOR | SWT.HORIZONTAL);
		Image itemImage = this.imagePoolHolder.get().get(Images.class, Images.IMAGE_A_16);

		commands.addItem(SWT.NONE);

		Assert.assertNotNull(commands.currentItem());

		commandsTools.addItem(SWT.PUSH).withImage(itemImage);

		Assert.assertNotNull(commandsTools.currentItem());

		commandsTools.onSelected(controller::onCommandItemSelectionEvent);
		commandsTools.addItem(SWT.PUSH).withImage(itemImage);
		commandsTools.onSelected(controller::onCommandItemSelectionEvent);
		commands.withControl(commandsTools);
		commands.pack();
		RowLayoutBuilder.layout(SWT.VERTICAL).margin(0, 0).spacing(0).apply(group);
		RowLayoutBuilder.data().apply(commands);
		RowLayoutBuilder.data().apply(separator1);
		return group.get();
	}

	private Control buildTab2(TabFolder tab, TestUserController controller) throws ResourceException {
		CompositeBuilder<Group> group = new CompositeBuilder<>(new Group(tab, SWT.NONE));
		CoolBarBuilder commands = CoolBarBuilder.vertical(group, SWT.NONE);
		ToolBarBuilder commandsTools = ToolBarBuilder.vertical(commands, SWT.FLAT);
		ControlBuilder<Label> separator1 = group.addLabelChild(SWT.SEPARATOR | SWT.VERTICAL);
		Image itemImage = this.imagePoolHolder.get().get(Images.class, Images.IMAGE_A_16);

		commands.addItem(SWT.NONE);
		commandsTools.addItem(SWT.PUSH).withText("1").withImage(itemImage).withDisabledImage(itemImage);
		commandsTools.onSelected(controller::onCommandItemSelected);
		commandsTools.addItem(SWT.PUSH).withText("2").withImage(itemImage).withDisabledImage(itemImage);
		commandsTools.onSelected(controller::onCommandItemSelected);
		commands.withControl(commandsTools);
		commands.addItem(SWT.DROP_DOWN);
		commands.withText("3").withImage(itemImage);
		commands.onSelected(controller::onCommandItemSelectionEvent);
		commands.lock(true).pack();
		RowLayoutBuilder.layout().margin(2, 2, 2, 2).spacing(2).wrap(true).apply(tab);
		RowLayoutBuilder.data().apply(commands);
		RowLayoutBuilder.data().apply(separator1);
		return group.get();
	}

	private Control buildTab3(TabFolder tab) {
		CompositeBuilder<Group> group = new CompositeBuilder<>(new Group(tab, SWT.NONE));
		ControlBuilder<Tree> control11 = group.addTreeChild(SWT.SINGLE);
		ControlBuilder<List> control12 = group.addListChild(SWT.SINGLE);
		ControlBuilder<Tree> control21 = group.addTreeChild(SWT.SINGLE);
		ControlBuilder<List> control22 = group.addListChild(SWT.SINGLE);
		ControlBuilder<Label> hSeparator = group.addLabelChild(SWT.SEPARATOR | SWT.HORIZONTAL);
		ControlBuilder<Label> vSeparator = group.addLabelChild(SWT.SEPARATOR | SWT.VERTICAL);

		FormLayoutBuilder.layout().apply(group);
		FormLayoutBuilder.data().left(0).top(0).right(vSeparator).bottom(hSeparator, 0).apply(control11);
		FormLayoutBuilder.data().left(vSeparator, 0, SWT.LEFT).top(0, 0).right(100).bottom(hSeparator).apply(control12);
		FormLayoutBuilder.data().left(0, 100, 0).top(hSeparator).right(vSeparator).bottom(100).apply(control21);
		FormLayoutBuilder.data().left(vSeparator).top(hSeparator).right(100).bottom(100).apply(control22);
		FormLayoutBuilder.data().left(0).top(30).right(100).apply(hSeparator);
		FormLayoutBuilder.data().left(30).top(0).bottom(100).apply(vSeparator);
		return group.get();
	}

}
