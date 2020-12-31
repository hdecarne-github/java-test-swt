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
package de.carne.test.swt.test.platform;

import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import de.carne.test.swt.platform.ProcessRunner;

/**
 * Test {@linkplain ProcessRunner} class.
 */
class ProcessRunnerTest {

	@Test
	void testRunCommand() {
		ProcessRunner runner = new ProcessRunner("nslookup", "github.com").withTimeout(5000).withCaptureOutput(true);
		ProcessRunner.Status status = runner.run();

		Assertions.assertEquals(ProcessRunner.Status.RUN_COMPLETED, status);
		Assertions.assertTrue(runner.statusException().isEmpty());
		Assertions.assertEquals(0, runner.exitValue());
		Assertions.assertTrue(runner.output().contains("github.com"));
	}

	@Test
	void testRunInvalidCommand() {
		ProcessRunner runner = new ProcessRunner("unlikely-command-name", "--help");
		ProcessRunner.Status status = runner.run();

		Assertions.assertEquals(ProcessRunner.Status.START_FAILED, status);
		Assertions.assertFalse(runner.statusException().isEmpty());
		Assertions.assertNotEquals(0, runner.exitValue());
		Assertions.assertThrows(IOException.class, runner::checkStatus);
	}

	@Test
	void testRunFailingCommand() {
		ProcessRunner runner = new ProcessRunner("ping", "unlikely.host.name");
		ProcessRunner.Status status = runner.run();

		Assertions.assertEquals(ProcessRunner.Status.RUN_COMPLETED, status);
		Assertions.assertTrue(runner.statusException().isEmpty());
		Assertions.assertNotEquals(0, runner.exitValue());
		Assertions.assertThrows(IOException.class, runner::checkStatus);
	}

	@Test
	void testRunTimedoutCommand() {
		ProcessRunner runner = new ProcessRunner("ping", "www.google.de").withTimeout(1);
		ProcessRunner.Status status = runner.run();

		Assertions.assertEquals(ProcessRunner.Status.RUN_TIMED_OUT, status);
		Assertions.assertTrue(runner.statusException().isEmpty());
		Assertions.assertNotEquals(0, runner.exitValue());
		Assertions.assertThrows(IOException.class, runner::checkStatus);
	}

}
