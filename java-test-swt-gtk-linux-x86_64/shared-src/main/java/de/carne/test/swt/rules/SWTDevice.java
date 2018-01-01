/*
 * Copyright (c) 2017-2018 Holger de Carne and contributors, All Rights Reserved.
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
package de.carne.test.swt.rules;

import java.util.function.Supplier;

import org.eclipse.swt.graphics.Device;
import org.junit.rules.ExternalResource;

import de.carne.util.Lazy;

/**
 * Rule class for maintaining a SWT device object during test execution.
 *
 * @param <T> The actual device type.
 */
public class SWTDevice<T extends Device> extends ExternalResource implements Supplier<T> {

	private final Lazy<T> deviceHolder;

	/**
	 * Construct {@linkplain SWTDevice}.
	 * 
	 * @param deviceSupplier The {@linkplain Supplier} to use for device creation.
	 */
	public SWTDevice(Supplier<T> deviceSupplier) {
		this.deviceHolder = new Lazy<>(deviceSupplier);
	}

	@Override
	protected void before() throws Throwable {
		this.deviceHolder.get();
	}

	@Override
	protected void after() {
		this.deviceHolder.get().dispose();
	}

	@Override
	public T get() {
		return this.deviceHolder.get();
	}

}
