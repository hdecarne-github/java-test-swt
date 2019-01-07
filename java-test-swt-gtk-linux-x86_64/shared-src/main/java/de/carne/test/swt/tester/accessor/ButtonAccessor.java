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

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;

/**
 * Accessor class for {@linkplain Button} objects.
 */
public class ButtonAccessor extends ControlAccessor<Button> {

	/**
	 * Constructs a new {@linkplain ButtonAccessor} instance.
	 *
	 * @param button the {@linkplain Button} instance to access.
	 */
	protected ButtonAccessor(Button button) {
		super(button);
	}

	/**
	 * Creates a {@linkplain Predicate} for exact text matching.
	 *
	 * @param text the text to match.
	 * @return the created {@linkplain Predicate}.
	 */
	public static Predicate<Button> matchText(String text) {
		return button -> text.equals(button.getText());
	}

	/**
	 * Creates a {@linkplain Predicate} for pattern text matching.
	 *
	 * @param textPattern the pattern to match.
	 * @return the created {@linkplain Predicate}.
	 */
	public static Predicate<Button> matchText(Pattern textPattern) {
		return button -> textPattern.matcher(button.getText()).matches();
	}

	/**
	 * Generate a selection event to the {@linkplain Button}.
	 */
	public void select() {
		get().notifyListeners(SWT.Selection, new Event());
	}

}