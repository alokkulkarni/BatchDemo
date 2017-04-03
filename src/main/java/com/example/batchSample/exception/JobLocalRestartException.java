package com.example.batchSample.exception;

import org.springframework.batch.core.JobExecutionException;

/**
 * Created by alokkulkarni on 25/03/17.
 */
public class JobLocalRestartException extends JobExecutionException {
    public JobLocalRestartException(String msg) {
        super(msg);
    }

    public JobLocalRestartException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
