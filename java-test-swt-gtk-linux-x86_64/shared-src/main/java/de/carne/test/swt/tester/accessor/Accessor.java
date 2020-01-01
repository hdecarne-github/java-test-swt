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
package de.carne.test.swt.tester.accessor;

import java.util.Optional;
import java.util.function.Supplier;

import org.eclipse.jdt.annotation.Nullable;
import org.opentest4j.AssertionFailedError;

/**
 * Base class for all kinds of accessor classes.
 *
 * @param <T> the actual object type to access.
 */
public class Accessor<T> implements Supplier<T> {

	private final Optional<T> objectHolder;

	/**
	 * Constructs a new {@linkplain Accessor} instance.
	 *
	 * @param object the object to access.
	 */
	public Accessor(@Nullable T object) {
		this.objectHolder = Optional.ofNullable(object);
	}

	/**
	 * Constructs a new {@linkplain Accessor} instance.
	 *
	 * @param objectHolder to the optional object to access.
	 */
	public Accessor(Optional<T> objectHolder) {
		this.objectHolder = objectHolder;
	}

	/**
	 * Constructs a new {@linkplain Accessor} instance.
	 *
	 * @param accessor accessor to the object to access.
	 */
	public Accessor(Accessor<T> accessor) {
		this(accessor.objectHolder);
	}

	/**
	 * Gets the object instance wrapped by this accessor.
	 * <p>
	 * A test failure is signaled if the accessor is empty.
	 * </p>
	 *
	 * @return the object instance wrapped by this accessor.
	 */
	@Override
	public T get() {
		return this.objectHolder.orElseThrow(() -> new AssertionFailedError("No such object"));
	}

	/**
	 * Gets the optional object instance wrapped by this accessor.
	 *
	 * @return the optional object instance wrapped by this accessor.
	 */
	public Optional<T> getOptional() {
		return this.objectHolder;
	}

	/**
	 * Convenience function to access an object instance directly.
	 * <p>
	 * A test failure is signaled if the given object is {@code null}.
	 * </p>
	 *
	 * @param <T> the object type to access.
	 * @param object the object instance to access (may be {@code null}).
	 * @return the object instance.
	 */
	public static <T> T get(@Nullable T object) {
		return new Accessor<>(object).get();
	}

}
