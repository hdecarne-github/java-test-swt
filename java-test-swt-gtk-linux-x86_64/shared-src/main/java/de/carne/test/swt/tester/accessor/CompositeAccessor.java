/*
 * Copyright (c) 2017-2020 Holger de Carne and contributors, All Rights Reserved.
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
import java.util.Arrays;
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
	 * @param depthFirst whether to collect depths first or not.
	 * @return all child {@linkplain Control}s of this {@linkplain Composite}.
	 */
	public Stream<Control> children(int maxDepth, boolean depthFirst) {
		Optional<? extends Composite> optionalComposite = getOptional();

		return (optionalComposite.isPresent()
				? collectChildren(new ArrayList<>(), get(), 0, maxDepth, depthFirst).stream()
				: Stream.empty());
	}

	@SuppressWarnings("squid:S3776")
	private List<Control> collectChildren(List<Control> children, Composite composite, int depth, int maxDepth,
			boolean depthFirst) {
		if (depth <= maxDepth) {
			@NonNull Control[] controls = composite.getChildren();

			if (depthFirst) {
				for (Control control : controls) {
					children.add(control);
					if (control instanceof Composite) {
						collectChildren(children, (Composite) control, depth + 1, maxDepth, depthFirst);
					}
				}
			} else {
				children.addAll(Arrays.asList(controls));
				for (Control control : controls) {
					if (control instanceof Composite) {
						collectChildren(children, (Composite) control, depth + 1, maxDepth, depthFirst);
					}
				}
			}
		}
		return children;
	}

	/**
	 * Convenience function which gets a specific child {@linkplain Control}.
	 * <p>
	 * A test failure is signaled if either none or more than one matching {@linkplain Control} exists.
	 * </p>
	 *
	 * @param <C> the actual control type to access.
	 * @param <A> the actual control accessor type.
	 * @param wrap the function to use to wrap the found control.
	 * @param childClass the type of requested control.
	 * @param predicate the match criteria to use.
	 * @return the found {@linkplain Control}.
	 */
	public <C extends Control, A extends Accessor<C>> @NonNull A accessChild(WrapFunction<C, A> wrap,
			Class<C> childClass, Predicate<C> predicate) {
		return wrap.apply(children().filter(ControlAccessor.matchClass(childClass)).map(childClass::cast)
				.filter(predicate).collect(Unique.getOptional()));
	}

	/**
	 * Convenience function which gets a specific child {@linkplain Control}.
	 * <p>
	 * A test failure is signaled if the requested {@linkplain Control} does not exist.
	 * </p>
	 *
	 * @param <C> the actual control type to access.
	 * @param <A> the actual control accessor type.
	 * @param wrap the function to use to wrap the found control.
	 * @param childClass the type of requested control.
	 * @param childIndex the child index to access.
	 * @return the found {@linkplain Control}.
	 */
	public <C extends Control, A extends Accessor<?>> A accessChild(WrapFunction<C, A> wrap, Class<C> childClass,
			int childIndex) {
		Optional<? extends Composite> optionalComposite = getOptional();
		@Nullable C child = null;

		if (optionalComposite.isPresent()) {
			Control[] children = optionalComposite.get().getChildren();

			if (0 <= childIndex && childIndex < children.length) {
				Control childControl = children[childIndex];

				if (childClass.isAssignableFrom(childControl.getClass())) {
					child = childClass.cast(childControl);
				}
			}
		}
		return wrap.apply(Optional.ofNullable(child));
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
		return accessChild(ButtonAccessor::new, Button.class, predicate);
	}

	/**
	 * Convenience function which gets a specific {@linkplain Button}.
	 * <p>
	 * A test failure is signaled if either none or more than one matching {@linkplain Button} exists.
	 * </p>
	 *
	 * @param text the button text to match.
	 * @return the found {@linkplain Button}.
	 */
	public ButtonAccessor accessButton(String text) {
		return accessChild(ButtonAccessor::new, Button.class, ButtonAccessor.matchText(text));
	}

	/**
	 * Convenience function which gets a specific {@linkplain Button}.
	 * <p>
	 * A test failure is signaled if either none or more than one matching {@linkplain Button} exists.
	 * </p>
	 *
	 * @param childIndex the child index to access.
	 * @return the found {@linkplain Button}.
	 */
	public ButtonAccessor accessButton(int childIndex) {
		return accessChild(ButtonAccessor::new, Button.class, childIndex);
	}

}
