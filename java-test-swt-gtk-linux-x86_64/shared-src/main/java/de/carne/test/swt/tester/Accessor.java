/*
 * Copyright (c) 2017-2018 Holger de Carne and contributors, All Rights Reserved.
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
import org.junit.jupiter.api.Assertions;

/**
 * Base class for all accessor objects.
 *
 * @param <T> The actual type providing access to.
 */
public class Accessor<T> implements Supplier<T> {

	private final T object;

	/**
	 * Construct {@linkplain Accessor}.
	 *
	 * @param object The object to wrap.
	 */
	public Accessor(T object) {
		this.object = object;
	}

	@Override
	public T get() {
		return this.object;
	}

	/**
	 * Ensure that an object is not {@code null}.
	 * <p>
	 * If the submitted object is {@code null} this function signals a test failure.
	 *
	 * @param <T> The checked object's actual type.
	 * @param object The object to check.
	 * @param message The message to use for test case failure.
	 * @return The checked object.
	 */
	public static <T> T notNull(@Nullable T object, String message) {
		if (object == null) {
			Assertions.fail(message);
		}

		assert object != null;

		return object;
	}

}
