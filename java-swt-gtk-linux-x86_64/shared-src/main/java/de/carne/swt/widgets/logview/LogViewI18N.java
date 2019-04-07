/*
 * I18N resource strings (automatically generated - do not edit)
 */
package de.carne.swt.widgets.logview;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Resource bundle: de/carne/swt/widgets/logview/LogViewI18N.properties
 */
public final class LogViewI18N {

	/**
	 * The name of the {@linkplain ResourceBundle} wrapped by this class.
	 */
	public static final String BUNDLE_NAME = LogViewI18N.class.getName();

	/**
	 * The {@linkplain ResourceBundle} wrapped by this class.
	 */
	public static final ResourceBundle BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private LogViewI18N() {
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
	 * Resource key {@code I18N_BUTTON_CLOSE}
	 * <p>
	 * Close
	 */
	public static final String I18N_BUTTON_CLOSE = "I18N_BUTTON_CLOSE";

	/**
	 * Resource string {@code I18N_BUTTON_CLOSE}
	 * <p>
	 * Close
	 *
	 * @param arguments Format arguments.
	 * @return The formatted string.
	 */
	public static String i18nButtonClose(Object... arguments) {
		return format(I18N_BUTTON_CLOSE, arguments);
	}

	/**
	 * Resource key {@code I18N_LABEL_LEVEL}
	 * <p>
	 * Level
	 */
	public static final String I18N_LABEL_LEVEL = "I18N_LABEL_LEVEL";

	/**
	 * Resource string {@code I18N_LABEL_LEVEL}
	 * <p>
	 * Level
	 *
	 * @param arguments Format arguments.
	 * @return The formatted string.
	 */
	public static String i18nLabelLevel(Object... arguments) {
		return format(I18N_LABEL_LEVEL, arguments);
	}

	/**
	 * Resource key {@code I18N_LABEL_MESSAGE}
	 * <p>
	 * Message
	 */
	public static final String I18N_LABEL_MESSAGE = "I18N_LABEL_MESSAGE";

	/**
	 * Resource string {@code I18N_LABEL_MESSAGE}
	 * <p>
	 * Message
	 *
	 * @param arguments Format arguments.
	 * @return The formatted string.
	 */
	public static String i18nLabelMessage(Object... arguments) {
		return format(I18N_LABEL_MESSAGE, arguments);
	}

	/**
	 * Resource key {@code I18N_LABEL_TIME}
	 * <p>
	 * Time
	 */
	public static final String I18N_LABEL_TIME = "I18N_LABEL_TIME";

	/**
	 * Resource string {@code I18N_LABEL_TIME}
	 * <p>
	 * Time
	 *
	 * @param arguments Format arguments.
	 * @return The formatted string.
	 */
	public static String i18nLabelTime(Object... arguments) {
		return format(I18N_LABEL_TIME, arguments);
	}

	/**
	 * Resource key {@code I18N_TITLE}
	 * <p>
	 * Log
	 */
	public static final String I18N_TITLE = "I18N_TITLE";

	/**
	 * Resource string {@code I18N_TITLE}
	 * <p>
	 * Log
	 *
	 * @param arguments Format arguments.
	 * @return The formatted string.
	 */
	public static String i18nTitle(Object... arguments) {
		return format(I18N_TITLE, arguments);
	}

}
