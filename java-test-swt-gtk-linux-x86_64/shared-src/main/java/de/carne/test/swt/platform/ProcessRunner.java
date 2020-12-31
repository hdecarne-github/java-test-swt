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
package de.carne.test.swt.platform;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import de.carne.util.Exceptions;
import de.carne.util.Strings;
import de.carne.util.logging.Log;

/**
 * Utility class providing platform command execution support.
 */
public final class ProcessRunner {

	private static final Log LOG = new Log();

	/**
	 * The status of a {@linkplain ProcessRunner} instance.
	 */
	public enum Status {

		/**
		 * Command has not yet been run.
		 */
		NOT_YET_RUN,

		/**
		 * Command start failed.
		 */
		START_FAILED,

		/**
		 * Command run failed.
		 */
		RUN_FAILED,

		/**
		 * Command run timed out.
		 */
		RUN_TIMED_OUT,

		/**
		 * Command execution was interrupted.
		 */
		RUN_INTERRUPTED,

		/**
		 * Command run completed normally.
		 */
		RUN_COMPLETED

	}

	private ProcessBuilder builder;
	private long timeoutMillis = 1000;
	private boolean captureOutput = false;
	private Status status = Status.NOT_YET_RUN;
	private Optional<IOException> statusException = Optional.empty();
	private int exitValue = -1;
	private String output = "";

	/**
	 * Constructs a new {@linkplain ProcessRunner} instance.
	 *
	 * @param command the command line to run.
	 */
	public ProcessRunner(String... command) {
		this.builder = new ProcessBuilder(command).redirectErrorStream(true);
	}

	/**
	 * Sets the timeout (in milliseconds) to wait for process completion.
	 *
	 * @param timeout the timeout to use.
	 * @return the updated {@linkplain ProcessRunner} instance.
	 */
	public ProcessRunner withTimeout(long timeout) {
		this.timeoutMillis = timeout;
		return this;
	}

	/**
	 * Sets the capture-output option for process execution.
	 *
	 * @param capture whether to capture the running's process output ({@code true}) or not ({@code false}).
	 * @return the updated {@linkplain ProcessRunner} instance.
	 */
	public ProcessRunner withCaptureOutput(boolean capture) {
		this.captureOutput = capture;
		return this;
	}

	/**
	 * Runs the command and waits until either the process completes or an error occurs.
	 *
	 * @return the new status of this {@linkplain ProcessRunner} instance.
	 */
	public Status run() {
		LOG.debug("Running ''{0}''...", this);

		this.statusException = Optional.empty();
		this.exitValue = -1;
		this.output = "";

		Process process = null;

		try {
			process = this.builder.start();
		} catch (IOException e) {
			LOG.warning(e, "Process start failed");

			this.status = Status.START_FAILED;
			this.statusException = Optional.of(e);
		}
		if (process != null) {
			try {
				if (this.captureOutput) {
					this.output = new String(process.getInputStream().readAllBytes());
				}
				if (process.waitFor(this.timeoutMillis, TimeUnit.MILLISECONDS)) {
					this.exitValue = process.exitValue();
					this.status = Status.RUN_COMPLETED;
				} else {
					LOG.warning("Process timed out");

					this.status = Status.RUN_TIMED_OUT;
					process.destroyForcibly();
				}
			} catch (IOException e) {
				LOG.warning(e, "Process run failed");

				this.status = Status.RUN_FAILED;
				this.statusException = Optional.of(e);
			} catch (InterruptedException e) {
				LOG.warning(e, "Process run interrupted");

				Thread.currentThread().interrupt();
				this.status = Status.RUN_INTERRUPTED;
				this.statusException = Optional.of(new IOException(Exceptions.getMessage(e), e));
			}
		}
		return this.status;
	}

	/**
	 * Checks the status of the last {@linkplain #run()} invocation and throws an I/O exception in case an error
	 * occurred or if the process' exit status is not 0.
	 *
	 * @throws IOException if an error occurred during
	 */
	public void checkStatus() throws IOException {
		if (this.status != Status.RUN_COMPLETED || this.exitValue != 0) {
			if (this.statusException.isPresent()) {
				throw this.statusException.get();
			}
			throw new IOException(
					"Command '" + this + "' failed (status: " + this.status + "; exit value: " + this.exitValue + ")");
		}
	}

	/**
	 * Gets the optional {@linkplain IOException} caught during the last {@linkplain #run()} invocation.
	 *
	 * @return the optional {@linkplain IOException} caught during the last {@linkplain #run()} invocation.
	 */
	public Optional<IOException> statusException() {
		return this.statusException;
	}

	/**
	 * Gets the process exit value of the last {@linkplain #run()} invocation.
	 *
	 * @return the process exit value of the last {@linkplain #run()} invocation.
	 */
	public int exitValue() {
		return this.exitValue;
	}

	/**
	 * Gets the process output captured during the last {@linkplain #run()} invocation.
	 *
	 * @return the process output captured during the last {@linkplain #run()} invocation.
	 * @see #withCaptureOutput(boolean)
	 */
	public String output() {
		return this.output;
	}

	@Override
	public String toString() {
		return Strings.join(this.builder.command(), " ");
	}

}
