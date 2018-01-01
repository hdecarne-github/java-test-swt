/*
 * Copyright (c) 2007-2018 Holger de Carne and contributors, All Rights Reserved.
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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;

/**
 * Resource pool to manage {@linkplain Image} resources identified by their resource {@linkplain URI}s.
 */
public final class ImageResourcePool extends ResourcePool<URI, Image> {

	/**
	 * Construct {@linkplain ImageResourcePool}.
	 *
	 * @param device The {@linkplain Device} to create the {@linkplain Image} objects for.
	 */
	public ImageResourcePool(Device device) {
		super(device);
	}

	@Override
	protected Image create(URI key) throws ResourceException {
		URL keyUrl;

		try {
			keyUrl = key.toURL();
		} catch (MalformedURLException e) {
			throw new CreateResourceException("Failed determine resource URL from URI: " + key, e);
		}

		Image image;

		try (InputStream imageStream = keyUrl.openStream()) {
			image = new Image(device(), imageStream);
		} catch (IOException e) {
			throw new CreateResourceException("Failed to open resource from URL: " + keyUrl, e);
		} catch (SWTException e) {
			throw new CreateResourceException("Failed to create resource from URL: " + keyUrl, e);
		}
		return image;
	}

	/**
	 * Get a {@linkplain Image} resource.
	 *
	 * @param clazz The {@linkplain Class} to use to load the resource.
	 * @param name The name of the resource to load.
	 * @return The {@linkplain Image} resource.
	 * @throws ResourceException if the resource cannot be found or be created.
	 * @see Class#getResource(String)
	 */
	public Image get(Class<?> clazz, String name) throws ResourceException {
		URL imageUrl = clazz.getResource(name);

		if (imageUrl == null) {
			throw new UnknownResourceException("Resource not found: " + clazz.getName() + ":" + name);
		}

		URI imageUri;

		try {
			imageUri = imageUrl.toURI();
		} catch (URISyntaxException e) {
			throw new CreateResourceException("Failed determine resource URI from URL: " + imageUrl, e);
		}
		return get(imageUri);
	}

	/**
	 * Get multiple {@linkplain Image} resources.
	 *
	 * @param clazz The {@linkplain Class} to use to load the resources.
	 * @param names The names of the resources to load.
	 * @return The {@linkplain Image} resources.
	 * @throws ResourceException if one of resources cannot be found or be created.
	 * @see #get(Class, String)
	 */
	public Image[] getAll(Class<?> clazz, String... names) throws ResourceException {
		int nameCount = names.length;
		Image[] images = new Image[nameCount];

		for (int nameIndex = 0; nameIndex < nameCount; nameIndex++) {
			images[nameIndex] = get(clazz, names[nameIndex]);
		}
		return images;
	}

}
