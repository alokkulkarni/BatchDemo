package com.example.batchSample.controllers;

import org.springframework.batch.admin.service.JobService;
import org.springframework.batch.admin.web.JobInfo;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alokkulkarni on 09/03/17.
 */
@RestController
@RequestMapping(value = "/jobs")
public class JobController {

    private JobService jobservice;


    /**
     * Instantiates a new Job controller.
     *
     * @param jobservice the jobservice
     */
    @Autowired
    public JobController(JobService jobservice) {
        Assert.notNull(jobservice, "JobService cannot be null");
        this.jobservice = jobservice;
    }

    /**
     * Gets all jobs.
     *
     * @return the all jobs
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<JobInfo>> getAllJobs()
    {
        int total = jobservice.countJobs();
        if (total == 0) {
            return ResponseEntity.notFound().build();
        }

        List<JobInfo> jobInfoList = new ArrayList<>();
        int startJob =  0;
        int pageSize =  20;

        jobservice.listJobs(startJob, pageSize)
                .forEach(jobName -> {
                    try {
                        jobInfoList.add(new JobInfo(jobName, jobservice.countJobExecutionsForJob(jobName), null, jobservice.isLaunchable(jobName), jobservice.isIncrementable(jobName)));
                    } catch (NoSuchJobException e) {
                        e.printStackTrace();
                    }
                });
        return new ResponseEntity<>(jobInfoList, HttpStatus.OK);
    }

    /**
     * Stop all jobs int.
     *
     * @return the int
     */
    @PostMapping(value = "/stopAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public int stopAllJobs() {

        return jobservice.stopAll();
    }

    /**
     * Stop job for job execution id job execution.
     *
     * @param jobExecutionId the job execution id
     * @return the job execution
     * @throws NoSuchJobExecutionException     the no such job execution exception
     * @throws JobExecutionNotRunningException the job execution not running exception
     */
    @PostMapping(value = "/{jobExecutionId}/stop", produces = MediaType.APPLICATION_JSON_VALUE)
    public JobExecution stopJobForJobExecutionId(@PathVariable("jobExecutionId") Long jobExecutionId) throws NoSuchJobExecutionException, JobExecutionNotRunningException {

        Assert.notNull(jobExecutionId, "JobExecutionId cannot be null");

        return jobservice.stop(jobExecutionId);
    }

    /**
     * Restart job job execution.
     *
     * @param executionId the execution id
     * @return the job execution
     * @throws JobInstanceAlreadyCompleteException the job instance already complete exception
     * @throws NoSuchJobException                  the no such job exception
     * @throws JobExecutionAlreadyRunningException the job execution already running exception
     * @throws JobParametersInvalidException       the job parameters invalid exception
     * @throws JobRestartException                 the job restart exception
     * @throws NoSuchJobExecutionException         the no such job execution exception
     */
    @PostMapping(value = "/{executionId}/restart", produces = MediaType.APPLICATION_JSON_VALUE)
    public JobExecution restartJob(@PathVariable("executionId") Long executionId) throws JobInstanceAlreadyCompleteException, NoSuchJobException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException, NoSuchJobExecutionException {

        Assert.notNull(executionId, "JobExecutionId cannot be Null");

        return jobservice.restart(executionId);
    }

    /**
     * Launch.
     *
     * @param jobName the job name
     * @throws NoSuchJobException                  the no such job exception
     * @throws JobParametersInvalidException       the job parameters invalid exception
     * @throws JobExecutionAlreadyRunningException the job execution already running exception
     * @throws JobRestartException                 the job restart exception
     * @throws JobInstanceAlreadyCompleteException the job instance already complete exception
     */
    @PostMapping(value = "/{jobName}/launch", produces = MediaType.APPLICATION_JSON_VALUE)
    public void launch(@PathVariable("jobName") String jobName) throws NoSuchJobException, JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {

        JobParameters jobParameters = jobservice.getLastJobParameters(jobName);

        jobservice.launch(jobName, jobParameters);
    }
}
