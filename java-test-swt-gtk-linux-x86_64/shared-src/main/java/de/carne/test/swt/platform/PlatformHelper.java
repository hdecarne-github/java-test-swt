/*
 * Copyright (c) 2017-2022 Holger de Carne and contributors, All Rights Reserved.
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
package de.carne.test.swt.platform;

import java.io.IOException;
import java.nio.file.Path;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

import de.carne.util.Exceptions;
import de.carne.util.Lazy;

/**
 * Utility class providing platform specific functions.
 */
public abstract class PlatformHelper {

	private static final Lazy<PlatformHelper> INSTANCE_HOLDER = new Lazy<>(PlatformHelper::getInstance);

	private static PlatformHelper getInstance() {
		PlatformHelper instance;

		try {
			String platform = SWT.getPlatform();
			String platformName = PlatformHelper.class.getPackage().getName() + "." + platform + "."
					+ platform.substring(0, 1).toUpperCase() + platform.substring(1)
					+ PlatformHelper.class.getSimpleName();

			instance = Class.forName(platformName).asSubclass(PlatformHelper.class).getConstructor().newInstance();
		} catch (ReflectiveOperationException e) {
			throw Exceptions.toRuntime(e);
		}
		return instance;
	}

	/**
	 * Constructs a new {@linkplain PlatformHelper} instance.
	 */
	protected PlatformHelper() {
		// Nothing to do here
	}

	/**
	 * Checks whether SWT code can be executed by the current thread.
	 *
	 * @return {@code true} if the current thread can execute SWT code.
	 */
	public static boolean isCurrentThreadSWTCapable() {
		return INSTANCE_HOLDER.get().internalIsCurrentThreadSWTCapable();
	}

	/**
	 * Checks whether SWT code can be executed by the current thread.
	 *
	 * @return {@code true} if the current thread can execute SWT code.
	 * @see #isCurrentThreadSWTCapable()
	 */
	protected boolean internalIsCurrentThreadSWTCapable() {
		return false;
	}

	/**
	 * Checks whether a native platform dialog is currently open.
	 *
	 * @param display the {@linkplain Display} to use for checking.
	 * @return {@code true} if a native platform dialog is currently open.
	 */
	public static boolean inNativeDialog(Display display) {
		return INSTANCE_HOLDER.get().internalInNativeDialog(display);
	}

	/**
	 * Checks whether a native platform dialog is currently open.
	 *
	 * @param display the {@linkplain Display} to use for checking.
	 * @return {@code true} if a native platform dialog is currently open.
	 * @see #inNativeDialog(Display)
	 */
	protected boolean internalInNativeDialog(Display display) {
		return false;
	}

	/**
	 * Makes sure that any open native platform dialog is closed.
	 *
	 * @param display the {@linkplain Display} to use for checking.
	 * @return {@code true} if a native platform dialog has been closed.
	 */
	public static boolean closeNativeDialogs(Display display) {
		return INSTANCE_HOLDER.get().internalCloseNativeDialogs(display);
	}

	/**
	 * Makes sure that any open native platform dialog is closed.
	 *
	 * @param display the {@linkplain Display} to use for checking.
	 * @return {@code true} if a native platform dialog has been closed.
	 * @see #closeNativeDialogs(Display)
	 */
	protected boolean internalCloseNativeDialogs(Display display) {
		return false;
	}

	/**
	 * Tries to grab the screen and stores a screenshot by invoking a platform dependent command.
	 *
	 * @param dir the directory to store the screenshot into.
	 * @return the created screenshot file.
	 * @throws IOException if an I/O error occurs.
	 */
	public static Path grabScreen(Path dir) throws IOException {
		return INSTANCE_HOLDER.get().internalGrabScreen(dir);
	}

	/**
	 * Tries to grab the screen and stores a screenshot by invoking a platform dependent command.
	 *
	 * @param dir the directory to store the screenshot into.
	 * @return the created screenshot file.
	 * @throws IOException if an I/O error occurs.
	 */
	protected Path internalGrabScreen(Path dir) throws IOException {
		throw new IOException("No screenshot command found");
	}

}
