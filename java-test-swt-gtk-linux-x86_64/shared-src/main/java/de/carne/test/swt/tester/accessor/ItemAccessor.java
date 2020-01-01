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
import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.swt.widgets.Item;

/**
 * Accessor class for {@linkplain Item} objects.
 *
 * @param <T> the actual object type to access.
 */
public class ItemAccessor<T extends Item> extends Accessor<T> {

	/**
	 * Constructs a new {@linkplain ItemAccessor} instance.
	 *
	 * @param item the {@linkplain Item} instance to access.
	 */
	public ItemAccessor(@Nullable T item) {
		super(item);
	}

	/**
	 * Constructs a new {@linkplain ItemAccessor} instance.
	 *
	 * @param optionalItem the optional {@linkplain Item} instance to access.
	 */
	public ItemAccessor(Optional<T> optionalItem) {
		super(optionalItem);
	}

	/**
	 * Constructs a new {@linkplain ItemAccessor} instance.
	 *
	 * @param accessor the accessor to the {@linkplain Item} instance to access.
	 */
	public ItemAccessor(ItemAccessor<T> accessor) {
		super(accessor);
	}

	/**
	 * Creates a {@linkplain Predicate} for exact text matching.
	 *
	 * @param text the text to match.
	 * @return the created {@linkplain Predicate}.
	 * @param <S> the actual widget type.
	 */
	public static <S extends Item> Predicate<S> matchText(String text) {
		return decorations -> text.equals(decorations.getText());
	}

	/**
	 * Creates a {@linkplain Predicate} for pattern text matching.
	 *
	 * @param textPattern the pattern to match.
	 * @return the created {@linkplain Predicate}.
	 * @param <S> the actual widget type.
	 */
	public static <S extends Item> Predicate<S> matchText(Pattern textPattern) {
		return decorations -> textPattern.matcher(decorations.getText()).matches();
	}

}
