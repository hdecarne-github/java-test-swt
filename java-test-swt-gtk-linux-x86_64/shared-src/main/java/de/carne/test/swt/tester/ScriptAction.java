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
package de.carne.test.swt.tester;

import java.util.function.Consumer;
import java.util.function.Supplier;

import de.carne.boot.logging.Log;
import de.carne.test.swt.tester.accessor.Accessor;

abstract class ScriptAction {

	private static final Log LOG = new Log();

	private final String name;

	protected ScriptAction(String name) {
		this.name = name;
	}

	abstract void run(ScriptRunner scriptRunner) throws InterruptedException;

	protected Log log() {
		return LOG;
	}

	@Override
	public String toString() {
		return this.name;
	}

	static class AsyncDoScriptAction extends ScriptAction {

		private final Runnable doAction;

		AsyncDoScriptAction(String name, Runnable doAction) {
			super(name);
			this.doAction = doAction;
		}

		@Override
		void run(ScriptRunner scriptRunner) throws InterruptedException {
			scriptRunner.runNoWait(this.doAction);

			Timing.step();

			log().debug("{0} triggered", this);
		}

	}

	static class DoScriptAction extends ScriptAction {

		private final Runnable doAction;

		DoScriptAction(String name, Runnable doAction) {
			super(name);
			this.doAction = doAction;
		}

		@Override
		void run(ScriptRunner scriptRunner) throws InterruptedException {
			long start = System.nanoTime();

			scriptRunner.runWait(this.doAction);

			long elapsed = System.nanoTime() - start;

			Timing.step();

			log().debug("{0} executed (took {1} ms)", this, elapsed / 1000000);
		}

	}

	static class WaitScriptAction<T, A extends Accessor<T>> extends ScriptAction {

		private final Supplier<A> supplierAction;
		private final Consumer<A> consumerAction;
		private final int stepCountLimit;

		WaitScriptAction(String name, Supplier<A> supplierAction, Consumer<A> consumerAction, long timoutMillis) {
			super(name);
			this.supplierAction = supplierAction;
			this.consumerAction = consumerAction;
			this.stepCountLimit = Timing.stepCountLimit(timoutMillis);
		}

		@Override
		void run(ScriptRunner scriptRunner) throws InterruptedException {
			long start = System.nanoTime();

			Timing supply = new Timing(this.stepCountLimit);
			Runnable consumeSuppliedAccessor;

			while (true) {
				A suppliedAccessor = scriptRunner.runWait(this.supplierAction);

				if (suppliedAccessor.getOptional().isPresent()) {
					consumeSuppliedAccessor = () -> this.consumerAction.accept(suppliedAccessor);
					break;
				}
				supply.step("Timeout exceeded while waiting for accessor");
			}
			scriptRunner.runWait(consumeSuppliedAccessor);

			Timing.step();

			long elapsed = System.nanoTime() - start;

			log().debug("{0} executed (took {1} ms)", this, elapsed / 1000000);
		}

	}

}
