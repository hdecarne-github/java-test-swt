/*
 * Copyright (c) 2007-2019 Holger de Carne and contributors, All Rights Reserved.
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
package de.carne.swt.test.app;

import java.net.URL;
import java.util.Objects;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.junit.jupiter.api.Assertions;

import de.carne.swt.graphics.ResourceException;
import de.carne.swt.test.app.resources.Resources;
import de.carne.swt.widgets.MenuBuilder;
import de.carne.swt.widgets.ShellBuilder;
import de.carne.swt.widgets.ShellUserInterface;
import de.carne.swt.widgets.aboutinfo.AboutInfoDialog;

/**
 * Test application root shell user interface.
 */
public class TestAppUserInterface extends ShellUserInterface {

	/**
	 * Constructs a new {@linkplain TestAppUserInterface} instance.
	 *
	 * @param shell the user interface {@linkplain Shell}.
	 */
	protected TestAppUserInterface(Shell shell) {
		super(shell);
	}

	@Override
	public void open() throws ResourceException {
		Shell shell = root();
		ShellBuilder shellBuilder = new ShellBuilder(shell);

		shellBuilder.withText(TestApp.ROOT_TEXT)
				.withImages(Resources.getImages(shell.getDisplay(), Resources.APP_ICON));
		buildMenuBar();
		shell.layout();
		shell.open();
	}

	private void buildMenuBar() {
		MenuBuilder menuBarBuilder = MenuBuilder.menuBar(root());

		menuBarBuilder.addItem(SWT.CASCADE).withText(TestApp.ROOT_MENU_SHELL);
		menuBarBuilder.beginMenu();
		menuBarBuilder.addItem(SWT.PUSH).withText(TestApp.ROOT_MENU_SHELL_CLOSE).onSelected(this::onShellClose);
		menuBarBuilder.endMenu();
		menuBarBuilder.addItem(SWT.CASCADE).withText(TestApp.ROOT_MENU_WIDGETS);
		menuBarBuilder.beginMenu();
		menuBarBuilder.addItem(SWT.PUSH).withText(TestApp.ROOT_MENU_WIDGETS_ABOUTINFO).onSelected(this::onAbout);
		menuBarBuilder.endMenu();
	}

	private void onShellClose() {
		root().close();
	}

	private void onAbout() {
		AboutInfoDialog about = new AboutInfoDialog(root());

		try {
			URL logoUrl = Objects.requireNonNull(Resources.class.getResource(Resources.APP_ICON48));
			URL copyrightUrl = Objects.requireNonNull(Resources.class.getResource(Resources.ABOUT_TEXT));

			about.withLogo(logoUrl).withCopyright(copyrightUrl).open();
		} catch (Exception e) {
			Assertions.fail(e);
		}
	}

}
