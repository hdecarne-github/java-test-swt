/*
 * Copyright (c) 2007-2018 Holger de Carne and contributors, All Rights Reserved.
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
import org.junit.jupiter.api.Assertions;

import de.carne.swt.graphics.ResourceException;
import de.carne.swt.layout.FormLayoutBuilder;
import de.carne.swt.layout.GridLayoutBuilder;
import de.carne.swt.layout.RowLayoutBuilder;
import de.carne.swt.widgets.CompositeBuilder;
import de.carne.swt.widgets.ControlBuilder;
import de.carne.swt.widgets.CoolBarBuilder;
import de.carne.swt.widgets.MenuBuilder;
import de.carne.swt.widgets.ShellBuilder;
import de.carne.swt.widgets.ShellUserInterface;
import de.carne.swt.widgets.TabFolderBuilder;
import de.carne.swt.widgets.ToolBarBuilder;
import de.carne.util.Late;

/**
 * Test user interface.
 */
public class TestUserInterface extends ShellUserInterface {

	private final Late<Label> status = new Late<>();

	/**
	 * Constructs a new {@linkplain TestUserInterface} instance.
	 *
	 * @param shell the user interface {@linkplain Shell}.
	 */
	public TestUserInterface(Shell shell) {
		super(shell);
	}

	/**
	 * Set status text.
	 *
	 * @param status The status text to set.
	 */
	public void setStatus(String status) {
		this.status.get().setText(status);
	}

	@Override
	public void open() throws ResourceException {
		Shell shell = root();

		TestUserController controller = new TestUserController(this);
		ShellBuilder shellBuilder = new ShellBuilder(shell);

		shellBuilder.withText(getClass().getSimpleName())
				.withImages(Images.getImages(shell.getDisplay(), Images.IMAGES_A));
		shellBuilder.onDisposed(controller::onShellDisposed);
		shellBuilder.onDisposed(controller::onShellDisposeEvent);
		shellBuilder.onShellActivated(controller::onShellActivated);
		shellBuilder.onShellActivated(controller::onShellEventEvent);
		shellBuilder.onShellDeactivated(controller::onShellEvent);
		shellBuilder.onShellDeactivated(controller::onShellEventEvent);
		shellBuilder.onShellIconified(controller::onShellEvent);
		shellBuilder.onShellIconified(controller::onShellEventEvent);
		shellBuilder.onShellDeiconified(controller::onShellEvent);
		shellBuilder.onShellDeiconified(controller::onShellEventEvent);
		shellBuilder.onShellClosed(controller::onShellEvent);
		shellBuilder.onShellClosed(controller::onShellEventEvent);
		buildMenuBar(controller);

		TabFolder tabs = buildTabs(controller);

		this.status.set(new Label(shell, SWT.HORIZONTAL));
		GridLayoutBuilder.layout(1).margin(2, 2, 2, 2).spacing(2, 2).apply(shell);
		GridLayoutBuilder.data().align(SWT.FILL, SWT.FILL).grab(true, true).apply(tabs);
		GridLayoutBuilder.data().align(SWT.FILL, SWT.BOTTOM).grab(true, false).apply(this.status);
		setStatus("UI ready...");
		shell.layout();
		shell.open();
	}

	private void buildMenuBar(TestUserController controller) throws ResourceException {
		MenuBuilder menu = MenuBuilder.menuBar(root());
		Image itemImage = Images.getImage(root().getDisplay(), Images.IMAGE_A_16);

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
		TabFolderBuilder tabs = TabFolderBuilder.top(root(), SWT.NONE);
		Image itemImage = Images.getImage(root().getDisplay(), Images.IMAGE_A_16);

		tabs.addItem(SWT.NONE).withText("Tab 1").withImage(itemImage);

		Assertions.assertNotNull(tabs.currentItem());

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
		ControlBuilder<Label> separator1 = group.addControlChild(Label.class, SWT.SEPARATOR | SWT.HORIZONTAL);
		Image itemImage = Images.getImage(root().getDisplay(), Images.IMAGE_A_16);

		commands.addItem(SWT.NONE);

		Assertions.assertNotNull(commands.currentItem());

		commandsTools.addItem(SWT.PUSH).withImage(itemImage);

		Assertions.assertNotNull(commandsTools.currentItem());

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
		ControlBuilder<Label> separator1 = group.addControlChild(Label.class, SWT.SEPARATOR | SWT.VERTICAL);
		Image itemImage = Images.getImage(root().getDisplay(), Images.IMAGE_A_16);

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
		ControlBuilder<Tree> control11 = group.addControlChild(Tree.class, SWT.SINGLE);
		ControlBuilder<List> control12 = group.addControlChild(List.class, SWT.SINGLE);
		ControlBuilder<Tree> control21 = group.addControlChild(Tree.class, SWT.SINGLE);
		ControlBuilder<List> control22 = group.addControlChild(List.class, SWT.SINGLE);
		ControlBuilder<Label> hSeparator = group.addControlChild(Label.class, SWT.SEPARATOR | SWT.HORIZONTAL);
		ControlBuilder<Label> vSeparator = group.addControlChild(Label.class, SWT.SEPARATOR | SWT.VERTICAL);

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
