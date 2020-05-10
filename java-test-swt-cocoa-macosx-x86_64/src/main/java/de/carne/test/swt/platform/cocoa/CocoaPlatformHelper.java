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
package de.carne.test.swt.platform.cocoa;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.TimeUnit;

import org.eclipse.swt.internal.cocoa.NSApplication;
import org.eclipse.swt.internal.cocoa.NSThread;
import org.eclipse.swt.internal.cocoa.OS;
import org.eclipse.swt.widgets.Display;

import de.carne.nio.file.attribute.FileAttributes;
import de.carne.test.swt.platform.PlatformHelper;
import de.carne.util.logging.Log;

/**
 * Cocoa platform helper.
 */
public class CocoaPlatformHelper extends PlatformHelper {

	private static final Log LOG = new Log();

	private static final long CUSTOM_SEL_modalWindow = OS.sel_registerName("modalWindow");

	@Override
	protected boolean internalIsCurrentThreadSWTCapable() {
		return NSThread.isMainThread();
	}

	@Override
	protected boolean internalInNativeDialog(Display display) {
		NSApplication application = NSApplication.sharedApplication();

		return application != null && findModalWindow(application) != 0;
	}

	@Override
	protected boolean internalCloseNativeDialogs(Display display) {
		boolean inNativeDialog = false;
		NSApplication application = NSApplication.sharedApplication();

		if (application != null) {
			long modalWindowId = findModalWindow(application);

			if (modalWindowId != 0) {
				LOG.debug("Stopping modal loop...");

				application.stopModal();
				inNativeDialog = true;
			}
		}
		return inNativeDialog;
	}

	private long findModalWindow(NSApplication application) {
		long modalWindowId = OS.objc_msgSend(application.id, CUSTOM_SEL_modalWindow);

		LOG.debug("Find modal window result: 0x{0}", Long.toHexString(modalWindowId));

		return modalWindowId;
	}

	private static final long GRAB_SCREEN_TIMEOUT = 1000;

	@Override
	protected Path internalGrabScreen(Path dir) throws IOException {
		Path tmpFile = Files.createTempFile(dir, null, ".png", FileAttributes.userFileDefault(dir)).toAbsolutePath();
		ProcessBuilder processBuilder = new ProcessBuilder("screencapture", "-m", tmpFile.toString());

		Process process = processBuilder.start();
		int status = -1;

		try {
			LOG.debug("Waiting for screencapture process ''{0}''...", process);

			if (process.waitFor(CocoaPlatformHelper.GRAB_SCREEN_TIMEOUT, TimeUnit.MILLISECONDS)) {
				status = process.exitValue();

				LOG.debug("Screencapture process completed with status {0}", status);
			} else {
				LOG.warning("Screencapture process timout reached; destroy process");

				process.destroyForcibly();
			}
		} catch (InterruptedException e) {
			LOG.warning(e, "Screencapture process invocation interrupted");

			Thread.currentThread().interrupt();
		}
		return tmpFile;
	}

}
