/*
 * Copyright (c) 2007-2017 Holger de Carne and contributors, All Rights Reserved.
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
package de.carne.swt.widgets;

import java.util.function.Consumer;
import java.util.function.Supplier;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Control;

import de.carne.swt.events.EventConsumer;
import de.carne.swt.events.EventReceiver;

/**
 * {@linkplain Control} builder.
 *
 * @param <T> The actual control type.
 */
public class ControlBuilder<T extends Control> implements Supplier<T> {

	private final T control;

	/**
	 * Construct {@linkplain ControlBuilder}.
	 *
	 * @param control The control to build.
	 */
	public ControlBuilder(T control) {
		this.control = control;
	}

	/**
	 * Get the {@linkplain Control} the builder operates on.
	 *
	 * @return The {@linkplain Control} the builder operates on.
	 */
	@Override
	public T get() {
		return this.control;
	}

	/**
	 * Resize the {@linkplain Control} to it's preferred size.
	 *
	 * @return The updated builder.
	 * @see Control#pack()
	 */
	public ControlBuilder<T> pack() {
		this.control.pack(true);
		return this;
	}

	/**
	 * Set {@linkplain DisposeEvent} action.
	 *
	 * @param action The action to set.
	 * @return The updated builder.
	 * @see Control#addDisposeListener(org.eclipse.swt.events.DisposeListener)
	 */
	public ControlBuilder<T> onDisposed(Consumer<DisposeEvent> action) {
		EventConsumer<DisposeEvent> listener = EventConsumer.disposed(action);

		this.control.addListener(SWT.Dispose, listener);
		return this;
	}

	/**
	 * Set {@linkplain DisposeEvent} action.
	 *
	 * @param action The action to set.
	 * @return The updated builder.
	 * @see Control#addDisposeListener(org.eclipse.swt.events.DisposeListener)
	 */
	public ControlBuilder<T> onDisposed(Runnable action) {
		EventReceiver listener = EventReceiver.any(action);

		this.control.addListener(SWT.Dispose, listener);
		return this;
	}

	/**
	 * Set {@linkplain SelectionEvent} action.
	 *
	 * @param action The action to set.
	 * @return The updated builder.
	 */
	public ControlBuilder<T> onSelected(Consumer<SelectionEvent> action) {
		EventConsumer<SelectionEvent> listener = EventConsumer.selected(action);

		this.control.addListener(SWT.Selection, listener);
		this.control.addListener(SWT.DefaultSelection, listener);
		return this;
	}

	/**
	 * Set {@linkplain SelectionEvent} action.
	 *
	 * @param action The action to set.
	 * @return The updated builder.
	 */
	public ControlBuilder<T> onSelected(Runnable action) {
		EventReceiver listener = EventReceiver.any(action);

		this.control.addListener(SWT.Selection, listener);
		this.control.addListener(SWT.DefaultSelection, listener);
		return this;
	}

}
