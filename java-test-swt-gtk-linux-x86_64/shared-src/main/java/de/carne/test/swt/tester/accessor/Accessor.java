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
package de.carne.test.swt.tester.accessor;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.junit.jupiter.api.Assertions;
import org.opentest4j.AssertionFailedError;

/**
 * Base class for all kinds of accessor objects.
 *
 * @param <O> the actual type providing access to.
 */
public class Accessor<O> implements Supplier<O> {

	private final O object;

	/**
	 * Constructs a new {@linkplain Accessor} instance.
	 *
	 * @param object the object to access.
	 */
	protected Accessor(O object) {
		this.object = object;
	}

	@Override
	public O get() {
		return this.object;
	}

	/**
	 * Accesses an object and makes sure it is not {@code null}.
	 * <p>
	 * If the submitted object is {@code null} a test failure is signaled.
	 * </p>
	 *
	 * @param object the object to access.
	 * @param message the message to use to signal a test failure.
	 * @return the checked object.
	 * @param <T> the actual object type.
	 */
	public static <T> @NonNull T accessNonNull(@Nullable T object, String message) {
		Assertions.assertNotNull(object, message);
		return Objects.requireNonNull(object);
	}

	/**
	 * Accesses the unique object from an one-element {@linkplain Stream}.
	 * <p>
	 * If the submitted {@linkplain Stream} contains 0 or more than 1 element a test failure is signaled.
	 * </p>
	 *
	 * @param stream the {@linkplain Stream} to retrieve the object from.
	 * @param mapper the {@linkplain Function} to use for the result type mapping.
	 * @return the unique stream element.
	 * @param <T> the actual stream element type.
	 * @param <R> the actual return type the stream element is mapped to.
	 */
	public static <T, R> R accessUnique(Stream<T> stream, Function<T, R> mapper) {
		return Accessor.assertPresent(stream.reduce(Accessor::assertUnique).map(mapper), "Object not found");
	}

	private static <T> T assertUnique(T o1, T o2) {
		Assertions.fail("Object not unique (o1: " + o1 + "; o2: " + o2 + ")");
		return o1;
	}

	private static <T> T assertPresent(Optional<T> optional, String message) {
		return optional.orElseThrow(() -> new AssertionFailedError(message));
	}

}
