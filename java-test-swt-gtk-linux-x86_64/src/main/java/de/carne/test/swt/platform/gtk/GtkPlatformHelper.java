/*
 * Copyright (c) 2017-2019 Holger de Carne and contributors, All Rights Reserved.
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
package de.carne.test.swt.platform.gtk;

import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import de.carne.test.swt.platform.PlatformHelper;

/**
 * GTK platform helper.
 */
public class GtkPlatformHelper extends PlatformHelper {

	@Override
	protected boolean internalIsCurrentThreadSWTCapable() {
		return true;
	}

	@Override
	protected boolean internalInNativeDialog(Display display) {
		AtomicBoolean resultHolder = new AtomicBoolean();

		if (!display.isDisposed()) {
			if (Thread.currentThread().equals(display.getThread())) {
				Shell activeShell = display.getActiveShell();

				if (activeShell != null) {
					resultHolder.set(activeShell.isEnabled());
				}
			} else {
				display.syncExec(() -> resultHolder.set(inNativeDialog(display)));
			}
		}
		return resultHolder.get();
	}

	@Override
	protected boolean internalCloseNativeDialogs(Display display) {
		AtomicBoolean resultHolder = new AtomicBoolean();

		if (!display.isDisposed()) {
			if (Thread.currentThread().equals(display.getThread())) {
				Shell activeShell = display.getActiveShell();

				if (activeShell != null && !activeShell.isEnabled()) {
					resultHolder.set(true);
				}
			} else {
				display.syncExec(() -> resultHolder.set(inNativeDialog(display)));
			}
		}
		return resultHolder.get();
	}

}
