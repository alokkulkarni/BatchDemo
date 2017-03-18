package com.example.batchSample.utils;

import org.springframework.batch.admin.history.CumulativeHistory;
import org.springframework.batch.core.StepExecution;

import java.util.Date;

/**
 * Created by alokkulkarni on 10/03/17.
 */
public class StepExecutionHistory {
    private final String stepName;
    private int count = 0;
    private CumulativeHistory commitCount = new CumulativeHistory();
    private CumulativeHistory rollbackCount = new CumulativeHistory();
    private CumulativeHistory readCount = new CumulativeHistory();
    private CumulativeHistory writeCount = new CumulativeHistory();
    private CumulativeHistory filterCount = new CumulativeHistory();
    private CumulativeHistory readSkipCount = new CumulativeHistory();
    private CumulativeHistory writeSkipCount = new CumulativeHistory();
    private CumulativeHistory processSkipCount = new CumulativeHistory();
    private CumulativeHistory duration = new CumulativeHistory();
    private CumulativeHistory durationPerRead = new CumulativeHistory();

    public StepExecutionHistory(String stepName) {
        this.stepName = stepName;
    }

    public void append(StepExecution stepExecution) {
        if (stepExecution.getEndTime()==null) {
            // ignore unfinished executions
            return;
        }
        Date startTime = stepExecution.getStartTime();
        Date endTime = stepExecution.getEndTime();
        long time = endTime.getTime()-startTime.getTime();
        duration.append(time);
        if (stepExecution.getReadCount()>0) {
            durationPerRead.append(time/stepExecution.getReadCount());
        }
        count++;
        commitCount.append(stepExecution.getCommitCount());
        rollbackCount.append(stepExecution.getRollbackCount());
        readCount.append(stepExecution.getReadCount());
        writeCount.append(stepExecution.getWriteCount());
        filterCount.append(stepExecution.getFilterCount());
        readSkipCount.append(stepExecution.getReadSkipCount());
        writeSkipCount.append(stepExecution.getWriteSkipCount());
        processSkipCount.append(stepExecution.getProcessSkipCount());
    }

    public String getStepName() {
        return stepName;
    }

    public int getCount() {
        return count;
    }

    public CumulativeHistory getCommitCount() {
        return commitCount;
    }

    public CumulativeHistory getRollbackCount() {
        return rollbackCount;
    }

    public CumulativeHistory getReadCount() {
        return readCount;
    }

    public CumulativeHistory getWriteCount() {
        return writeCount;
    }

    public CumulativeHistory getFilterCount() {
        return filterCount;
    }

    public CumulativeHistory getReadSkipCount() {
        return readSkipCount;
    }

    public CumulativeHistory getWriteSkipCount() {
        return writeSkipCount;
    }

    public CumulativeHistory getProcessSkipCount() {
        return processSkipCount;
    }

    public CumulativeHistory getDuration() {
        return duration;
    }

    public CumulativeHistory getDurationPerRead() {
        return durationPerRead;
    }
}
