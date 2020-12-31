/*
 * Copyright (c) 2017-2021 Holger de Carne and contributors, All Rights Reserved.
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
import java.util.function.IntSupplier;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import de.carne.util.logging.Log;

final class MessageBoxMock extends IntDialogMock implements AutoCloseable {

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

	private MockedConstruction<MessageBox> mockConstruction = Mockito.mockConstruction(MessageBox.class,
			Mockito.withSettings(), (mock, context) -> Mockito.when(mock.open()).then(iom -> {
				IntSupplier resultSupplier = pollResult();
				int result = (resultSupplier != null ? resultSupplier.getAsInt() : SWT.CANCEL);

				LOG.info("MessageBox.open() = {0}", SWT_SYMBOLS.getOrDefault(result, Integer.toString(result)));

				return result;
			}));

	@Override
	public void close() {
		this.mockConstruction.close();
	}

}
