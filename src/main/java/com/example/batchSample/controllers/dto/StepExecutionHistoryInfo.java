package com.example.batchSample.controllers.dto;

import com.example.batchSample.utils.StepExecutionHistory;
import org.springframework.batch.core.StepExecution;
import org.springframework.util.Assert;

/**
 * Created by alokkulkarni on 10/03/17.
 */
public class StepExecutionHistoryInfo {
    private StepExecution stepExecution;

    private StepExecutionHistory stepExecutionHistory;

    private double percentageComplete;

    private boolean finished;

    private double duration;

    public StepExecutionHistoryInfo() {
    }

    /**
     *
     * @param stepExecution Must not be null
     * @param stepExecutionHistory Must not be null
     */
    public StepExecutionHistoryInfo(StepExecution stepExecution, StepExecutionHistory stepExecutionHistory,
                                             double percentageComplete, boolean isFinished, double duration) {

        Assert.notNull(stepExecution, "stepExecution must not be null.");
        Assert.notNull(stepExecutionHistory, "stepExecution must not be null.");

        this.stepExecution = stepExecution;
        this.stepExecutionHistory = stepExecutionHistory;
        this.percentageComplete = percentageComplete;
        this.finished = isFinished;
        this.duration = duration;
    }

    /**
     * Getter for property 'stepExecutionHistory'.
     *
     * @return Value for property 'stepExecutionHistory'.
     */
    public StepExecutionHistory getStepExecutionHistory() {
        return stepExecutionHistory;
    }

    /**
     * Getter for property 'percentageComplete'.
     *
     * @return Value for property 'percentageComplete'.
     */
    public double getPercentageComplete() {
        return percentageComplete;
    }

    /**
     * Getter for property 'finished'.
     *
     * @return Value for property 'finished'.
     */
    public boolean isFinished() {
        return finished;
    }

    /**
     * Getter for property 'duration'.
     *
     * @return Value for property 'duration'.
     */
    public double getDuration() {
        return duration;
    }
}
