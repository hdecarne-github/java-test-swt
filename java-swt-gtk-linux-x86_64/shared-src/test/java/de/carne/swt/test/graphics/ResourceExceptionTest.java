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
package de.carne.swt.test.graphics;

import org.junit.Test;

import de.carne.swt.graphics.CreateResourceException;
import de.carne.swt.graphics.ResourceException;
import de.carne.swt.graphics.UnknownResourceException;

/**
 * Test {@linkplain ResourceException} class and derived classes.
 */
public class ResourceExceptionTest {

	/**
	 * Test {@linkplain ResourceException#ResourceException(String)}.
	 *
	 * @throws ResourceException always
	 */
	@Test(expected = ResourceException.class)
	public void testResourceException1() throws ResourceException {
		throw new ResourceException(getClass().getSimpleName());
	}

	/**
	 * Test {@linkplain ResourceException#ResourceException(Throwable)}.
	 *
	 * @throws ResourceException always
	 */
	@Test(expected = ResourceException.class)
	public void testResourceException2() throws ResourceException {
		throw new ResourceException(new NullPointerException());
	}

	/**
	 * Test {@linkplain ResourceException#ResourceException(String,Throwable)}.
	 *
	 * @throws ResourceException always
	 */
	@Test(expected = ResourceException.class)
	public void testResourceException3() throws ResourceException {
		throw new ResourceException(getClass().getSimpleName(), new NullPointerException());
	}

	/**
	 * Test {@linkplain UnknownResourceException#UnknownResourceException(String)}.
	 *
	 * @throws ResourceException always
	 */
	@Test(expected = UnknownResourceException.class)
	public void testUnknownResourceException1() throws ResourceException {
		throw new UnknownResourceException(getClass().getSimpleName());
	}

	/**
	 * Test {@linkplain UnknownResourceException#UnknownResourceException(Throwable)}.
	 *
	 * @throws ResourceException always
	 */
	@Test(expected = UnknownResourceException.class)
	public void testUnknownResourceException2() throws ResourceException {
		throw new UnknownResourceException(new NullPointerException());
	}

	/**
	 * Test {@linkplain UnknownResourceException#UnknownResourceException(String,Throwable)}.
	 *
	 * @throws ResourceException always
	 */
	@Test(expected = UnknownResourceException.class)
	public void testUnknownResourceException3() throws ResourceException {
		throw new UnknownResourceException(getClass().getSimpleName(), new NullPointerException());
	}

	/**
	 * Test {@linkplain CreateResourceException#CreateResourceException(String)}.
	 *
	 * @throws ResourceException always
	 */
	@Test(expected = CreateResourceException.class)
	public void testCreateResourceException1() throws ResourceException {
		throw new CreateResourceException(getClass().getSimpleName());
	}

	/**
	 * Test {@linkplain CreateResourceException#CreateResourceException(Throwable)}.
	 *
	 * @throws ResourceException always
	 */
	@Test(expected = CreateResourceException.class)
	public void testCreateResourceException2() throws ResourceException {
		throw new CreateResourceException(new NullPointerException());
	}

	/**
	 * Test {@linkplain CreateResourceException#CreateResourceException(String,Throwable)}.
	 *
	 * @throws ResourceException always
	 */
	@Test(expected = CreateResourceException.class)
	public void testCreateResourceException3() throws ResourceException {
		throw new CreateResourceException(getClass().getSimpleName(), new NullPointerException());
	}

}
