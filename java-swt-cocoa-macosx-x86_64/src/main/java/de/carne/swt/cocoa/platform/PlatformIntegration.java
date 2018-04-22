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
package de.carne.swt.cocoa.platform;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MenuItem;

/**
 * Cocoa platform integration.
 */
@SuppressWarnings("squid:S2176")
public class PlatformIntegration extends de.carne.swt.platform.PlatformIntegration {

	@Override
	public boolean isCocoa() {
		return true;
	}

	@Override
	public void cocoaAddAboutListener(Display display, SelectionListener listener) {
		addSystemMenuItemListener(display, SWT.ID_ABOUT, listener);
	}

	@Override
	public void cocoaAddPreferencesListener(Display display, SelectionListener listener) {
		addSystemMenuItemListener(display, SWT.ID_PREFERENCES, listener);
	}

	@Override
	public void cocoaAddQuitListener(Display display, SelectionListener listener) {
		addSystemMenuItemListener(display, SWT.ID_QUIT, listener);
	}

	private void addSystemMenuItemListener(Display display, int itemId, SelectionListener listener) {
		for (MenuItem menuItem : display.getSystemMenu().getItems()) {
			if (menuItem.getID() == itemId) {
				menuItem.addSelectionListener(listener);
				break;
			}
		}
	}

}
