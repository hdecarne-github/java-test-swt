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

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;

import de.carne.boot.logging.Log;
import mockit.Mock;
import mockit.MockUp;

/**
 * Mock for {@linkplain MessageBox} dialog.
 */
public final class MessageBoxMock {

	private static final Log LOG = new Log();

	private static final Map<Integer, String> SWT_SYMBOLS = new HashMap<>();

	static {
		SWT_SYMBOLS.put(SWT.OK, "SWT.OK");
		SWT_SYMBOLS.put(SWT.CANCEL, "SWT.CANCEL");
		SWT_SYMBOLS.put(SWT.YES, "SWT.YES");
		SWT_SYMBOLS.put(SWT.NO, "SWT.NO");
		SWT_SYMBOLS.put(SWT.ABORT, "SWT.ABORT");
		SWT_SYMBOLS.put(SWT.RETRY, "SWT.RETRY");
		SWT_SYMBOLS.put(SWT.IGNORE, "SWT.IGNORE");
	}

	private int nextResult = SWT.CANCEL;

	@SuppressWarnings("unused")
	private final MockUp<MessageBox> mockUp = new MockUp<MessageBox>() {

		@Mock
		public int open() {
			return mockOpen();
		}

	};

	/**
	 * Sets the result for the next call to {@linkplain MessageBox#open()}.
	 *
	 * @param result the result for the next call to {@linkplain MessageBox#open()}.
	 */
	public void result(int result) {
		this.nextResult = result;
	}

	int mockOpen() {
		int result = this.nextResult;

		LOG.info("MessageBox.open() = {0}", SWT_SYMBOLS.getOrDefault(result, Integer.toString(result)));

		this.nextResult = SWT.CANCEL;
		return result;
	}

}
