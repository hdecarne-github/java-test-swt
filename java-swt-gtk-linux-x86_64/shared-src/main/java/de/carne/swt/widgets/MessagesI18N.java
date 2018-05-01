/*
 * I18N resource strings (automatically generated - do not edit)
 */
package de.carne.swt.widgets;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Resource bundle: de/carne/swt/widgets/MessagesI18N.properties
 */
public final class MessagesI18N {

	/**
	 * The name of the {@linkplain ResourceBundle} wrapped by this class.
	 */
	public static final String BUNDLE_NAME = MessagesI18N.class.getName();

	/**
	 * The {@linkplain ResourceBundle} wrapped by this class.
	 */
	public static final ResourceBundle BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private MessagesI18N() {
		// Prevent instantiation
	}

	/**
	 * Format a resource string.
	 * @param key The resource key.
	 * @param arguments Format arguments.
	 * @return The formatted string.
	 */
	public static String format(String key, Object... arguments) {
		String pattern = BUNDLE.getString(key);

		return MessageFormat.format(pattern, arguments);
	}

	/**
	 * Resource key {@code I18N_MESSAGE_UNEXPECTED_EXCEPTION}
	 * <p>
	 * An unexpected exception has been encountered.<br>Cause: {0}
	 */
	public static final String I18N_MESSAGE_UNEXPECTED_EXCEPTION = "I18N_MESSAGE_UNEXPECTED_EXCEPTION";

	/**
	 * Resource string {@code I18N_MESSAGE_UNEXPECTED_EXCEPTION}
	 * <p>
	 * An unexpected exception has been encountered.<br>Cause: {0}
	 *
	 * @param arguments Format arguments.
	 * @return The formatted string.
	 */
	public static String i18nMessageUnexpectedException(Object... arguments) {
		return format(I18N_MESSAGE_UNEXPECTED_EXCEPTION, arguments);
	}

	/**
	 * Resource key {@code I18N_TEXT_UNEXPECTED_EXCEPTION}
	 * <p>
	 * Unexpected exception
	 */
	public static final String I18N_TEXT_UNEXPECTED_EXCEPTION = "I18N_TEXT_UNEXPECTED_EXCEPTION";

	/**
	 * Resource string {@code I18N_TEXT_UNEXPECTED_EXCEPTION}
	 * <p>
	 * Unexpected exception
	 *
	 * @param arguments Format arguments.
	 * @return The formatted string.
	 */
	public static String i18nTextUnexpectedException(Object... arguments) {
		return format(I18N_TEXT_UNEXPECTED_EXCEPTION, arguments);
	}

}
