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

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.ToolItem;

import de.carne.util.stream.Unique;

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
	 * Constructs a new {@linkplain CoolBarAccessor} instance.
	 *
	 * @param accessor the accessor to the {@linkplain CoolBar} instance to access.
	 */
	public CoolBarAccessor(Accessor<CoolBar> accessor) {
		super(accessor);
	}

	/**
	 * Gets all {@linkplain CoolItem}s of this {@linkplain CoolBar}.
	 *
	 * @return all {@linkplain CoolItem}s of this {@linkplain CoolBar}.
	 */
	public Stream<CoolItem> items() {
		Optional<CoolBar> optionalCoolBar = getOptional();

		return (optionalCoolBar.isPresent() ? Arrays.stream(optionalCoolBar.get().getItems()) : Stream.empty());
	}

	/**
	 * Convenience function which gets a specific {@linkplain CoolItem}.
	 * <p>
	 * A test failure is signaled if either none or more than one matching {@linkplain ToolItem} exists.
	 * </p>
	 *
	 * @param predicate the match criteria to use.
	 * @return the found {@linkplain CoolItem}.
	 */
	public CoolItemAccessor accessItem(Predicate<CoolItem> predicate) {
		return new CoolItemAccessor(items().filter(predicate).collect(Unique.getOptional()));
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
	public CoolItemAccessor accessItem(int itemIndex) {
		Optional<CoolBar> optionalCoolBar = getOptional();

		return new CoolItemAccessor(optionalCoolBar.isPresent() ? getCoolItem(optionalCoolBar.get(), itemIndex) : null);
	}

	private @Nullable CoolItem getCoolItem(CoolBar coolBar, int itemIndex) {
		return (0 <= itemIndex && itemIndex < coolBar.getItemCount() ? coolBar.getItem(itemIndex) : null);
	}

}
