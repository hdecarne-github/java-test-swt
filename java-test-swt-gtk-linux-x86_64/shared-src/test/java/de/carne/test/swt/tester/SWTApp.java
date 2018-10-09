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

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import de.carne.util.Late;

class SWTApp implements Runnable {

	public static final String TITLE_SHELL = "Shell";
	public static final String TITLE_MENU_APP = "App";
	public static final String TITLE_MENU_APP_QUIT = "Quit";

	private Late<Shell> shellHolder = new Late<>();

	@Override
	public void run() {
		Display display = new Display();
		Shell shell = this.shellHolder.set(new Shell(display));

		setupShell();
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	private void setupShell() {
		this.shellHolder.get().setText(TITLE_SHELL);
		setupShellMenu();
	}

	private void setupShellMenu() {
		Menu bar = new Menu(this.shellHolder.get(), SWT.BAR);
		MenuItem appItem = new MenuItem(bar, SWT.CASCADE);
		Menu app = new Menu(appItem);
		MenuItem appQuit = new MenuItem(app, SWT.PUSH);

		this.shellHolder.get().setMenuBar(bar);
		appItem.setText(TITLE_MENU_APP);
		appItem.setMenu(app);
		appQuit.setText(TITLE_MENU_APP_QUIT);
		appQuit.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(@Nullable SelectionEvent evt) {
				onAppQuit();
			}

		});
	}

	void onAppQuit() {
		this.shellHolder.get().close();
	}

}
