package com.example.batchSample.controllers.dto;

import com.example.batchSample.utils.StepExecutionHistory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by alokkulkarni on 10/03/17.
 */
public class StepExecutionInfo {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    private SimpleDateFormat durationFormat = new SimpleDateFormat("HH:mm:ss");

    private Long id;

    private Long jobExecutionId;

    private String jobName;

    private String name;

    private String startDate = "-";

    private String startTime = "-";

    private String duration = "-";

    private String lastUpdated = "-";

    private StepExecution stepExecution;

    private long durationMillis;

    private StepExecutionHistory stepExecutionHistory;

    private List<Throwable> failureExceptions = new CopyOnWriteArrayList<Throwable>();

    public StepExecutionInfo(String jobName, Long jobExecutionId, String name, TimeZone timeZone) {
        this.jobName = jobName;
        this.jobExecutionId = jobExecutionId;
        this.name = name;
        this.stepExecution = new StepExecution(name, new JobExecution(jobExecutionId));
    }

    public StepExecutionInfo(StepExecution stepExecution, TimeZone timeZone) {

        this.stepExecution = stepExecution;
        this.id = stepExecution.getId();
        this.name = stepExecution.getStepName();
        this.jobName = stepExecution.getJobExecution() == null
                || stepExecution.getJobExecution().getJobInstance() == null ? "?" : stepExecution.getJobExecution()
                .getJobInstance().getJobName();
        this.jobExecutionId = stepExecution.getJobExecutionId();
        // Duration is always in GMT
        durationFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        timeFormat.setTimeZone(timeZone);
        dateFormat.setTimeZone(timeZone);
        if (stepExecution.getStartTime() != null) {
            this.startDate = dateFormat.format(stepExecution.getStartTime());
            this.startTime = timeFormat.format(stepExecution.getStartTime());
            this.lastUpdated = stepExecution.getLastUpdated() != null ? dateFormat.format(stepExecution.getLastUpdated()) : "-";
            Date endTime = stepExecution.getEndTime() != null ? stepExecution.getEndTime() : new Date();
            this.durationMillis = endTime.getTime() - stepExecution.getStartTime().getTime();
            this.duration = durationFormat.format(new Date(durationMillis));
        }
        this.failureExceptions = stepExecution.getFailureExceptions();
        this.stepExecutionHistory = new StepExecutionHistory(this.stepExecution.getStepName());
    }

    public Long getId() {
        return id;
    }

    public Long getJobExecutionId() {
        return jobExecutionId;
    }

    public String getName() {
        return name;
    }

    public String getJobName() {
        return jobName;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getDuration() {
        return duration;
    }

    public long getDurationMillis() {
        return durationMillis;
    }

    public String getStatus() {
        if (id!=null) {
            return stepExecution.getStatus().toString();
        }
        return "NONE";
    }

    public String getExitCode() {
        if (id!=null) {
            return stepExecution.getExitStatus().getExitCode();
        }
        return "NONE";
    }


    /**
     * Getter for property 'lastUpdated'.
     *
     * @return Value for property 'lastUpdated'.
     */
    public String getLastUpdated() {
        return lastUpdated;
    }

    /**
     * Getter for property 'failureExceptions'.
     *
     * @return Value for property 'failureExceptions'.
     */
    public List<Throwable> getFailureExceptions() {
        return failureExceptions;
    }

    /**
     * Getter for property 'stepExecutionHistory'.
     *
     * @return Value for property 'stepExecutionHistory'.
     */
    public StepExecutionHistory getStepExecutionHistory() {
        return stepExecutionHistory;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StepExecutionInfo)) return false;

        StepExecutionInfo that = (StepExecutionInfo) o;

        if (getDurationMillis() != that.getDurationMillis()) return false;
        if (dateFormat != null ? !dateFormat.equals(that.dateFormat) : that.dateFormat != null) return false;
        if (timeFormat != null ? !timeFormat.equals(that.timeFormat) : that.timeFormat != null) return false;
        if (durationFormat != null ? !durationFormat.equals(that.durationFormat) : that.durationFormat != null)
            return false;
        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getJobExecutionId() != null ? !getJobExecutionId().equals(that.getJobExecutionId()) : that.getJobExecutionId() != null)
            return false;
        if (getJobName() != null ? !getJobName().equals(that.getJobName()) : that.getJobName() != null) return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        if (getStartDate() != null ? !getStartDate().equals(that.getStartDate()) : that.getStartDate() != null)
            return false;
        if (getStartTime() != null ? !getStartTime().equals(that.getStartTime()) : that.getStartTime() != null)
            return false;
        if (getDuration() != null ? !getDuration().equals(that.getDuration()) : that.getDuration() != null)
            return false;
        return stepExecution != null ? stepExecution.equals(that.stepExecution) : that.stepExecution == null;
    }

    @Override
    public int hashCode() {
        int result = dateFormat != null ? dateFormat.hashCode() : 0;
        result = 31 * result + (timeFormat != null ? timeFormat.hashCode() : 0);
        result = 31 * result + (durationFormat != null ? durationFormat.hashCode() : 0);
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        result = 31 * result + (getJobExecutionId() != null ? getJobExecutionId().hashCode() : 0);
        result = 31 * result + (getJobName() != null ? getJobName().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getStartDate() != null ? getStartDate().hashCode() : 0);
        result = 31 * result + (getStartTime() != null ? getStartTime().hashCode() : 0);
        result = 31 * result + (getDuration() != null ? getDuration().hashCode() : 0);
        result = 31 * result + (stepExecution != null ? stepExecution.hashCode() : 0);
        result = 31 * result + (int) (getDurationMillis() ^ (getDurationMillis() >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "StepExecutionInfo{" +
                "dateFormat=" + dateFormat +
                ", timeFormat=" + timeFormat +
                ", durationFormat=" + durationFormat +
                ", id=" + id +
                ", jobExecutionId=" + jobExecutionId +
                ", jobName='" + jobName + '\'' +
                ", name='" + name + '\'' +
                ", startDate='" + startDate + '\'' +
                ", startTime='" + startTime + '\'' +
                ", duration='" + duration + '\'' +
                ", stepExecution=" + stepExecution +
                ", durationMillis=" + durationMillis +
                '}';
    }
}
