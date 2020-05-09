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
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;

/**
 * Accessor class for {@linkplain Button} objects.
 */
public class ButtonAccessor extends ControlAccessor<Button> {

	private static final ButtonAccessor NOT_PRESENT = new ButtonAccessor(Optional.empty());

	/**
	 * Constructs a new {@linkplain ButtonAccessor} instance.
	 *
	 * @param button the {@linkplain Button} instance to access.
	 */
	public ButtonAccessor(@Nullable Button button) {
		super(button);
	}

	/**
	 * Constructs a new {@linkplain ButtonAccessor} instance.
	 *
	 * @param buttonHolder the optional {@linkplain Button} instance to access.
	 */
	public ButtonAccessor(Optional<Button> buttonHolder) {
		super(buttonHolder);
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
	 * Gets the button only if it is enabled.
	 * 
	 * @return the accessor for the enabled button.
	 */
	public ButtonAccessor accessEnabled() {
		Optional<Button> optionalButton = getOptional();

		return (optionalButton.isPresent() && optionalButton.get().isEnabled() ? this : NOT_PRESENT);
	}

	/**
	 * Generate a selection event to the {@linkplain Button}.
	 */
	public void select() {
		get().notifyListeners(SWT.Selection, new Event());
	}

}
