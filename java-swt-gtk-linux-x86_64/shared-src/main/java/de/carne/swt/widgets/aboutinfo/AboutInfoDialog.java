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
package de.carne.swt.widgets.aboutinfo;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Shell;

import de.carne.swt.graphics.ResourceException;

/**
 * Dialog for display of version and copyright information.
 */
public class AboutInfoDialog extends Dialog {

	private @Nullable URL logoUrl = null;
	private List<URL> copyrightUrls = new ArrayList<>();

	/**
	 * Constructs a new {@linkplain AboutInfoDialog} instance.
	 *
	 * @param parent the dialog's parent.
	 */
	public AboutInfoDialog(Shell parent) {
		super(parent, SWT.DIALOG_TRIM | SWT.RESIZE | SWT.APPLICATION_MODAL);
	}

	/**
	 * Convenience function for starting a {@linkplain AboutInfoDialog} setup.
	 *
	 * @param parent the dialog's parent.
	 * @return the created {@linkplain AboutInfoDialog} instance for chaining.
	 */
	public static AboutInfoDialog build(Shell parent) {
		return new AboutInfoDialog(parent);
	}

	/**
	 * Sets the dialog's logo.
	 *
	 * @param logo the {@linkplain URL} of the logo to display.
	 * @return the updated {@linkplain AboutInfoDialog} instance for chaining.
	 */
	public AboutInfoDialog withLogo(URL logo) {
		this.logoUrl = logo;
		return this;
	}

	/**
	 * Adds a copyright info.
	 * <p>
	 * The first line of the copyright info is used as a copyright title the remaining lines are used as the actual
	 * copyright info.
	 *
	 * @param copyright the {@linkplain URL} of the copyright info to display.
	 * @return the updated {@linkplain AboutInfoDialog} instance for chaining.
	 */
	public AboutInfoDialog withCopyright(URL copyright) {
		this.copyrightUrls.add(copyright);
		return this;
	}

	/**
	 * Opens and runs the dialog.
	 *
	 * @throws ResourceException if a required resource is not available.
	 */
	public void open() throws ResourceException {
		AboutInfoUI userInterface = new AboutInfoUI(new Shell(getParent(), getStyle()), this.logoUrl,
				this.copyrightUrls);

		userInterface.open();
		userInterface.run();
	}

}
