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

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellEvent;

import de.carne.Application;
import de.carne.check.Check;
import de.carne.util.logging.Log;

/**
 * Test user agent.
 */
class TestUserController {

	private static final Log LOG = new Log();

	private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

	private final TestUserInterface ui;

	TestUserController(TestUserInterface ui) {
		this.ui = ui;
	}

	void onShellEvent(ShellEvent event) {
		LOG.info("Shell event: {0}", event);
	}

	void onShellDisposed() {
		this.executorService.shutdownNow();
		Application.getMain(TestUserApplicationMain.class).setStatus(0);
	}

	void onShellActivated() {
		this.executorService.schedule(this::runBackgroundCommand, 500, TimeUnit.MILLISECONDS);
	}

	private void runBackgroundCommand() {
		TestUserApplicationMain main = Application.getMain(TestUserApplicationMain.class);

		String statusText = Check.notNull(main.runWait(() -> main.getDisplay().toString()));

		main.runWait(() -> main.getDisplay().beep());
		main.runNoWait(() -> this.ui.setStatus(statusText));
	}

	void onCommandItemSelected() {
		this.ui.setStatus("Command selected.");
	}

	void onCommandItemSelectionEvent(SelectionEvent event) {
		this.ui.setStatus("Command item '" + event.widget + "'selected.");
	}

}
