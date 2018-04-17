/*
 * Copyright (c) 2007-2018 Holger de Carne and contributors, All Rights Reserved.
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
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;

import de.carne.swt.events.EventConsumer;
import de.carne.swt.events.EventReceiver;

/**
 * {@linkplain Shell} builder.
 */
public class ShellBuilder extends CompositeBuilder<Shell> {

	/**
	 * Constructs a new {@linkplain ShellBuilder} instance.
	 *
	 * @param shell the {@linkplain Shell} widget to build up.
	 */
	public ShellBuilder(Shell shell) {
		super(shell);
	}

	/**
	 * Sets the {@linkplain Shell}'s text.
	 *
	 * @param text the text to set.
	 * @return the updated builder.
	 * @see Shell#setText(String)
	 */
	public ShellBuilder withText(String text) {
		get().setText(text);
		return this;
	}

	/**
	 * Sets the {@linkplain Shell}'s images.
	 *
	 * @param images the {@linkplain Image}s to set.
	 * @return the updated builder.
	 * @see Shell#setImages(Image[])
	 */
	public ShellBuilder withImages(Image[] images) {
		get().setImages(images);
		return this;
	}

	/**
	 * Sets the {@linkplain Shell}'s images.
	 *
	 * @param images the {@linkplain Image}s to set.
	 * @return the updated builder.
	 * @see Shell#setImages(Image[])
	 */
	public ShellBuilder withImages(Supplier<Image[]> images) {
		return withImages(images.get());
	}

	/**
	 * Sets the activated {@linkplain ShellEvent} action.
	 *
	 * @param action the action to set.
	 * @return the updated builder.
	 * @see Shell#addShellListener(org.eclipse.swt.events.ShellListener)
	 */
	public ShellBuilder onShellActivated(Consumer<ShellEvent> action) {
		EventConsumer<ShellEvent> listener = EventConsumer.shellEvent(action);

		get().addListener(SWT.Activate, listener);
		return this;
	}

	/**
	 * Sets the activated {@linkplain ShellEvent} action.
	 *
	 * @param action the action to set.
	 * @return the updated builder.
	 * @see Shell#addShellListener(org.eclipse.swt.events.ShellListener)
	 */
	public ShellBuilder onShellActivated(Runnable action) {
		EventReceiver listener = EventReceiver.any(action);

		get().addListener(SWT.Activate, listener);
		return this;
	}

	/**
	 * Sets the deactivated {@linkplain ShellEvent} action.
	 *
	 * @param action the action to set.
	 * @return the updated builder.
	 * @see Shell#addShellListener(org.eclipse.swt.events.ShellListener)
	 */
	public ShellBuilder onShellDeactivated(Consumer<ShellEvent> action) {
		EventConsumer<ShellEvent> listener = EventConsumer.shellEvent(action);

		get().addListener(SWT.Deactivate, listener);
		return this;
	}

	/**
	 * Sets the deactivated {@linkplain ShellEvent} action.
	 *
	 * @param action The action to set.
	 * @return the updated builder.
	 * @see Shell#addShellListener(org.eclipse.swt.events.ShellListener)
	 */
	public ShellBuilder onShellDeactivated(Runnable action) {
		EventReceiver listener = EventReceiver.any(action);

		get().addListener(SWT.Deactivate, listener);
		return this;
	}

	/**
	 * Sets the iconified {@linkplain ShellEvent} action.
	 *
	 * @param action the action to set.
	 * @return the updated builder.
	 * @see Shell#addShellListener(org.eclipse.swt.events.ShellListener)
	 */
	public ShellBuilder onShellIconified(Consumer<ShellEvent> action) {
		EventConsumer<ShellEvent> listener = EventConsumer.shellEvent(action);

		get().addListener(SWT.Iconify, listener);
		return this;
	}

	/**
	 * Sets the iconified {@linkplain ShellEvent} action.
	 *
	 * @param action the action to set.
	 * @return the updated builder.
	 * @see Shell#addShellListener(org.eclipse.swt.events.ShellListener)
	 */
	public ShellBuilder onShellIconified(Runnable action) {
		EventReceiver listener = EventReceiver.any(action);

		get().addListener(SWT.Iconify, listener);
		return this;
	}

	/**
	 * Sets the deiconified {@linkplain ShellEvent} action.
	 *
	 * @param action the action to set.
	 * @return the updated builder.
	 * @see Shell#addShellListener(org.eclipse.swt.events.ShellListener)
	 */
	public ShellBuilder onShellDeiconified(Consumer<ShellEvent> action) {
		EventConsumer<ShellEvent> listener = EventConsumer.shellEvent(action);

		get().addListener(SWT.Deiconify, listener);
		return this;
	}

	/**
	 * Sets the deiconified {@linkplain ShellEvent} action.
	 *
	 * @param action the action to set.
	 * @return the updated builder.
	 * @see Shell#addShellListener(org.eclipse.swt.events.ShellListener)
	 */
	public ShellBuilder onShellDeiconified(Runnable action) {
		EventReceiver listener = EventReceiver.any(action);

		get().addListener(SWT.Deiconify, listener);
		return this;
	}

	/**
	 * Sets the closed {@linkplain ShellEvent} action.
	 *
	 * @param action the action to set.
	 * @return the updated builder.
	 * @see Shell#addShellListener(org.eclipse.swt.events.ShellListener)
	 */
	public ShellBuilder onShellClosed(Consumer<ShellEvent> action) {
		EventConsumer<ShellEvent> listener = EventConsumer.shellEvent(action);

		get().addListener(SWT.Close, listener);
		return this;
	}

	/**
	 * Sets the closed {@linkplain ShellEvent} action.
	 *
	 * @param action the action to set.
	 * @return the updated builder.
	 * @see Shell#addShellListener(org.eclipse.swt.events.ShellListener)
	 */
	public ShellBuilder onShellClosed(Runnable action) {
		EventReceiver listener = EventReceiver.any(action);

		get().addListener(SWT.Close, listener);
		return this;
	}

}
