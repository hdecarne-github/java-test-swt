/*
 * Copyright (c) 2007-2019 Holger de Carne and contributors, All Rights Reserved.
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

import java.util.function.Consumer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Display;

import de.carne.boot.Exceptions;
import de.carne.swt.UserApplication;
import de.carne.util.Lazy;

/**
 * This class provides a generic access to platform specific OS integration features (like the application menu on
 * macOS).
 */
public abstract class PlatformIntegration {

	private static final Lazy<PlatformIntegration> INSTANCE_HOLDER = new Lazy<>(PlatformIntegration::getInstance);

	private static PlatformIntegration getInstance() {
		PlatformIntegration instance;

		try {
			String basePackageName = UserApplication.class.getPackage().getName();
			String platformName = PlatformIntegration.class.getName().replace(basePackageName,
					basePackageName + "." + SWT.getPlatform());

			instance = Class.forName(platformName).asSubclass(PlatformIntegration.class).getConstructor().newInstance();
		} catch (ReflectiveOperationException e) {
			throw Exceptions.toRuntime(e);
		}
		return instance;
	}

	/**
	 * Determines whether the preferred button order is left-to-right for the current platform.
	 *
	 * @return {@code true} if the preferred button order is left-to-right.
	 */
	public static boolean isButtonOrderLeftToRight() {
		return INSTANCE_HOLDER.get().internalIsButtonOrderLeftToRight();
	}

	/**
	 * Determines whether this code runs on the Cocoa platform.
	 *
	 * @return {@code true} if this code runs on the Cocoa platform.
	 */
	public static boolean isCocoa() {
		return INSTANCE_HOLDER.get().internalIsCocoa();
	}

	/**
	 * Adds a action to be invoked in case the Cocoa application menu's about entry is selected.
	 *
	 * @param display the application's {@linkplain Display} instance.
	 * @param action the action to add.
	 */
	public static void cocoaAddAboutSelectionAction(Display display, Consumer<SelectionEvent> action) {
		INSTANCE_HOLDER.get().internalCocoaAddAboutSelectionAction(display, action);
	}

	/**
	 * Adds a action to be invoked in case the Cocoa application menu's about entry is selected.
	 *
	 * @param display the application's {@linkplain Display} instance.
	 * @param action the action to add.
	 */
	public static void cocoaAddAboutSelectionAction(Display display, Runnable action) {
		INSTANCE_HOLDER.get().internalCocoaAddAboutSelectionAction(display, action);
	}

	/**
	 * Adds an action to be invoked in case the Cocoa application menu's preferences entry is selected.
	 *
	 * @param display the application's {@linkplain Display} instance.
	 * @param action the action to add.
	 */
	public static void cocoaAddPreferencesSelectionAction(Display display, Consumer<SelectionEvent> action) {
		INSTANCE_HOLDER.get().internalCocoaAddPreferencesSelectionAction(display, action);
	}

	/**
	 * Adds an action to be invoked in case the Cocoa application menu's preferences entry is selected.
	 *
	 * @param display the application's {@linkplain Display} instance.
	 * @param action the action to add.
	 */
	public static void cocoaAddPreferencesSelectionAction(Display display, Runnable action) {
		INSTANCE_HOLDER.get().internalCocoaAddPreferencesSelectionAction(display, action);
	}

	/**
	 * Adds an action to be invoked in case the Cocoa application menu's quit entry is selected.
	 *
	 * @param display the application's {@linkplain Display} instance.
	 * @param action the action to add.
	 */
	public static void cocoaAddQuitSelectionAction(Display display, Consumer<SelectionEvent> action) {
		INSTANCE_HOLDER.get().internalCocoaAddQuitSelectionAction(display, action);
	}

	/**
	 * Adds an action to be invoked in case the Cocoa application menu's quit entry is selected.
	 *
	 * @param display the application's {@linkplain Display} instance.
	 * @param action the action to add.
	 */
	public static void cocoaAddQuitSelectionAction(Display display, Runnable action) {
		INSTANCE_HOLDER.get().internalCocoaAddQuitSelectionAction(display, action);
	}

	/**
	 * Determines whether this code runs on the Win32 platform.
	 *
	 * @return {@code true} if this code runs on the Win32 platform.
	 */
	public static boolean isWin32() {
		return INSTANCE_HOLDER.get().internalIsWin32();
	}

	/**
	 * Determines whether this code runs on the GTK platform.
	 *
	 * @return {@code true} if this code runs on the GTK platform.
	 */
	public static boolean isGtk() {
		return INSTANCE_HOLDER.get().internalIsGtk();
	}

	/**
	 * Constructs a new {@linkplain PlatformIntegration} instance.
	 */
	protected PlatformIntegration() {
		// Nothing to do here
	}

	/**
	 * Determines whether the preferred button order is left-to-right for the current platform.
	 *
	 * @return {@code true} if the preferred button order is left-to-right.
	 */
	protected abstract boolean internalIsButtonOrderLeftToRight();

	/**
	 * Determines whether this code runs on the Cocoa platform.
	 *
	 * @return {@code true} if this code runs on the Cocoa platform.
	 */
	protected boolean internalIsCocoa() {
		return false;
	}

	/**
	 * Adds a action to be invoked in case the Cocoa application menu's about entry is selected.
	 *
	 * @param display the application's {@linkplain Display} instance.
	 * @param action the action to add.
	 */
	protected void internalCocoaAddAboutSelectionAction(Display display, Consumer<SelectionEvent> action) {
		// default is to do nothing
	}

	/**
	 * Adds a action to be invoked in case the Cocoa application menu's about entry is selected.
	 *
	 * @param display the application's {@linkplain Display} instance.
	 * @param action the action to add.
	 */
	protected void internalCocoaAddAboutSelectionAction(Display display, Runnable action) {
		// default is to do nothing
	}

	/**
	 * Adds an action to be invoked in case the Cocoa application menu's preferences entry is selected.
	 *
	 * @param display the application's {@linkplain Display} instance.
	 * @param action the action to add.
	 */
	protected void internalCocoaAddPreferencesSelectionAction(Display display, Consumer<SelectionEvent> action) {
		// default is to do nothing
	}

	/**
	 * Adds an action to be invoked in case the Cocoa application menu's preferences entry is selected.
	 *
	 * @param display the application's {@linkplain Display} instance.
	 * @param action the action to add.
	 */
	protected void internalCocoaAddPreferencesSelectionAction(Display display, Runnable action) {
		// default is to do nothing
	}

	/**
	 * Adds an action to be invoked in case the Cocoa application menu's quit entry is selected.
	 *
	 * @param display the application's {@linkplain Display} instance.
	 * @param action the action to add.
	 */
	protected void internalCocoaAddQuitSelectionAction(Display display, Consumer<SelectionEvent> action) {
		// default is to do nothing
	}

	/**
	 * Adds an action to be invoked in case the Cocoa application menu's quit entry is selected.
	 *
	 * @param display the application's {@linkplain Display} instance.
	 * @param action the action to add.
	 */
	protected void internalCocoaAddQuitSelectionAction(Display display, Runnable action) {
		// default is to do nothing
	}

	/**
	 * Determines whether this code runs on the Win32 platform.
	 *
	 * @return {@code true} if this code runs on the Win32 platform.
	 */
	protected boolean internalIsWin32() {
		return false;
	}

	/**
	 * Determines whether this code runs on the GTK platform.
	 *
	 * @return {@code true} if this code runs on the GTK platform.
	 */
	protected boolean internalIsGtk() {
		return false;
	}

}
