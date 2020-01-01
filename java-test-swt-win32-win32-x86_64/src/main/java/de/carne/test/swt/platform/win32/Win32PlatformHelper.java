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
package de.carne.test.swt.platform.win32;

import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.swt.internal.win32.OS;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import de.carne.boot.logging.Log;
import de.carne.test.swt.platform.PlatformHelper;

/**
 * Win32 platform helper.
 */
public class Win32PlatformHelper extends PlatformHelper {

	private static final Log LOG = new Log();

	@Override
	protected boolean internalIsCurrentThreadSWTCapable() {
		return true;
	}

	@Override
	protected boolean internalInNativeDialog(Display display) {
		AtomicBoolean resultHolder = new AtomicBoolean();

		if (!display.isDisposed()) {
			if (Thread.currentThread().equals(display.getThread())) {
				resultHolder.set(findNativeDialog(display) != 0);
			} else {
				display.syncExec(() -> resultHolder.set(internalInNativeDialog(display)));
			}
		}
		return resultHolder.get();
	}

	@Override
	protected boolean internalCloseNativeDialogs(Display display) {
		AtomicBoolean resultHolder = new AtomicBoolean();

		if (Thread.currentThread().equals(display.getThread())) {
			long nativeDialog = findNativeDialog(display);

			if (nativeDialog != 0) {
				LOG.debug("Destroying native dialog result: 0x{0}", Long.toHexString(nativeDialog));

				OS.DestroyWindow(nativeDialog);
				resultHolder.set(true);
			}
		} else {
			display.syncExec(() -> resultHolder.set(internalCloseNativeDialogs(display)));
		}
		return resultHolder.get();
	}

	private long findNativeDialog(Display display) {
		long activeWindow = OS.GetActiveWindow();

		if (activeWindow != 0) {
			Shell[] shells = display.getShells();

			for (Shell shell : shells) {
				if (shell.handle == activeWindow) {
					activeWindow = 0;
					break;
				}
			}
		}

		LOG.debug("Find native dialog result: 0x{0}", Long.toHexString(activeWindow));

		return activeWindow;
	}

}
