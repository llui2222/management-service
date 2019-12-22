package com.tpam.service.management.batch.configuration;

import com.tpam.service.management.batch.common.JobConstants;
import com.tpam.service.management.batch.common.JobParams;
import com.tpam.service.management.batch.decider.PositionsClosedDecider;
import com.tpam.service.management.batch.listener.JobCompletionListener;
import com.tpam.service.management.batch.processor.TradeExecutionProcessor;
import com.tpam.service.management.batch.reader.SymbolExposureReader;
import com.tpam.service.management.batch.writer.TradeExecutionWriter;
import com.tpam.service.management.client.trade.model.SubmitTradeRequest;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersValidator;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.DefaultJobParametersValidator;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;

@Configuration
@EnableBatchProcessing
public class JobsConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public JobsConfiguration(final JobBuilderFactory jobBuilderFactory, final StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Step closePositionsStep(final SymbolExposureReader symbolExposureReader,
                                   final TradeExecutionProcessor tradeExecutionProcessor,
                                   final TradeExecutionWriter tradeExecutionWriter) {

        final FixedBackOffPolicy backOffPolicy = new FixedBackOffPolicy();
        backOffPolicy.setBackOffPeriod(1000);

        return stepBuilderFactory.get(JobConstants.CLOSE_POSITIONS_STEP)
            .<SubmitTradeRequest, SubmitTradeRequest>chunk(10)
            .reader(symbolExposureReader)
            .processor(tradeExecutionProcessor)
            .writer(tradeExecutionWriter)
            .faultTolerant()
            .retry(Exception.class)
            .retryLimit(3)
            .backOffPolicy(backOffPolicy)
            .build();
    }

    @Bean
    public JobParametersValidator closeStrategySymbolPositionsParametersValidator() {
        return jobParametersValidator(JobParams.SYMBOL_NAME, JobParams.TAKER_LOGIN, JobParams.SYMBOL_NAME, JobParams.MAX_INSTRUMENTS_PER_SINGLE_TRADE, JobParams.TRADE_DELAY_IN_MILLIS);
    }

    @Bean
    public Job closePositionsJob(
        final JobParametersValidator closeStrategySymbolPositionsParametersValidator,
        final JobCompletionListener listener,
        final Step closeStrategySymbolPositionsStep,
        final PositionsClosedDecider decider) {

        return jobBuilderFactory.get(JobConstants.CLOSE_POSITIONS_JOB)
            .listener(listener)
            .validator(closeStrategySymbolPositionsParametersValidator)
            .flow(closeStrategySymbolPositionsStep)
            .next(decider).on(JobConstants.DECISION_STATUS_RESTART).to(closeStrategySymbolPositionsStep)
            .next(decider).on(FlowExecutionStatus.COMPLETED.getName()).end()
            .end()
            .build();
    }

    private JobParametersValidator jobParametersValidator(final String... requiredKeys) {
        final DefaultJobParametersValidator validator = new DefaultJobParametersValidator();
        validator.setRequiredKeys(requiredKeys);
        return validator;
    }
}
