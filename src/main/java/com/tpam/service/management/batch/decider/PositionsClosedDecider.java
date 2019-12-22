package com.tpam.service.management.batch.decider;

import com.tpam.service.management.batch.common.JobConstants;
import com.tpam.service.management.batch.common.JobParams;
import com.tpam.service.management.client.trade.TradeServiceClient;
import com.tpam.service.management.client.trade.model.StrategySymbolExposure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PositionsClosedDecider implements JobExecutionDecider {

    private static final Logger logger = LoggerFactory.getLogger(PositionsClosedDecider.class);

    private final TradeServiceClient tradeServiceClient;

    public PositionsClosedDecider(final TradeServiceClient tradeServiceClient) {
        this.tradeServiceClient = tradeServiceClient;
    }

    public FlowExecutionStatus decide(final JobExecution jobExecution, final StepExecution stepExecution) {

        final String takerName = stepExecution.getJobParameters().getString(JobParams.TAKER_NAME);
        final String takerLogin = stepExecution.getJobParameters().getString(JobParams.TAKER_LOGIN);
        final String symbol = stepExecution.getJobParameters().getString(JobParams.SYMBOL_NAME);
        final StrategySymbolExposure symbolExposures = tradeServiceClient.getStrategySymbolExposures(takerName, takerLogin, symbol);
        if (BigDecimal.ZERO.compareTo(symbolExposures.getTotalBaseCurrencyExposure()) != 0) {
            logger.warn("Not all positions are closed for taker {}, login {} and symbol {}. Repeating job step {}", takerName, takerLogin, symbol, stepExecution.getStepName());
            return new FlowExecutionStatus(JobConstants.DECISION_STATUS_RESTART);
        }
        return FlowExecutionStatus.COMPLETED;
    }
}