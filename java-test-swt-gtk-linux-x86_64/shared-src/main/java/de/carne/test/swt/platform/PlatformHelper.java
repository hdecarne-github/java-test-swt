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
package de.carne.test.swt.platform;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

import de.carne.boot.Exceptions;
import de.carne.test.swt.DisableIfThreadNotSWTCapable;
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
			String basePackageName = DisableIfThreadNotSWTCapable.class.getPackage().getName();
			String baseClassName = PlatformHelper.class.getSimpleName();
			String platformName = PlatformHelper.class.getName()
					.replace(basePackageName, basePackageName + "." + platform).replace(baseClassName,
							platform.substring(0, 1).toUpperCase() + platform.substring(1) + baseClassName);

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

	protected boolean internalIsCurrentThreadSWTCapable() {
		return true;
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

	protected boolean internalInNativeDialog(@SuppressWarnings("unused") Display display) {
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

	protected boolean internalCloseNativeDialogs(@SuppressWarnings("unused") Display display) {
		return false;
	}

}
