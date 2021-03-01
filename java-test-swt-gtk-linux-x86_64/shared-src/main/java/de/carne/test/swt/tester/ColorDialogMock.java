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

import java.util.function.Supplier;

import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.ColorDialog;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import de.carne.test.mock.ScopedMockInstance;
import de.carne.util.logging.Log;

final class ColorDialogMock extends ScopedMockInstance<MockedConstruction<ColorDialog>, DialogMock<RGB>> {

	private static final Log LOG = new Log();

	ColorDialogMock() {
		super(ColorDialogMock::initialize, new DialogMock<>());
	}

	private static MockedConstruction<ColorDialog> initialize(DialogMock<RGB> instance) {
		return Mockito.mockConstruction(ColorDialog.class, Mockito.withSettings(),
				(mock, context) -> Mockito.when(mock.open()).then(iom -> {
					Supplier<RGB> resultSupplier = instance.pollResult();
					RGB result = (resultSupplier != null ? resultSupplier.get() : null);

					LOG.info("ColorDialog.open() = {0}", result);

					return result;
				}));
	}

}
