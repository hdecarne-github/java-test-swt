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

import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.swt.widgets.Display;

import de.carne.util.Late;

/**
 * Utility class used to forward exceptions caused by synchronous the UI thread calls to the actual
 * {@linkplain Display#syncExec(Runnable)} caller.
 */
public final class SyncErrorHandler implements AutoCloseable {

	private final Display display;
	private final Late<Consumer<RuntimeException>> savedRuntimeExceptionHandler = new Late<>();
	private final Late<Consumer<Error>> savedErrorHandler = new Late<>();
	private @Nullable RuntimeException runtimeException = null;
	private @Nullable Error error = null;

	/**
	 * Constructs a new {@code SyncErrorHandler} instance.
	 *
	 * @param display the {@linkplain Display} assigned to the targeted UI thread.
	 */
	public SyncErrorHandler(Display display) {
		this.display = display;
		initErrorHandler();
	}

	private void initErrorHandler() {
		if (Thread.currentThread().equals(this.display.getThread())) {
			this.savedRuntimeExceptionHandler.set(this.display.getRuntimeExceptionHandler());
			this.savedErrorHandler.set(this.display.getErrorHandler());
			this.display.setRuntimeExceptionHandler(this::handleRuntimeException);
			this.display.setErrorHandler(this::handleError);
		} else {
			this.display.syncExec(this::initErrorHandler);
		}
	}

	private void restoreErrorHandler() {
		if (Thread.currentThread().equals(this.display.getThread())) {
			this.display.setRuntimeExceptionHandler(this.savedRuntimeExceptionHandler.get());
			this.display.setErrorHandler(this.savedErrorHandler.get());
		} else {
			this.display.syncExec(this::restoreErrorHandler);
		}
	}

	private void handleRuntimeException(RuntimeException exception) {
		this.runtimeException = exception;
	}

	private void handleError(Error exception) {
		this.error = exception;
	}

	@Override
	public void close() {
		restoreErrorHandler();
		if (this.runtimeException != null) {
			throw this.runtimeException;
		}
		if (this.error != null) {
			throw this.error;
		}
	}

}
