package com.example.batchSample.utils;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.util.Assert;

/**
 * Provides a common utility methods for job-related tasks.
 */
public final class JobUtils {

	/** Prevent instantiation. */
	private JobUtils() {
		throw new AssertionError();
	}

	/**
	 * Determine whether the provided {@link JobExecution} is restartable.
	 *
	 * @param jobExecution Must not be null and its {@link BatchStatus} must not be null either.
	 * @return Never returns null
	 */
	public static boolean isJobExecutionRestartable(JobExecution jobExecution) {
		Assert.notNull(jobExecution, "The provided jobExecution must not be null.");

		final BatchStatus batchStatus = jobExecution.getStatus();
		Assert.notNull(batchStatus, "The BatchStatus of the provided jobExecution must not be null.");

		return batchStatus.isGreaterThan(BatchStatus.STOPPING) && batchStatus.isLessThan(BatchStatus.ABANDONED);
	}

	/**
	 * Determine whether the provided {@link JobExecution} is abandonable.
	 *
	 * @param jobExecution Must not be null and its {@link BatchStatus} must not be null either.
	 * @return Never returns null
	 */
	public static boolean isJobExecutionAbandonable(JobExecution jobExecution) {
		Assert.notNull(jobExecution, "The provided jobExecution must not be null.");

		final BatchStatus batchStatus = jobExecution.getStatus();
		Assert.notNull(batchStatus, "The BatchStatus of the provided jobExecution must not be null.");

		return batchStatus.isGreaterThan(BatchStatus.STARTED) && batchStatus != BatchStatus.ABANDONED;
	}

	/**
	 * Determine whether the provided {@link JobExecution} is stoppable.
	 *
	 * @param jobExecution Must not be null and its {@link BatchStatus} must not be null either.
	 * @return Never returns null
	 */
	public static boolean isJobExecutionStoppable(JobExecution jobExecution) {
		Assert.notNull(jobExecution, "The provided jobExecution must not be null.");

		final BatchStatus batchStatus = jobExecution.getStatus();
		Assert.notNull(batchStatus, "The BatchStatus of the provided jobExecution must not be null.");

		return batchStatus.isLessThan(BatchStatus.STOPPING) && batchStatus != BatchStatus.COMPLETED;
	}
}
