package com.tpam.service.management.batch.decider;

import com.tpam.service.management.batch.common.JobConstants;
import com.tpam.service.management.batch.common.JobParams;
import com.tpam.service.management.client.trade.TradeServiceClient;
import com.tpam.service.management.client.trade.model.StrategySymbolExposure;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;

import java.math.BigDecimal;

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class PositionsClosedDeciderTest {

    @Mock
    private TradeServiceClient tradeServiceClient;
    @Mock
    private JobExecution jobExecution;
    @Mock
    private StepExecution stepExecution;
    @Mock
    private StrategySymbolExposure strategySymbolExposure;
    @InjectMocks
    private PositionsClosedDecider decider;

    @Before
    public void setup() {
        final String taker = "dummyTaker";
        final String login = "dummyLogin";
        final String symbol = "symbol";
        final JobParameters jobParameters = new JobParametersBuilder()
            .addString(JobParams.TAKER_NAME, taker)
            .addString(JobParams.TAKER_LOGIN, login)
            .addString(JobParams.SYMBOL_NAME, symbol)
            .toJobParameters();

        given(stepExecution.getJobParameters()).willReturn(jobParameters);
        given(tradeServiceClient.getStrategySymbolExposures(taker, login, symbol)).willReturn(strategySymbolExposure);
    }

    @Test
    public void shouldCompleteStepExecution() {
        given(strategySymbolExposure.getTotalBaseCurrencyExposure()).willReturn(BigDecimal.ZERO);

        final FlowExecutionStatus executionStatus = decider.decide(jobExecution, stepExecution);

        Assertions.assertThat(executionStatus).isEqualTo(FlowExecutionStatus.COMPLETED);
    }

    @Test
    public void shouldRepeatStepExecution() {
        given(strategySymbolExposure.getTotalBaseCurrencyExposure()).willReturn(BigDecimal.valueOf(1));

        final FlowExecutionStatus executionStatus = decider.decide(jobExecution, stepExecution);

        Assertions.assertThat(executionStatus).isEqualTo(new FlowExecutionStatus(JobConstants.DECISION_STATUS_RESTART));
    }
}