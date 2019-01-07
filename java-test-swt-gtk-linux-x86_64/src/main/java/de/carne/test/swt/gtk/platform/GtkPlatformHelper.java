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
package de.carne.test.swt.gtk.platform;

import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.swt.internal.gtk.GDK;
import org.eclipse.swt.internal.gtk.OS;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import de.carne.test.swt.platform.PlatformHelper;

/**
 * GTK platform helper.
 */
public class GtkPlatformHelper extends PlatformHelper {

	@Override
	protected boolean internalInNativeDialog(Display display) {
		AtomicBoolean resultHolder = new AtomicBoolean();

		if (Thread.currentThread().equals(display.getThread())) {
			Shell activeShell = display.getActiveShell();
			long activeWindow = getActiveWindow();

			resultHolder.set(activeShell == null || activeShell.handle != activeWindow);
			OS.g_object_unref(activeWindow);
		} else {
			display.syncExec(() -> resultHolder.set(inNativeDialog(display)));
		}
		return resultHolder.get();
	}

	@Override
	protected boolean internalCloseNativeDialogs(Display display) {
		AtomicBoolean resultHolder = new AtomicBoolean();

		if (Thread.currentThread().equals(display.getThread())) {
			Shell activeShell = display.getActiveShell();
			long activeWindow = getActiveWindow();

			if (activeShell == null || activeShell.handle != activeWindow) {
				GDK.gdk_window_destroy(activeWindow);
				OS.g_object_unref(activeWindow);
				resultHolder.set(true);
			}
		} else {
			display.syncExec(() -> resultHolder.set(inNativeDialog(display)));
		}
		return resultHolder.get();
	}

	private long getActiveWindow() {
		long activeWindow = 0;
		long windowStack = GDK.gdk_screen_get_window_stack(GDK.gdk_screen_get_default());

		if (windowStack != 0) {
			long window = OS.g_list_last(windowStack);

			while (window != 0) {
				OS.g_object_unref(activeWindow);
				activeWindow = window;
				window = OS.g_list_previous(window);
			}
		}
		OS.g_list_free(windowStack);
		return activeWindow;
	}

}
