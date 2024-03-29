/*
 * Copyright (c) 2017-2022 Holger de Carne and contributors, All Rights Reserved.
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

import java.util.Optional;
import java.util.function.Function;

import org.eclipse.swt.widgets.Control;

/**
 * Functional interface for wrapping controls into their corresponding accessor.
 *
 * @param <C> the actual control type to wrap.
 * @param <A> the actual control accessor type.
 */
@FunctionalInterface
public interface WrapFunction<C extends Control, A extends Accessor<?>> extends Function<Optional<C>, A> {

	// Nothing to declare here

}
