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
package de.carne.swt.graphics;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Resource;

import de.carne.swt.ResourceException;
import de.carne.util.function.FunctionException;

/**
 * Base class for pooling of SWT {@linkplain Resource} objects.
 *
 * @param <K> The key type used to identify the {@linkplain Resource} objects.
 * @param <R> The actual resource type.
 */
public abstract class ResourcePool<K, R extends Resource> {

	private final Device device;

	private final Map<K, R> resourceMap = new HashMap<>();

	/**
	 * Construct {@linkplain ResourcePool}.
	 *
	 * @param device The {@linkplain Device} sharing the {@linkplain Resource} objects.
	 */
	protected ResourcePool(Device device) {
		this.device = device;
	}

	/**
	 * Get the {@linkplain Device} this pool is associated with.
	 *
	 * @return The {@linkplain Device} this pool is associated with.
	 */
	public Device device() {
		return this.device;
	}

	/**
	 * Create a new {@linkplain Resource} object.
	 *
	 * @param key The key identifying the {@linkplain Resource} object to create.
	 * @return The created {@linkplain Resource} object.
	 * @throws ResourceException if the resource cannot be found or be created.
	 */
	protected abstract R create(K key) throws ResourceException;

	/**
	 * Get a specific {@linkplain Resource} object.
	 * <p>
	 * The requested {@linkplain Resource} object is created on first access. Any follow up requests will fulfilled by
	 * fetching the {@linkplain Resource} object from the pool.
	 *
	 * @param key The key identifying the {@linkplain Resource} object to retrieve.
	 * @return The requested {@linkplain Resource} object.
	 * @throws ResourceException if the resource cannot be found or be created.
	 */
	public synchronized R get(K key) throws ResourceException {
		R resource;

		try {
			resource = this.resourceMap.computeIfAbsent(key, this::safeCreate);
		} catch (FunctionException e) {
			throw e.rethrow(ResourceException.class);
		}
		return resource;
	}

	private R safeCreate(K key) {
		R resource;

		try {
			resource = create(key);
		} catch (ResourceException e) {
			throw new FunctionException(e);
		}
		return resource;
	}

	/**
	 * Dispose all {@linkplain Resource} object currently in the pool and clear the pool.
	 */
	public synchronized void disposeAll() {
		this.resourceMap.values().forEach(Resource::dispose);
		this.resourceMap.clear();
	}

}
