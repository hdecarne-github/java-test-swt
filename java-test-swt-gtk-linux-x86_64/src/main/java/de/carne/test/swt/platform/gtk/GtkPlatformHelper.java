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
package de.carne.test.swt.platform.gtk;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.swt.internal.gtk.GTK;
import org.eclipse.swt.internal.gtk.OS;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import de.carne.nio.file.attribute.FileAttributes;
import de.carne.test.swt.platform.PlatformHelper;
import de.carne.test.swt.platform.ProcessRunner;
import de.carne.util.logging.Log;

/**
 * GTK platform helper.
 */
public class GtkPlatformHelper extends PlatformHelper {

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

		if (!display.isDisposed()) {
			if (Thread.currentThread().equals(display.getThread())) {
				long nativeDialog = findNativeDialog(display);

				if (nativeDialog != 0) {
					LOG.debug("Destroying native dialog: 0x{0}", Long.toHexString(nativeDialog));

					GTK.gtk_widget_destroy(nativeDialog);
					resultHolder.set(true);
				}
			} else {
				display.syncExec(() -> resultHolder.set(internalCloseNativeDialogs(display)));
			}
		}
		return resultHolder.get();
	}

	private long findNativeDialog(Display display) {
		Set<Long> shellToplevels = new HashSet<>();

		for (Shell shell : display.getShells()) {
			shellToplevels.add(GTK.gtk_widget_get_toplevel(shell.handle));
		}

		long nativeDialog = 0;
		long toplevels = GTK.gtk_window_list_toplevels();

		while (toplevels != 0) {
			long toplevel = OS.g_list_data(toplevels);

			if (!shellToplevels.contains(toplevel) && GTK.gtk_window_get_modal(toplevel)) {
				nativeDialog = toplevel;
				break;
			}
			toplevels = OS.g_list_next(toplevels);
		}
		OS.g_list_free(toplevels);

		LOG.debug("Find native dialog result: 0x{0}", Long.toHexString(nativeDialog));

		return nativeDialog;
	}

	@Override
	protected Path internalGrabScreen(Path dir) throws IOException {
		Path tmpFile = Files.createTempFile(dir, null, ".png", FileAttributes.userFileDefault(dir)).toAbsolutePath();
		ProcessRunner processRunner = new ProcessRunner("import", "-window", "root", tmpFile.toString());

		processRunner.run();
		processRunner.checkStatus();
		return tmpFile;
	}

}
