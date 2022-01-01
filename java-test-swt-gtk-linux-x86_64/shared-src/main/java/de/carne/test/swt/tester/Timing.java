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

import org.junit.jupiter.api.Assertions;

import de.carne.util.SystemProperties;

/**
 * Helper class used to track execution steps and detect timeout situations.
 */
final class Timing {

	private static final String PROPERTY_TEST_TIMEOUT = Timing.class.getPackage().getName() + ".TEST_TIMEOUT";

	public static final long TEST_TIMEOUT = SystemProperties.longValue(PROPERTY_TEST_TIMEOUT, 5 * 60 * 1000l);

	private static final String PROPERTY_STEP_TIMEOUT = Timing.class.getPackage().getName() + ".STEP_TIMEOUT";

	public static final long STEP_TIMEOUT = SystemProperties.longValue(PROPERTY_STEP_TIMEOUT, 500);

	private static final String PROPERTY_STEP_COUNT_LIMIT = Timing.class.getPackage().getName() + ".STEP_COUNT_LIMIT";

	public static final int STEP_COUNT_LIMIT = SystemProperties.intValue(PROPERTY_STEP_COUNT_LIMIT, 10);

	private int stepCountLimit;
	private final Synchronizer synchronizer;
	private int stepCount;

	public Timing() {
		this(STEP_COUNT_LIMIT, Thread::sleep);
	}

	public Timing(int stepCountLimit) {
		this(stepCountLimit, Thread::sleep);
	}

	public Timing(Synchronizer synchronizer) {
		this(STEP_COUNT_LIMIT, synchronizer);
	}

	public Timing(int stepCountLimit, Synchronizer synchronizer) {
		this.stepCountLimit = stepCountLimit;
		this.synchronizer = synchronizer;
		this.stepCount = 0;
	}

	public void step(String timeoutMessage) throws InterruptedException {
		if (this.stepCount >= this.stepCountLimit) {
			Assertions.fail(timeoutMessage);
		}
		this.synchronizer.sync(STEP_TIMEOUT);
		this.stepCount++;
	}

	public static int stepCountLimit(long timoutMillis) {
		return (int) Math.floorDiv(timoutMillis + Timing.STEP_TIMEOUT - 1, Timing.STEP_TIMEOUT);
	}

	@SuppressWarnings("squid:S2925")
	public static void step() throws InterruptedException {
		Thread.sleep(STEP_TIMEOUT);
	}

}
