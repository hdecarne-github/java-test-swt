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

import java.util.function.Function;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Layout;
import org.eclipse.swt.widgets.ToolBar;

/**
 * {@linkplain Composite} builder.
 *
 * @param <T> The actual composite type.
 */
public class CompositeBuilder<T extends Composite> extends ControlBuilder<T> {

	/**
	 * Construct {@linkplain CompositeBuilder}.
	 *
	 * @param composite The {@linkplain Composite} widget to build up.
	 */
	public CompositeBuilder(T composite) {
		super(composite);
	}

	/**
	 * Set the {@linkplain Composite}'s {@linkplain Layout}.
	 *
	 * @param layout The {@linkplain Layout} to set.
	 * @see Composite#setLayout(Layout)
	 */
	public void setLayout(Layout layout) {
		get().setLayout(layout);
	}

	/**
	 * Add a child control to the {@linkplain Composite}.
	 *
	 * @param <C> The actual control type (either direct or wrapped in a builder).
	 * @param createChild The function to use to create the new control.
	 * @return The added child control.
	 */
	public <C> C addChild(Function<T, C> createChild) {
		return createChild.apply(get());
	}

	/**
	 * Add a child of type {@linkplain Composite}.
	 *
	 * @param style The {@linkplain Composite} style.
	 * @return The added child control.
	 */
	public CompositeBuilder<Composite> addCompositeChild(int style) {
		return addChild(parent -> new CompositeBuilder<>(new Composite(parent, style)));
	}

	/**
	 * Add a child of type {@linkplain Group}.
	 *
	 * @param style The {@linkplain Group} style.
	 * @return The added child control.
	 */
	public CompositeBuilder<Group> addGroupChild(int style) {
		return addChild(parent -> new CompositeBuilder<>(new Group(parent, style)));
	}

	/**
	 * Add a child of type {@linkplain CoolBar}.
	 *
	 * @param style The {@linkplain CoolBar} style.
	 * @return The added child control.
	 */
	public CoolBarBuilder addCoolBarChild(int style) {
		return addChild(parent -> new CoolBarBuilder(new CoolBar(parent, style)));
	}

	/**
	 * Add a child of type {@linkplain ToolBar}.
	 *
	 * @param style The {@linkplain ToolBar} style.
	 * @return The added child control.
	 */
	public ToolBarBuilder addToolBarChild(int style) {
		return addChild(parent -> new ToolBarBuilder(new ToolBar(parent, style)));
	}

}
