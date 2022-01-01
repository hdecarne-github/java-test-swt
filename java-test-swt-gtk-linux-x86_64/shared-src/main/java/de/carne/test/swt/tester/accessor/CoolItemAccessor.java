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

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.CoolItem;

/**
 * Accessor class for {@linkplain CoolItem} objects.
 */
public class CoolItemAccessor extends ItemAccessor<CoolItem> {

	/**
	 * Constructs a new {@linkplain CoolItemAccessor} instance.
	 *
	 * @param coolItem the {@linkplain CoolItem} object to access.
	 */
	public CoolItemAccessor(@Nullable CoolItem coolItem) {
		super(coolItem);
	}

	/**
	 * Constructs a new {@linkplain CoolItemAccessor} instance.
	 *
	 * @param optionalCoolItem the optional {@linkplain CoolItem} object to access.
	 */
	public CoolItemAccessor(Optional<CoolItem> optionalCoolItem) {
		super(optionalCoolItem);
	}

	/**
	 * Constructs a new {@linkplain CoolItemAccessor} instance.
	 *
	 * @param accessor the accessor to the {@linkplain CoolItem} instance to access.
	 */
	public CoolItemAccessor(Accessor<CoolItem> accessor) {
		super(accessor);
	}

	/**
	 * Gets the {@linkplain CoolItem}'s {@linkplain Control}.
	 * <p>
	 * A test failure is signaled if the requested {@linkplain Control} does not exist.
	 * </p>
	 *
	 * @param <C> the actual control type to access.
	 * @param <A> the actual control accessor type.
	 * @param wrap the function to use to wrap the found control.
	 * @param controlClass the type of requested control.
	 * @return the found {@linkplain Control}.
	 */
	public <C extends Control, A extends Accessor<C>> A accessControl(WrapFunction<C, A> wrap, Class<C> controlClass) {
		Optional<? extends CoolItem> optionalCoolItem = getOptional();
		@Nullable C control = null;

		if (optionalCoolItem.isPresent()) {
			Control coolItemControl = optionalCoolItem.get().getControl();

			if (coolItemControl != null && controlClass.isAssignableFrom(coolItemControl.getClass())) {
				control = controlClass.cast(coolItemControl);
			}
		}
		return wrap.apply(Optional.ofNullable(control));
	}

}
