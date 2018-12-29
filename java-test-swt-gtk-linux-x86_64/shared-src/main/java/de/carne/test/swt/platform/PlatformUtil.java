/*
 * Copyright (c) 2017-2018 Holger de Carne and contributors, All Rights Reserved.
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
package de.carne.test.swt.platform;

import org.eclipse.swt.SWT;

import de.carne.boot.Exceptions;
import de.carne.test.swt.DisableIfNotSWTCapable;
import de.carne.util.Lazy;

/**
 * Utility class providing platform specific functions.
 */
public abstract class PlatformUtil {

	private static final Lazy<PlatformUtil> INSTANCE_HOLDER = new Lazy<>(PlatformUtil::getInstance);

	private static PlatformUtil getInstance() {
		PlatformUtil instance;

		try {
			String basePackageName = DisableIfNotSWTCapable.class.getPackage().getName();
			String platformName = PlatformUtil.class.getName().replace(basePackageName,
					basePackageName + "." + SWT.getPlatform());

			instance = Class.forName(platformName).asSubclass(PlatformUtil.class).getConstructor().newInstance();
		} catch (ReflectiveOperationException e) {
			throw Exceptions.toRuntime(e);
		}
		return instance;
	}

	/**
	 * Constructs a new {@linkplain PlatformUtil} instance.
	 */
	protected PlatformUtil() {
		// Nothing to do here
	}

	/**
	 * Checks whether SWT code can be executed by the current thread.
	 * 
	 * @return {@code true} if the current thread can execute SWT code.
	 */
	public static boolean isCurrentThreadSWTCapable() {
		return INSTANCE_HOLDER.get().internalIsCurrentThreadSWTCapable();
	}

	protected boolean internalIsCurrentThreadSWTCapable() {
		return true;
	}

}
