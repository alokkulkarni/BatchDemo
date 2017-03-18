package com.example.batchSample.controllers;

import com.example.batchSample.controllers.dto.JobExecutionInfo;
import org.springframework.batch.admin.service.JobService;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

/**
 * Created by alokkulkarni on 10/03/17.
 */
@RestController
@RequestMapping(value = "/jobs")
public class JobInstanceController {

    private JobService jobService;


    public JobInstanceController(JobService jobService) {
        Assert.notNull(jobService, "JobService cannot be null");
        this.jobService = jobService;
    }


    @GetMapping(value = "/{jobName}/jobInstance/{jobInstanceId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<JobExecutionInfo>> getJobExecutionInfoFromInstanceId(@PathVariable("jobName") String jobName, @PathVariable("jobInstanceId") Long jobInstanceId) throws NoSuchJobException {

        Assert.notNull(jobInstanceId, "JobInstanceId cannot be null");
        Assert.hasLength(jobName, "JobName is required");

        List<JobExecutionInfo> jobExecutionInfoList = jobService.getJobExecutionsForJobInstance(jobName, jobInstanceId)
                                                                            .stream()
                                                                            .map(jobExecution -> new JobExecutionInfo(jobExecution, TimeZone.getDefault()))
                                                                            .collect(Collectors.toList());

        return new ResponseEntity<>(jobExecutionInfoList, HttpStatus.OK);


    }


}
