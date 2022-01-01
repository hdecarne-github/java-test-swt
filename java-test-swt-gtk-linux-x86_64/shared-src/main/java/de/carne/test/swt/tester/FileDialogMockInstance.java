/*
 * Copyright (c) 2017-2022 Holger de Carne and contributors, All Rights Reserved.
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

import org.eclipse.swt.widgets.FileDialog;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import de.carne.test.mock.ScopedMockInstance;
import de.carne.util.logging.Log;

final class FileDialogMockInstance extends ScopedMockInstance<MockedConstruction<FileDialog>, DialogMock<String>> {

	private static final Log LOG = new Log();

	FileDialogMockInstance() {
		super(FileDialogMockInstance::initialize, new DialogMock<>());
	}

	private static MockedConstruction<FileDialog> initialize(DialogMock<String> instance) {
		return Mockito.mockConstruction(FileDialog.class, Mockito.withSettings(),
				(mock, context) -> Mockito.when(mock.open()).then(iom -> {
					Supplier<String> resultSupplier = instance.pollResult();
					String result = (resultSupplier != null ? resultSupplier.get() : null);

					LOG.info("FileDialog.open() = {0}", result);

					return result;
				}));
	}

}
