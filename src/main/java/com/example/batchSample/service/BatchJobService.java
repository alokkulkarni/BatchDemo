package com.example.batchSample.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.admin.service.*;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.ListableJobLocator;
import org.springframework.batch.core.launch.*;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.core.repository.dao.ExecutionContextDao;
import org.springframework.batch.core.step.NoSuchStepException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by alokkulkarni on 25/03/17.
 */
public class BatchJobService implements JobService {
    private static final Log logger = LogFactory.getLog(SimpleJobService.class);

    // 60 seconds
    private static final int DEFAULT_SHUTDOWN_TIMEOUT = 60 * 1000;

    private final SearchableJobInstanceDao jobInstanceDao;

    private final SearchableJobExecutionDao jobExecutionDao;

    private final JobRepository jobRepository;

    private final JobLauncher jobLauncher;

    private final ListableJobLocator jobLocator;

    private final SearchableStepExecutionDao stepExecutionDao;

    private final ExecutionContextDao executionContextDao;

    private Collection<JobExecution> activeExecutions = Collections.synchronizedList(new ArrayList<JobExecution>());

    private int shutdownTimeout = DEFAULT_SHUTDOWN_TIMEOUT;

    /**
     * Timeout for shutdown waiting for jobs to finish processing.
     *
     * @param shutdownTimeout in milliseconds (default 60 secs)
     */
    public void setShutdownTimeout(int shutdownTimeout) {
        this.shutdownTimeout = shutdownTimeout;
    }

    public BatchJobService(SearchableJobInstanceDao jobInstanceDao, SearchableJobExecutionDao jobExecutionDao,
                            SearchableStepExecutionDao stepExecutionDao, JobRepository jobRepository, JobLauncher jobLauncher,
                            ListableJobLocator jobLocator, ExecutionContextDao executionContextDao) {
        super();
        this.jobInstanceDao = jobInstanceDao;
        this.jobExecutionDao = jobExecutionDao;
        this.stepExecutionDao = stepExecutionDao;
        this.jobRepository = jobRepository;
        this.jobLauncher = jobLauncher;
        this.jobLocator = jobLocator;
        this.executionContextDao = executionContextDao;
    }

    @Override
    public boolean isLaunchable(String jobName) {
        return false;
    }

    @Override
    public JobExecution launch(String jobName, JobParameters params) throws NoSuchJobException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        return null;
    }

    @Override
    public JobParameters getLastJobParameters(String jobName) throws NoSuchJobException {
        return null;
    }

    @Override
    public JobExecution restart(Long jobExecutionId) throws NoSuchJobExecutionException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, NoSuchJobException, JobParametersInvalidException {
        return null;
    }

    @Override
    public JobExecution stop(Long jobExecutionId) throws NoSuchJobExecutionException, JobExecutionNotRunningException {
        return null;
    }

    @Override
    public JobExecution abandon(Long jobExecutionId) throws NoSuchJobExecutionException, JobExecutionAlreadyRunningException {
        return null;
    }

    @Override
    public Collection<String> listJobs(int start, int count) {
        return null;
    }

    @Override
    public int countJobs() {
        return 0;
    }

    @Override
    public JobInstance getJobInstance(long jobInstanceId) throws NoSuchJobInstanceException {
        return null;
    }

    @Override
    public Collection<JobInstance> listJobInstances(String jobName, int start, int count) throws NoSuchJobException {
        return null;
    }

    @Override
    public int countJobInstances(String jobName) throws NoSuchJobException {
        return 0;
    }

    @Override
    public Collection<JobExecution> listJobExecutionsForJob(String jobName, int start, int count) throws NoSuchJobException {
        return null;
    }

    @Override
    public int countJobExecutionsForJob(String jobName) throws NoSuchJobException {
        return 0;
    }

    @Override
    public Collection<JobExecution> getJobExecutionsForJobInstance(String jobName, Long jobInstanceId) throws NoSuchJobException {
        return null;
    }

    @Override
    public Collection<JobExecution> listJobExecutions(int start, int count) {
        return null;
    }

    @Override
    public int countJobExecutions() {
        return 0;
    }

    @Override
    public JobExecution getJobExecution(Long jobExecutionId) throws NoSuchJobExecutionException {
        return null;
    }

    @Override
    public Collection<StepExecution> getStepExecutions(Long jobExecutionId) throws NoSuchJobExecutionException {
        return null;
    }

    @Override
    public Collection<StepExecution> listStepExecutionsForStep(String jobName, String stepName, int start, int count) throws NoSuchStepException {
        return null;
    }

    @Override
    public int countStepExecutionsForStep(String jobName, String stepName) throws NoSuchStepException {
        return 0;
    }

    @Override
    public StepExecution getStepExecution(Long jobExecutionId, Long stepExecutionId) throws NoSuchStepExecutionException, NoSuchJobExecutionException {
        return null;
    }

    @Override
    public int stopAll() {
        return 0;
    }

    @Override
    public boolean isIncrementable(String jobName) {
        return false;
    }

    @Override
    public Collection<String> getStepNamesForJob(String jobName) throws NoSuchJobException {
        return null;
    }
}
