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

import java.util.Optional;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;

/**
 * Accessor class for {@linkplain CoolBar} objects.
 */
public class CoolBarAccessor extends ControlAccessor<CoolBar> {

	/**
	 * Constructs a new {@linkplain CoolBarAccessor} instance.
	 *
	 * @param coolBar the {@linkplain CoolBar} object to access.
	 */
	public CoolBarAccessor(@Nullable CoolBar coolBar) {
		super(coolBar);
	}

	/**
	 * Constructs a new {@linkplain CoolBarAccessor} instance.
	 *
	 * @param coolBarHolder the optional {@linkplain CoolBar} object to access.
	 */
	public CoolBarAccessor(Optional<CoolBar> coolBarHolder) {
		super(coolBarHolder);
	}

	/**
	 * Wraps a {@linkplain CoolBar} object for further accessor based processing.
	 *
	 * @param optionalCoolBar the optional {@linkplain CoolBar} object to wrap.
	 * @return the wrapped optional {@linkplain CoolBar} object.
	 */
	public static CoolBarAccessor wrap(Optional<CoolBar> optionalCoolBar) {
		return new CoolBarAccessor(optionalCoolBar);
	}

	/**
	 * Convenience function which gets a specific {@linkplain CoolItem} from this {@linkplain CoolBar}.
	 * <p>
	 * A test failure is signaled if the {@linkplain CoolItem} does not exist.
	 * </p>
	 *
	 * @param itemIndex the item index to get.
	 * @return the found {@linkplain CoolItem}.
	 */
	public CoolItemAccessor accessCoolItem(int itemIndex) {
		Optional<? extends CoolBar> optionalCoolBar = getOptional();

		return new CoolItemAccessor(optionalCoolBar.isPresent() ? getCoolItem(optionalCoolBar.get(), itemIndex) : null);
	}

	private @Nullable CoolItem getCoolItem(CoolBar coolBar, int itemIndex) {
		return (0 <= itemIndex && itemIndex < coolBar.getItemCount() ? coolBar.getItem(itemIndex) : null);
	}

}
