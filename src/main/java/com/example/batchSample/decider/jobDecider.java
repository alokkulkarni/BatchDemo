package com.example.batchSample.decider;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.stereotype.Component;

import javax.batch.runtime.BatchStatus;

/**
 * Created by alokkulkarni on 30/09/16.
 */
@Component
public class jobDecider implements JobExecutionDecider {
    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {

        if (jobExecution.getJobConfigurationName().equalsIgnoreCase("Job") && jobExecution.getStatus().getBatchStatus().equals(BatchStatus.COMPLETED)) {

            return new FlowExecutionStatus("STARTNEXT");

        } else  {
            return FlowExecutionStatus.FAILED;
        }
    }
}
