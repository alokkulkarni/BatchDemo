package com.example.batchSample.controllers;

import com.example.batchSample.controllers.dto.JobExecutionInfo;
import com.example.batchSample.controllers.dto.StepExecutionInfo;
import org.springframework.batch.admin.service.JobService;
import org.springframework.batch.admin.service.NoSuchStepExecutionException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

/**
 * Created by alokkulkarni on 10/03/17.
 */
@RestController
@RequestMapping(value = "/jobs/executions")
public class JobExecutionController {

    private JobService jobservice;


    /**
     * Instantiates a new Job controller.
     *
     * @param jobservice the jobservice
     */
    @Autowired
    public JobExecutionController(JobService jobservice) {
        Assert.notNull(jobservice, "JobService cannot be null");
        this.jobservice = jobservice;
    }

    /**
     * Gets list of job executions.
     *
     * @return the list of job executions
     */
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<JobExecutionInfo>> getListOfJobExecutions() {

        int total = jobservice.countJobExecutions();
        if (total == 0) {
            return ResponseEntity.notFound().build();
        }

        List<JobExecutionInfo> jobExecutionInfoList = new ArrayList<>();

        int startJob =  0;
        int pageSize =  20;

        jobservice.listJobExecutions(startJob, pageSize)
                .forEach(jobExecution -> jobExecutionInfoList.add(new JobExecutionInfo(jobExecution, TimeZone.getDefault())));

        return new ResponseEntity<>(jobExecutionInfoList, HttpStatus.OK);
    }

    /**
     * Gets job execution.
     *
     * @param jobExecutionId the job execution id
     * @return the job execution
     * @throws NoSuchJobExecutionException the no such job execution exception
     */
    @GetMapping(value = "/{executionId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JobExecutionInfo> getJobExecutionForExecutionId(@PathVariable("executionId") Long jobExecutionId) throws NoSuchJobExecutionException {

        Assert.notNull(jobExecutionId, "JobExecution Id cannot be Null");

        JobExecutionInfo jobExecutionInfo = new JobExecutionInfo(jobservice.getJobExecution(jobExecutionId), TimeZone.getDefault());

        return new ResponseEntity<>(jobExecutionInfo, HttpStatus.OK);
    }

    /**
     * Gets all step executions.
     *
     * @param jobExecutionId the job execution id
     * @return the all step executions
     * @throws NoSuchJobExecutionException the no such job execution exception
     */
    @GetMapping(value = "/{executionId}/steps", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StepExecutionInfo>> getAllStepExecutionsForExecutionId(@PathVariable("executionId") Long jobExecutionId) throws NoSuchJobExecutionException {

        Assert.notNull(jobExecutionId, "JobExecutionId cannot be null");

        List<StepExecutionInfo> stepExecutionInfoArrayList= new ArrayList<>();
        jobservice.getStepExecutions(jobExecutionId)
                .forEach(stepExecution -> stepExecutionInfoArrayList.add(new StepExecutionInfo(stepExecution, TimeZone.getDefault())));

        return new ResponseEntity<>(stepExecutionInfoArrayList, HttpStatus.OK);
    }


    /**
     * Gets step details.
     *
     * @param executionId the execution id
     * @param stepid      the stepid
     * @return the step details
     * @throws NoSuchStepExecutionException the no such step execution exception
     * @throws NoSuchJobExecutionException  the no such job execution exception
     */
    @GetMapping(value = "/{executionId}/steps/{stepId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StepExecutionInfo> getStepDetailsForExecutionIdAndStepId(@PathVariable("executionId") Long executionId, @PathVariable("stepId") Long stepid) throws NoSuchStepExecutionException, NoSuchJobExecutionException {

        Assert.notNull(executionId, "JobExecutionId cannot be null");
        Assert.notNull(stepid, "StepId cannot be null");

        StepExecutionInfo stepExecutionInfo = new StepExecutionInfo(jobservice.getStepExecution(executionId, stepid), TimeZone.getDefault());

        return new ResponseEntity<>(stepExecutionInfo, HttpStatus.OK);
    }

    /**
     * Gets list of job executions for job name.
     *
     * @param jobName  the job name
     * @param startJob the start job
     * @param pageSize the page size
     * @return the list of job executions for job name
     * @throws NoSuchJobException the no such job exception
     */
    @PostMapping(value ="/{jobName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<JobExecutionInfo>> getListOfJobExecutionsForJobName(@PathVariable("jobName") String jobName,
                                                                                   @RequestParam(defaultValue = "0") int startJob,
                                                                                   @RequestParam(defaultValue = "20") int pageSize) throws NoSuchJobException {

        Assert.hasLength(jobName, "Job Name cannot be blank");

        List<JobExecutionInfo> jobExecutionInfoList = jobservice.listJobExecutionsForJob(jobName, startJob, pageSize)
                .stream()
                .map(jobExecution -> new JobExecutionInfo(jobExecution, TimeZone.getDefault()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(jobExecutionInfoList, HttpStatus.OK);
    }


    /**
     * Gets step executions for job name and step name.
     *
     * @param jobName  the job name
     * @param stepName the step name
     * @param startJob the start job
     * @param pageSize the page size
     * @return the step executions for job name and step name
     * @throws NoSuchStepExecutionException the no such step execution exception
     */
    @PostMapping(value = "/{jobName}/steps/{stepName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StepExecutionInfo>> getStepExecutionsForJobNameAndStepName(@PathVariable("jobName") String jobName,
                                                                                        @PathVariable("stepName") String stepName,
                                                                                        @RequestParam(defaultValue = "0") int startJob,
                                                                                        @RequestParam(defaultValue = "20") int pageSize) throws NoSuchStepExecutionException {

        Assert.hasLength(jobName, "JobName cannot be blank");
        Assert.hasLength(stepName, "Step Name cannot be blank");


        List<StepExecutionInfo> stepExecutionInfoList = jobservice.listStepExecutionsForStep(jobName, stepName, startJob, pageSize)
                .stream()
                .map(stepExecution -> new StepExecutionInfo(stepExecution, TimeZone.getDefault()))
                .collect(Collectors.toList());

        return new ResponseEntity<>(stepExecutionInfoList, HttpStatus.OK);
    }

}
