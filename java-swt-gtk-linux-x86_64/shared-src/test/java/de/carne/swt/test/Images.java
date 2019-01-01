/*
 * Copyright (c) 2007-2019 Holger de Carne and contributors, All Rights Reserved.
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
package de.carne.swt.test;

import java.net.URL;

import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Image;

import de.carne.swt.graphics.ResourceException;
import de.carne.swt.graphics.ResourceTracker;

/**
 * Test image resources.
 */
public final class Images {

	private Images() {
		// Prevent instantiation
	}

	/**
	 * image_a_16.png
	 */
	public static final String IMAGE_A_16 = "image_a_16.png";
	/**
	 * image_a_32.png
	 */
	public static final String IMAGE_A_32 = "image_a_32.png";
	/**
	 * image_a_48.png
	 */
	public static final String IMAGE_A_48 = "image_a_48.png";
	/**
	 * image_a_128.png
	 */
	public static final String IMAGE_A_128 = "image_a_128.png";
	/**
	 * image_a_{16,32,48,128}.png
	 */
	public static final String[] IMAGES_A = new String[] { IMAGE_A_16, IMAGE_A_32, IMAGE_A_48, IMAGE_A_128 };

	/**
	 * Gets the {@linkplain Image} resource for the given name.
	 *
	 * @param device the {@linkplain Device} to use for the image.
	 * @param name the name of the image.
	 * @return the requested {@linkplain Image} resource.
	 * @throws ResourceException if the {@linkplain Image} resource is not available.
	 */
	public static Image getImage(Device device, String name) throws ResourceException {
		URL imageUrl = Images.class.getResource(name);

		if (imageUrl == null) {
			throw new ResourceException("Cannot find image: " + name);
		}
		return ResourceTracker.forDevice(device).getImage(imageUrl);
	}

	/**
	 * Gets the {@linkplain Image} resources for the given name set.
	 *
	 * @param device the {@linkplain Device} to use for the images.
	 * @param names the names of the images.
	 * @return the requested {@linkplain Image} resources.
	 * @throws ResourceException if on ore more of the {@linkplain Image} resources are not available.
	 */
	public static Image[] getImages(Device device, String[] names) throws ResourceException {
		ResourceTracker resourceTracker = ResourceTracker.forDevice(device);
		Image[] images = new Image[names.length];

		for (int nameIndex = 0; nameIndex < names.length; nameIndex++) {
			String name = names[nameIndex];
			URL imageUrl = Images.class.getResource(name);

			if (imageUrl == null) {
				throw new ResourceException("Cannot find image: " + name);
			}
			images[nameIndex] = resourceTracker.getImage(imageUrl);
		}
		return images;
	}

}
