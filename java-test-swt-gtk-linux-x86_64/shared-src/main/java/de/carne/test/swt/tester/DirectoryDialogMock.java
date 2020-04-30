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
package de.carne.test.swt.tester;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.swt.widgets.DirectoryDialog;

import de.carne.util.logging.Log;
import mockit.Mock;
import mockit.MockUp;

/**
 * Mock for {@linkplain DirectoryDialog} dialog.
 */
public final class DirectoryDialogMock {

	private static final Log LOG = new Log();

	private @Nullable String nextResult = null;

	@SuppressWarnings("unused")
	private final MockUp<DirectoryDialog> mockUp = new MockUp<DirectoryDialog>() {

		@Mock
		public @Nullable String open() {
			return mockOpen();
		}

	};

	/**
	 * Sets the result for the next call to {@linkplain DirectoryDialog#open()}.
	 *
	 * @param result the result for the next call to {@linkplain DirectoryDialog#open()}.
	 */
	public void result(String result) {
		this.nextResult = result;
	}

	@Nullable
	String mockOpen() {
		String result = this.nextResult;

		LOG.info("DirectoryDialog.open() = {0}", result);

		this.nextResult = null;
		return result;
	}

}
