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

import java.util.function.Supplier;

import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.widgets.FontDialog;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import de.carne.util.logging.Log;

final class FontDialogMock extends DialogMock<FontData> implements AutoCloseable {

	private static final Log LOG = new Log();

	private MockedConstruction<FontDialog> mockConstruction = Mockito.mockConstruction(FontDialog.class,
			Mockito.withSettings(), (mock, context) -> Mockito.when(mock.open()).then(iom -> {
				Supplier<FontData> resultSupplier = pollResult();
				FontData result = (resultSupplier != null ? resultSupplier.get() : null);

				LOG.info("FontDialog.open() = {0}", result);

				return result;
			}));

	@Override
	public void close() {
		this.mockConstruction.close();
	}

}
