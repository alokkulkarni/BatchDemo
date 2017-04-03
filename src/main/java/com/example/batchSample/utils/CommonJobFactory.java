package com.example.batchSample.utils;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.JobFactory;

/**
 * Created by alokkulkarni on 25/03/17.
 */
public class CommonJobFactory implements JobFactory {

    private final Job job;
    private final String jobName;

    public CommonJobFactory(Job job, String jobName) {
        this.job = job;
        this.jobName = jobName;
    }


    @Override
    public Job createJob() {
        return job;
    }

    @Override
    public String getJobName() {
        return jobName;
    }
}
