/*
 * Copyright (c) 2007-2019 Holger de Carne and contributors, All Rights Reserved.
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
package de.carne.swt.util;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.swt.widgets.Display;

/**
 * Utility class used to forward exceptions from the UI thread to the actual {@linkplain Display#syncExec(Runnable)}
 * caller.
 *
 * @param <T> the actual type of the guarded object.
 */
public final class SyncErrorHandler<T> implements AutoCloseable, Supplier<T> {

	private final Display display;
	private final T object;
	private final Consumer<RuntimeException> savedRuntimeExceptionHandler;
	private final Consumer<Error> savedErrorHandler;
	private @Nullable RuntimeException runtimeException = null;
	private @Nullable Error error = null;

	/**
	 * Constructs a new {@code SyncErrorHandler} instance.
	 *
	 * @param display the {@linkplain Display} assigned to the UI thread.
	 * @param object the object to guard.
	 */
	public SyncErrorHandler(Display display, T object) {
		this.display = display;
		this.object = object;
		this.savedRuntimeExceptionHandler = display.getRuntimeExceptionHandler();
		this.savedErrorHandler = display.getErrorHandler();
		this.display.setRuntimeExceptionHandler(this::handleRuntimeException);
		this.display.setErrorHandler(this::handleError);
	}

	@Override
	public T get() {
		return this.object;
	}

	@Override
	public void close() {
		this.display.setRuntimeExceptionHandler(this.savedRuntimeExceptionHandler);
		this.display.setErrorHandler(this.savedErrorHandler);
		if (this.runtimeException != null) {
			throw this.runtimeException;
		}
		if (this.error != null) {
			throw this.error;
		}
	}

	private void handleRuntimeException(RuntimeException exception) {
		this.runtimeException = exception;
	}

	private void handleError(Error exception) {
		this.error = exception;
	}

}
