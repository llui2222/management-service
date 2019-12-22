package com.tpam.service.management.batch.job;

import com.tpam.service.management.ManagementApplication;
import com.tpam.service.management.batch.common.JobParams;
import com.tpam.service.management.batch.configuration.BatchTestConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ManagementApplication.class, BatchTestConfiguration.class})
public abstract class ClosePositionsJobBaseTest {

    @Autowired
    protected JobLauncherTestUtils jobLauncherTestUtils;

    static final String TAKER_NAME = "dummyTaker";
    static final String TAKER_LOGIN = "dummyLogin";
    static final String SYMBOL = "symbol";
    static final BigDecimal MAX_INSTRUMENTS_PER_SINGLE_TRADE = BigDecimal.valueOf(30.4);
    private static final Integer TRADE_DELAY_IN_MILLIS = 10;

    @Test
    public void testJob() throws Exception {

        final JobParameters jobParameters = new JobParametersBuilder()
            .addString(JobParams.JOB_ID, UUID.randomUUID().toString())
            .addString(JobParams.TAKER_NAME, TAKER_NAME)
            .addString(JobParams.TAKER_LOGIN, TAKER_LOGIN)
            .addString(JobParams.SYMBOL_NAME, SYMBOL)
            .addString(JobParams.MAX_INSTRUMENTS_PER_SINGLE_TRADE, MAX_INSTRUMENTS_PER_SINGLE_TRADE.toString())
            .addString(JobParams.TRADE_DELAY_IN_MILLIS, TRADE_DELAY_IN_MILLIS.toString())
            .toJobParameters();
        final JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParameters);

        verifyJobExecution(jobExecution);
    }

    abstract void verifyJobExecution(final JobExecution jobExecution);
}