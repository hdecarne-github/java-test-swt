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
package de.carne.test.swt.tester;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.PrinterData;

import de.carne.boot.logging.Log;
import mockit.Mock;
import mockit.MockUp;

/**
 * Mock for {@linkplain PrintDialog} dialog.
 */
public final class PrintDialogMock {

	private static final Log LOG = new Log();

	private @Nullable PrinterData nextResult = null;

	@SuppressWarnings("unused")
	private final MockUp<PrintDialog> mockUp = new MockUp<PrintDialog>() {

		@Mock
		public @Nullable PrinterData open() {
			return mockOpen();
		}

	};

	/**
	 * Sets the result for the next call to {@linkplain PrintDialog#open()}.
	 *
	 * @param result the result for the next call to {@linkplain PrintDialog#open()}.
	 */
	public void result(PrinterData result) {
		this.nextResult = result;
	}

	@Nullable
	PrinterData mockOpen() {
		PrinterData result = this.nextResult;

		LOG.info("PrintDialog.open() = {0}", result);

		this.nextResult = null;
		return result;
	}

}
