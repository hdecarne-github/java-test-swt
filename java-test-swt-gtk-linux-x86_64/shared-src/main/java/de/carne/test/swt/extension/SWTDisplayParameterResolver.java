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
package de.carne.test.swt.extension;

import java.util.Optional;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.swt.widgets.Display;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import de.carne.boot.check.Check;

/**
 * {@linkplain ParameterResolver} that takes care of creation and disposal of a needed SWT {@linkplain Display} during
 * test execution.
 */
public class SWTDisplayParameterResolver implements ParameterResolver, AfterAllCallback {

	private static final Namespace EXTENSION_NAMESPACE = Namespace.create(SWTDisplayParameterResolver.class);
	private static final String DISPLAY_KEY = "Display";

	@Override
	public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
		return parameterContext.getParameter().getType().equals(Display.class);
	}

	@Override
	public @Nullable Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
		Optional<@Nullable ExtensionContext> optionalParentExtensionContext = extensionContext.getParent();

		if (!optionalParentExtensionContext.isPresent()) {
			throw new ParameterResolutionException("Parent extension context missing");
		}

		Store store = optionalParentExtensionContext.get().getStore(EXTENSION_NAMESPACE);
		Object displayObject = store.get(DISPLAY_KEY);

		if (displayObject == null) {
			displayObject = new Display();
			store.put(DISPLAY_KEY, displayObject);
		}
		return Check.isInstanceOf(displayObject, Display.class);
	}

	@Override
	public void afterAll(ExtensionContext context) {
		Store store = context.getStore(EXTENSION_NAMESPACE);
		Object displayObject = store.get(DISPLAY_KEY);

		if (displayObject != null) {
			Check.isInstanceOf(displayObject, Display.class).dispose();
		}
	}

}
