package com.example.batchSample.controllers;

import com.example.batchSample.controllers.dto.StepExecutionProgressInfo;
import com.example.batchSample.utils.StepExecutionHistory;
import org.springframework.batch.admin.service.JobService;
import org.springframework.batch.admin.service.NoSuchStepExecutionException;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author alok kulkarni
 */
@RestController
//@RequestMapping("/jobs/executions/{jobExecutionId}/steps")
public class JobStepExecutionProgressController {

	private final JobService jobService;

	/**
	 * Creates a {@code JobStepProgressInfoExecutionsController} that retrieves Job Step
	 * Progress Execution information from a the {@link JobService}
	 *
	 * @param jobService the service this controller will use for retrieving
	 *  job step progress execution information.
	 */
	@Autowired
	public JobStepExecutionProgressController(JobService jobService) {
		Assert.notNull(jobService, "repository must not be null");
		this.jobService = jobService;
	}

	/**
	 * Get the step execution progress for the given jobExecutions step.
	 *
	 * @param jobExecutionId  Id of the {@link JobExecution}, must not be null
	 * @param stepExecutionId Id of the {@link StepExecution}, must not be null
	 * @return {@link StepExecutionProgressInfo} that has the progress info on the given {@link StepExecution}.
	 * @throws NoSuchJobExecutionException  Thrown if the respective {@link JobExecution} does not exist
	 * @throws NoSuchStepExecutionException Thrown if the respective {@link StepExecution} does not exist
	 */
//	@RequestMapping(value = "/{stepExecutionId}/progress", method = RequestMethod.GET)
//	@ResponseStatus(HttpStatus.OK)
//	public StepExecutionProgressInfo progress(@PathVariable long jobExecutionId,
//                                              @PathVariable long stepExecutionId) throws NoSuchStepExecutionException, NoSuchJobExecutionException {
//
//	    Assert.notNull(jobExecutionId, "JobExecutionId cannot be null");
//	    Assert.notNull(stepExecutionId, "StepExecutionId cannot be null");
//
//		try {
//			StepExecution stepExecution = jobService.getStepExecution(jobExecutionId, stepExecutionId);
//			String stepName = stepExecution.getStepName();
//			if (stepName.contains(":partition")) {
//				// assume we want to compare all partitions
//				stepName = stepName.replaceAll("(:partition).*", "$1*");
//			}
//			String jobName = stepExecution.getJobExecution().getJobInstance().getJobName();
//			StepExecutionHistory stepExecutionHistory = computeHistory(jobName, stepName);
//			return new StepExecutionProgressInfo(stepExecution, stepExecutionHistory);
//		}
//		catch (NoSuchStepExecutionException e) {
//			throw new NoSuchStepExecutionException(String.valueOf(stepExecutionId));
//		}
//		catch (NoSuchJobExecutionException e) {
//			throw new NoSuchJobExecutionException(String.valueOf(jobExecutionId));
//		}
//	}

	/**
	 * Compute step execution history for the given jobs step.
	 *
	 * @param jobName the name of the job
	 * @param stepName the name of the step
	 * @return the step execution history for the given step
	 */
	private StepExecutionHistory computeHistory(String jobName, String stepName) {
		int total = jobService.countStepExecutionsForStep(jobName, stepName);
		StepExecutionHistory stepExecutionHistory = new StepExecutionHistory(stepName);
		for (int i = 0; i < total; i += 1000) {
			for (StepExecution stepExecution : jobService.listStepExecutionsForStep(jobName, stepName, i, 1000)) {
				stepExecutionHistory.append(stepExecution);
			}
		}
		return stepExecutionHistory;
	}
}
