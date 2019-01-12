/*
 * Copyright (c) 2017-2019 Holger de Carne and contributors, All Rights Reserved.
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
package de.carne.test.swt.tester.accessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jdt.annotation.Nullable;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import de.carne.util.stream.Unique;

/**
 * Accessor class for {@linkplain Composite} objects.
 *
 * @param <T> the actual object type to access.
 */
public class CompositeAccessor<T extends Composite> extends ControlAccessor<T> {

	/**
	 * Constructs a new {@linkplain CompositeAccessor} instance.
	 *
	 * @param composite the {@linkplain Composite} instance to access.
	 */
	public CompositeAccessor(@Nullable T composite) {
		super(composite);
	}

	/**
	 * Constructs a new {@linkplain CompositeAccessor} instance.
	 *
	 * @param compositeHolder the optional {@linkplain Composite} instance to access.
	 */
	public CompositeAccessor(Optional<T> compositeHolder) {
		super(compositeHolder);
	}

	/**
	 * Constructs a new {@linkplain CompositeAccessor} instance.
	 *
	 * @param accessor the accessor to the {@linkplain Composite} instance to access.
	 */
	public CompositeAccessor(CompositeAccessor<T> accessor) {
		super(accessor);
	}

	/**
	 * Gets all child {@linkplain Control}s of this {@linkplain Composite}.
	 * <p>
	 * Calling this function is equivalent to calling {@code children(Integer.MAX_VALUE, true)}.
	 * </p>
	 *
	 * @return all child {@linkplain Control}s of this {@linkplain Composite}.
	 * @see #children(int, boolean)
	 */
	public Stream<Control> children() {
		return children(Integer.MAX_VALUE, true);
	}

	/**
	 * Gets all child {@linkplain Control}s of this {@linkplain Composite}.
	 * <p>
	 * Calling this function is equivalent to calling {@code children(Integer.MAX_VALUE, true)}.
	 * </p>
	 *
	 * @param maxDepth the maximum number of sub-levels to search for child controls.
	 * @param depthsFirst whether to collect depths first or not.
	 * @return all child {@linkplain Control}s of this {@linkplain Composite}.
	 */
	public Stream<Control> children(int maxDepth, boolean depthsFirst) {
		return collectChildren(new ArrayList<>(), get(), 0, maxDepth, depthsFirst).stream();
	}

	private List<Control> collectChildren(List<Control> children, Composite composite, int depth, int maxDepth,
			boolean depthsFirst) {
		if (depth <= maxDepth) {
			@NonNull Control[] controls = composite.getChildren();

			if (depthsFirst) {
				for (Control control : controls) {
					children.add(control);
					if (control instanceof Composite) {
						collectChildren(children, (Composite) control, depth + 1, maxDepth, depthsFirst);
					}
				}
			} else {
				for (Control control : controls) {
					children.add(control);
				}
				for (Control control : controls) {
					if (control instanceof Composite) {
						collectChildren(children, (Composite) control, depth + 1, maxDepth, depthsFirst);
					}
				}
			}
		}
		return children;
	}

	/**
	 * Convenience function which gets a specific {@linkplain Button}.
	 * <p>
	 * A test failure is signaled if either none or more than one matching {@linkplain Button} exists.
	 * </p>
	 *
	 * @param predicate the match criteria to use.
	 * @return the found {@linkplain Button}.
	 */
	public ButtonAccessor accessButton(Predicate<Button> predicate) {
		return new ButtonAccessor(children().filter(ControlAccessor.matchClass(Button.class))
				.map(ControlAccessor.mapClass(Button.class)).filter(predicate).collect(Unique.getOptional()));
	}

}
