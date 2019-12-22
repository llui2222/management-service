package com.tpam.service.management.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.support.PropertiesConverter;
import org.springframework.stereotype.Service;

@Service
public class JobService {
    private static final Logger logger = LoggerFactory.getLogger(JobService.class);

    private final JobOperator jobOperator;

    public JobService(final JobOperator jobOperator) {
        this.jobOperator = jobOperator;
    }

    public void launchJob(final String jobName, final JobParameters jobParameters) {
        logger.debug("Starting job {} with parameters: {}", jobName, jobParameters);
        try {
            jobOperator.start(jobName, PropertiesConverter.propertiesToString(jobParameters.toProperties()));
        }
        catch (final Exception e) {
            logger.error("Error while starting job {}", jobName, e);
            throw new RuntimeException("Error while starting job", e);
        }
    }
}