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
package de.carne.test.swt.tester;

import java.util.Deque;
import java.util.LinkedList;
import java.util.function.IntSupplier;

import org.eclipse.jdt.annotation.Nullable;

/**
 * Class for mocking of standard dialog results during a test run.
 */
public final class IntDialogMock {

	private Deque<IntSupplier> resultQueue = new LinkedList<>();

	/**
	 * Adds a result to the result queue.
	 *
	 * @param result the result to add.
	 */
	public void offerResult(int result) {
		offerResult(() -> result);
	}

	/**
	 * Adds a result to the result queue.
	 *
	 * @param resultSupplier the result to add.
	 */
	public void offerResult(IntSupplier resultSupplier) {
		this.resultQueue.offer(resultSupplier);
	}

	@Nullable
	IntSupplier pollResult() {
		return this.resultQueue.poll();
	}

}
