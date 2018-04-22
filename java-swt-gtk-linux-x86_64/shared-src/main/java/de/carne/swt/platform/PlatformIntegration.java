/*
 * Copyright (c) 2007-2018 Holger de Carne and contributors, All Rights Reserved.
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
package de.carne.swt.platform;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;

import de.carne.boot.Exceptions;
import de.carne.swt.UserApplication;

/**
 * This class provides a generic access to platform specific OS integration features (like the application menu on
 * macOS).
 */
public abstract class PlatformIntegration {

	private static final PlatformIntegration INSTANCE;

	static {
		try {
			String basePackageName = UserApplication.class.getPackage().getName();
			String platformName = PlatformIntegration.class.getName().replace(basePackageName,
					basePackageName + "." + SWT.getPlatform());

			INSTANCE = Class.forName(platformName).asSubclass(PlatformIntegration.class).getConstructor().newInstance();
		} catch (ReflectiveOperationException e) {
			throw Exceptions.toRuntime(e);
		}
	}

	/**
	 * Constructs a new {@linkplain PlatformIntegration} instance.
	 */
	protected PlatformIntegration() {
		// Nothing to do here
	}

	/**
	 * Get this VM's {@linkplain PlatformIntegration} instance.
	 *
	 * @return this VM's {@linkplain PlatformIntegration} instance.
	 */
	public static PlatformIntegration getInstance() {
		return INSTANCE;
	}

	/**
	 * Determines whether this code runs on the Cocoa platform.
	 *
	 * @return {@code true} if this code runs on the Cocoa platform.
	 */
	public boolean isCocoa() {
		return false;
	}

	/**
	 * Adds a {@linkplain SelectionListener} to be invoked in case the Cocoa application menu's about entry is selected.
	 *
	 * @param display the application's {@linkplain Display} instance.
	 * @param listener the listener to add.
	 */
	public void cocoaAddAboutListener(Display display, SelectionListener listener) {
		// default is to do nothing
	}

	/**
	 * Adds a {@linkplain SelectionListener} to be invoked in case the Cocoa application menu's preferences entry is
	 * selected.
	 *
	 * @param display the application's {@linkplain Display} instance.
	 * @param listener the listener to add.
	 */
	public void cocoaAddPreferencesListener(Display display, SelectionListener listener) {
		// default is to do nothing
	}

	/**
	 * Adds a {@linkplain SelectionListener} to be invoked in case the Cocoa application menu's quit entry is selected.
	 *
	 * @param display the application's {@linkplain Display} instance.
	 * @param listener the listener to add.
	 */
	public void cocoaAddQuitListener(Display display, SelectionListener listener) {
		// default is to do nothing
	}

	/**
	 * Determines whether this code runs on the Win32 platform.
	 *
	 * @return {@code true} if this code runs on the Win32 platform.
	 */
	public boolean isWin32() {
		return false;
	}

	/**
	 * Determines whether this code runs on the GTK platform.
	 *
	 * @return {@code true} if this code runs on the GTK platform.
	 */
	public boolean isGtk() {
		return false;
	}

}
