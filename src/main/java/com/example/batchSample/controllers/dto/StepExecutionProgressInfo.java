package com.example.batchSample.controllers.dto;

import com.example.batchSample.utils.StepExecutionHistory;
import org.springframework.batch.core.StepExecution;
import org.springframework.util.Assert;

/**
 * Created by alokkulkarni on 11/03/17.
 */
public class StepExecutionProgressInfo {

    private StepExecution stepExecution;

    private StepExecutionHistory stepExecutionHistory;

    private double percentageComplete;

    private boolean finished;

    private double duration;

    public StepExecutionProgressInfo() {
    }

    /**
     *
     * @param stepExecution Must not be null
     * @param stepExecutionHistory Must not be null
     */
    public StepExecutionProgressInfo(StepExecution stepExecution, StepExecutionHistory stepExecutionHistory,
                                             double percentageComplete, boolean isFinished, double duration) {

        Assert.notNull(stepExecution, "stepExecution must not be null.");
        Assert.notNull(stepExecutionHistory, "stepExecution must not be null.");

        this.stepExecution = stepExecution;
        this.stepExecutionHistory = stepExecutionHistory;
        this.percentageComplete = percentageComplete;
        this.finished = isFinished;
        this.duration = duration;
    }

    public double getPercentageComplete() {
        return percentageComplete;
    }

    public boolean getFinished() {
        return finished;
    }

    public double getDuration() {
        return duration;
    }

    public StepExecution getStepExecution() {
        return stepExecution;
    }

    public StepExecutionHistory getStepExecutionHistory() {
        return stepExecutionHistory;
    }
}
