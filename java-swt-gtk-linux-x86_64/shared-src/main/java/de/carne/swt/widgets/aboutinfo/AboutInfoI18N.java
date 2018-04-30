/*
 * I18N resource strings (automatically generated - do not edit)
 */
package de.carne.swt.widgets.aboutinfo;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Resource bundle: de/carne/swt/widgets/aboutinfo/AboutInfoI18N.properties
 */
public final class AboutInfoI18N {

	/**
	 * The name of the {@linkplain ResourceBundle} wrapped by this class.
	 */
	public static final String BUNDLE_NAME = AboutInfoI18N.class.getName();

	/**
	 * The {@linkplain ResourceBundle} wrapped by this class.
	 */
	public static final ResourceBundle BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private AboutInfoI18N() {
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
	 * Resource key {@code I18N_LABEL_BUILD}
	 * <p>
	 * Build: {0}
	 */
	public static final String I18N_LABEL_BUILD = "I18N_LABEL_BUILD";

	/**
	 * Resource string {@code I18N_LABEL_BUILD}
	 * <p>
	 * Build: {0}
	 *
	 * @param arguments Format arguments.
	 * @return The formatted string.
	 */
	public static String i18nLabelBuild(Object... arguments) {
		return format(I18N_LABEL_BUILD, arguments);
	}

	/**
	 * Resource key {@code I18N_LABEL_TITLE}
	 * <p>
	 * {0}
	 */
	public static final String I18N_LABEL_TITLE = "I18N_LABEL_TITLE";

	/**
	 * Resource string {@code I18N_LABEL_TITLE}
	 * <p>
	 * {0}
	 *
	 * @param arguments Format arguments.
	 * @return The formatted string.
	 */
	public static String i18nLabelTitle(Object... arguments) {
		return format(I18N_LABEL_TITLE, arguments);
	}

	/**
	 * Resource key {@code I18N_LABEL_VERSION}
	 * <p>
	 * Version: {0}
	 */
	public static final String I18N_LABEL_VERSION = "I18N_LABEL_VERSION";

	/**
	 * Resource string {@code I18N_LABEL_VERSION}
	 * <p>
	 * Version: {0}
	 *
	 * @param arguments Format arguments.
	 * @return The formatted string.
	 */
	public static String i18nLabelVersion(Object... arguments) {
		return format(I18N_LABEL_VERSION, arguments);
	}

	/**
	 * Resource key {@code I18N_TITLE}
	 * <p>
	 * About {0}
	 */
	public static final String I18N_TITLE = "I18N_TITLE";

	/**
	 * Resource string {@code I18N_TITLE}
	 * <p>
	 * About {0}
	 *
	 * @param arguments Format arguments.
	 * @return The formatted string.
	 */
	public static String i18nTitle(Object... arguments) {
		return format(I18N_TITLE, arguments);
	}

}
