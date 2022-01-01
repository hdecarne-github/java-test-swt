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
package de.carne.test.swt.tester.accessor;

import java.util.Optional;
import java.util.function.Predicate;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.swt.widgets.Control;

/**
 * Accessor class for {@linkplain Control} objects.
 *
 * @param <T> the actual object type to access.
 */
public class ControlAccessor<T extends Control> extends Accessor<T> {

	/**
	 * Constructs a new {@linkplain ControlAccessor} instance.
	 *
	 * @param control the {@linkplain Control} instance to access.
	 */
	public ControlAccessor(@Nullable T control) {
		super(control);
	}

	/**
	 * Constructs a new {@linkplain ControlAccessor} instance.
	 *
	 * @param controlHolder the optional {@linkplain Control} instance to access.
	 */
	public ControlAccessor(Optional<T> controlHolder) {
		super(controlHolder);
	}

	/**
	 * Constructs a new {@linkplain ControlAccessor} instance.
	 *
	 * @param accessor the accessor to the {@linkplain Control} instance to access.
	 */
	public ControlAccessor(Accessor<T> accessor) {
		super(accessor);
	}

	/**
	 * Gets the control only if it is enabled.
	 *
	 * @return the accessor for the enabled widget.
	 */
	public Accessor<T> accessEnabled() {
		Optional<T> optionalControl = getOptional();

		return (optionalControl.isPresent() && optionalControl.get().isEnabled() ? this : Accessor.notPresent());
	}

	/**
	 * Creates a {@linkplain Predicate} for control type matching.
	 *
	 * @param type the control type to match.
	 * @return the created {@linkplain Predicate}.
	 */
	public static Predicate<Control> matchClass(Class<? extends Control> type) {
		return control -> type.isAssignableFrom(control.getClass());
	}

}
