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

import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.eclipse.swt.widgets.Decorations;

/**
 * Accessor class for {@linkplain Decorations} based widgets.
 *
 * @param <T> the actual type providing access to.
 */
public class DecorationsAccessor<T extends Decorations> extends Accessor<T> {

	/**
	 * Constructs a new {@linkplain DecorationsAccessor} instance.
	 *
	 * @param object the object to access.
	 */
	protected DecorationsAccessor(T object) {
		super(object);
	}

	/**
	 * Creates a {@linkplain Predicate} for exact title matching.
	 *
	 * @param title the title to match.
	 * @return the created {@linkplain Predicate}.
	 */
	public static <S extends Decorations> Predicate<S> matchTitle(String title) {
		return new Predicate<S>() {

			private final String matchingTitle = title;

			@Override
			public boolean test(S decorations) {
				return this.matchingTitle.equals(decorations.getText());
			}

		};
	}

	/**
	 * Creates a {@linkplain Predicate} for pattern title matching.
	 *
	 * @param titlePattern the pattern to match.
	 * @return the created {@linkplain Predicate}.
	 */
	public static <S extends Decorations> Predicate<S> matchTitle(Pattern titlePattern) {
		return new Predicate<S>() {

			private final Pattern matchingTitlePattern = titlePattern;

			@Override
			public boolean test(S decorations) {
				return this.matchingTitlePattern.matcher(decorations.getText()).matches();
			}

		};
	}

}
