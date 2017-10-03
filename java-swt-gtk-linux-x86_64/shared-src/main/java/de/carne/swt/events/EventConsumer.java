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
package de.carne.swt.events;

import java.util.function.Consumer;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.TypedEvent;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import de.carne.check.Nullable;

/**
 * Action listener for typed SWT events.
 *
 * @param <T> The event type to listen for.
 */
public class EventConsumer<T extends TypedEvent> implements Listener {

	private final Class<T> eventType;
	private final Consumer<T> consumer;

	private EventConsumer(Class<T> eventType, Consumer<T> consumer) {
		this.eventType = eventType;
		this.consumer = consumer;
	}

	/**
	 * {@linkplain SelectionEvent} action.
	 *
	 * @param consumer The action consumer.
	 * @return The action listener.
	 */
	public static EventConsumer<SelectionEvent> selected(Consumer<SelectionEvent> consumer) {
		return new EventConsumer<>(SelectionEvent.class, consumer);
	}

	@Override
	public void handleEvent(@Nullable Event event) {
		this.consumer.accept(this.eventType.cast(event));
	}

}
