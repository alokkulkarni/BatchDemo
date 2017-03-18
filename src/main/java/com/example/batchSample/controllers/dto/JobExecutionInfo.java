package com.example.batchSample.controllers.dto;

import org.springframework.batch.admin.web.JobParametersExtractor;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.converter.DefaultJobParametersConverter;
import org.springframework.batch.core.converter.JobParametersConverter;
import org.springframework.batch.item.ExecutionContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by alokkulkarni on 10/03/17.
 */
public class JobExecutionInfo {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

    private SimpleDateFormat durationFormat = new SimpleDateFormat("HH:mm:ss");

    private Long id;

    private int stepExecutionCount;

    private Long jobId;

    private String jobName;

    private String startDate = "";

    private String startTime = "";

    private String duration = "";

    private String lastUpdated = "";

    private JobExecution jobExecution;

    private Properties jobParameters;

    private String jobParametersString;

    private boolean restartable = false;

    private boolean abandonable = false;

    private boolean stoppable = false;

    private long jobInstanceId;

    private ExitStatus exitStatus;

    private String status;

    private ExecutionContext executionContext;

    private JobParametersConverter converter = new DefaultJobParametersConverter();

    private List<Throwable> failureExceptions = new CopyOnWriteArrayList<Throwable>();

    private final TimeZone timeZone;

    public JobExecutionInfo(JobExecution jobExecution, TimeZone timeZone) {

        this.jobExecution = jobExecution;
        this.timeZone = timeZone;
        this.id = jobExecution.getId();
        this.jobId = jobExecution.getJobId();
        this.stepExecutionCount = jobExecution.getStepExecutions().size();
        this.jobParameters = converter.getProperties(jobExecution.getJobParameters());
        this.jobParametersString = new JobParametersExtractor().fromJobParameters(jobExecution.getJobParameters());
        this.exitStatus = jobExecution.getExitStatus();
        this.status = jobExecution.getStatus().name();
        this.executionContext = jobExecution.getExecutionContext();
        this.failureExceptions = jobExecution.getFailureExceptions();

        JobInstance jobInstance = jobExecution.getJobInstance();
        if (jobInstance != null) {
            this.jobInstanceId = jobInstance.getInstanceId();
            this.jobName = jobInstance.getJobName();
            BatchStatus status = jobExecution.getStatus();
            this.restartable = status.isGreaterThan(BatchStatus.STOPPING) && status.isLessThan(BatchStatus.ABANDONED);
            this.abandonable = status.isGreaterThan(BatchStatus.STARTED) && status!=BatchStatus.ABANDONED;
            this.stoppable  = status.isLessThan(BatchStatus.STOPPING);
        }
        else {
            this.jobName = "?";
        }

        // Duration is always in GMT
        durationFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        // The others can be localized
        timeFormat.setTimeZone(timeZone);
        dateFormat.setTimeZone(timeZone);
        if (jobExecution.getStartTime() != null) {
            this.startDate = dateFormat.format(jobExecution.getStartTime());
            this.startTime = timeFormat.format(jobExecution.getStartTime());
            this.lastUpdated = dateFormat.format(jobExecution.getLastUpdated());
            Date endTime = jobExecution.getEndTime() != null ? jobExecution.getEndTime() : new Date();
            this.duration = durationFormat.format(new Date(endTime.getTime() - jobExecution.getStartTime().getTime()));
        }

    }

    public TimeZone getTimeZone() {
        return timeZone;
    }

    public String getName() {
        return jobName;
    }

    public Long getId() {
        return id;
    }

    public int getStepExecutionCount() {
        return stepExecutionCount;
    }

    public Long getJobId() {
        return jobId;
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

    public boolean isRestartable() {
        return restartable;
    }

    public boolean isAbandonable() {
        return abandonable;
    }

    public boolean isStoppable() {
        return stoppable;
    }

    public String getJobParametersString() {
        return jobParametersString;
    }

    public Properties getJobParameters() {
        return jobParameters;
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
     * Getter for property 'exitStatus'.
     *
     * @return Value for property 'exitStatus'.
     */
    public ExitStatus getExitStatus() {
        return exitStatus;
    }

    /**
     * Getter for property 'status'.
     *
     * @return Value for property 'status'.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Getter for property 'executionContext'.
     *
     * @return Value for property 'executionContext'.
     */
    public ExecutionContext getExecutionContext() {
        return executionContext;
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
     * Getter for property 'jobInstanceId'.
     *
     * @return Value for property 'jobInstanceId'.
     */
    public long getJobInstanceId() {
        return jobInstanceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JobExecutionInfo)) return false;

        JobExecutionInfo that = (JobExecutionInfo) o;

        if (getStepExecutionCount() != that.getStepExecutionCount()) return false;
        if (isRestartable() != that.isRestartable()) return false;
        if (isAbandonable() != that.isAbandonable()) return false;
        if (isStoppable() != that.isStoppable()) return false;
        if (getJobInstanceId() != that.getJobInstanceId()) return false;
        if (dateFormat != null ? !dateFormat.equals(that.dateFormat) : that.dateFormat != null) return false;
        if (timeFormat != null ? !timeFormat.equals(that.timeFormat) : that.timeFormat != null) return false;
        if (durationFormat != null ? !durationFormat.equals(that.durationFormat) : that.durationFormat != null)
            return false;
        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getJobId() != null ? !getJobId().equals(that.getJobId()) : that.getJobId() != null) return false;
        if (jobName != null ? !jobName.equals(that.jobName) : that.jobName != null) return false;
        if (getStartDate() != null ? !getStartDate().equals(that.getStartDate()) : that.getStartDate() != null)
            return false;
        if (getStartTime() != null ? !getStartTime().equals(that.getStartTime()) : that.getStartTime() != null)
            return false;
        if (getDuration() != null ? !getDuration().equals(that.getDuration()) : that.getDuration() != null)
            return false;
        if (jobExecution != null ? !jobExecution.equals(that.jobExecution) : that.jobExecution != null) return false;
        if (getJobParameters() != null ? !getJobParameters().equals(that.getJobParameters()) : that.getJobParameters() != null)
            return false;
        if (getJobParametersString() != null ? !getJobParametersString().equals(that.getJobParametersString()) : that.getJobParametersString() != null)
            return false;
        if (converter != null ? !converter.equals(that.converter) : that.converter != null) return false;
        return getTimeZone() != null ? getTimeZone().equals(that.getTimeZone()) : that.getTimeZone() == null;
    }

    @Override
    public int hashCode() {
        int result = dateFormat != null ? dateFormat.hashCode() : 0;
        result = 31 * result + (timeFormat != null ? timeFormat.hashCode() : 0);
        result = 31 * result + (durationFormat != null ? durationFormat.hashCode() : 0);
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        result = 31 * result + getStepExecutionCount();
        result = 31 * result + (getJobId() != null ? getJobId().hashCode() : 0);
        result = 31 * result + (jobName != null ? jobName.hashCode() : 0);
        result = 31 * result + (getStartDate() != null ? getStartDate().hashCode() : 0);
        result = 31 * result + (getStartTime() != null ? getStartTime().hashCode() : 0);
        result = 31 * result + (getDuration() != null ? getDuration().hashCode() : 0);
        result = 31 * result + (jobExecution != null ? jobExecution.hashCode() : 0);
        result = 31 * result + (getJobParameters() != null ? getJobParameters().hashCode() : 0);
        result = 31 * result + (getJobParametersString() != null ? getJobParametersString().hashCode() : 0);
        result = 31 * result + (isRestartable() ? 1 : 0);
        result = 31 * result + (isAbandonable() ? 1 : 0);
        result = 31 * result + (isStoppable() ? 1 : 0);
        result = 31 * result + (int) (getJobInstanceId() ^ (getJobInstanceId() >>> 32));
        result = 31 * result + (converter != null ? converter.hashCode() : 0);
        result = 31 * result + (getTimeZone() != null ? getTimeZone().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "JobExecutionInfo{" +
                "dateFormat=" + dateFormat +
                ", timeFormat=" + timeFormat +
                ", durationFormat=" + durationFormat +
                ", id=" + id +
                ", stepExecutionCount=" + stepExecutionCount +
                ", jobId=" + jobId +
                ", jobName='" + jobName + '\'' +
                ", startDate='" + startDate + '\'' +
                ", startTime='" + startTime + '\'' +
                ", duration='" + duration + '\'' +
                ", jobExecution=" + jobExecution +
                ", jobParameters=" + jobParameters +
                ", jobParametersString='" + jobParametersString + '\'' +
                ", restartable=" + restartable +
                ", abandonable=" + abandonable +
                ", stoppable=" + stoppable +
                ", jobInstanceId=" + jobInstanceId +
                ", converter=" + converter +
                ", timeZone=" + timeZone +
                '}';
    }
}
