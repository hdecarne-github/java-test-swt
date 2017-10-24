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
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Tree;

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
	 * Add a child of type {@linkplain Label}.
	 *
	 * @param style The {@linkplain Label} style.
	 * @return The added child control.
	 */
	public ControlBuilder<Label> addLabelChild(int style) {
		return addChild(parent -> new ControlBuilder<>(new Label(parent, style)));
	}

	/**
	 * Add a child of type {@linkplain List}.
	 *
	 * @param style The {@linkplain List} style.
	 * @return The added child control.
	 */
	public ControlBuilder<List> addListChild(int style) {
		return addChild(parent -> new ControlBuilder<>(new List(parent, style)));
	}

	/**
	 * Add a child of type {@linkplain Tree}.
	 *
	 * @param style The {@linkplain Tree} style.
	 * @return The added child control.
	 */
	public ControlBuilder<Tree> addTreeChild(int style) {
		return addChild(parent -> new ControlBuilder<>(new Tree(parent, style)));
	}

}
