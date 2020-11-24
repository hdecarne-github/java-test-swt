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

import org.eclipse.jdt.annotation.Nullable;

/**
 * Interface for mocking of standard dialog results during a test run.
 *
 * @param <T> the actual dialog result type.
 */
public interface DialogMock<T> {

	/**
	 * Adds a result to the result queue.
	 *
	 * @param result the result to add.
	 */
	void offerResult(@Nullable T result);

	/**
	 * Adds a result to the result queue.
	 *
	 * @param resultSupplier the result to add.
	 */
	void offerResult(Supplier<@Nullable T> resultSupplier);

}
