/*
 * Copyright (c) 2017-2021 Holger de Carne and contributors, All Rights Reserved.
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
package de.carne.test.swt.tester;

import java.util.Deque;
import java.util.LinkedList;
import java.util.function.Supplier;

import org.eclipse.jdt.annotation.Nullable;

/**
 * Base class for mocking of standard dialog results during a test run.
 *
 * @param <T> the actual dialog result type.
 */
public abstract class DialogMock<T> {

	private Deque<@Nullable Supplier<T>> resultQueue = new LinkedList<>();

	/**
	 * Adds a result to the result queue.
	 *
	 * @param result the result to add.
	 */
	public void offerResult(@Nullable T result) {
		offerResult(result != null ? () -> result : null);
	}

	/**
	 * Adds a result to the result queue.
	 *
	 * @param resultSupplier the result to add.
	 */
	public void offerResult(@Nullable Supplier<T> resultSupplier) {
		this.resultQueue.offer(resultSupplier);
	}

	/**
	 * Gets the next result from the result queue.
	 *
	 * @return the next result from the result queue or {@code null} if the result queue is empty.
	 */
	@Nullable
	protected Supplier<T> pollResult() {
		return this.resultQueue.poll();
	}

}
