package com.tpam.service.management.batch.writer;

import com.tpam.service.management.client.trade.TradeServiceClient;
import com.tpam.service.management.client.trade.model.SubmitTradeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@StepScope
public class TradeExecutionWriter implements ItemWriter<SubmitTradeRequest> {

    private final Logger logger = LoggerFactory.getLogger(TradeExecutionWriter.class);

    private final TradeServiceClient tradeServiceClient;
    private final Integer tradeDelayInMillis;

    public TradeExecutionWriter(final TradeServiceClient tradeServiceClient,
                                @Value("#{jobParameters['tradeDelayInMillis']}") final Integer tradeDelayInMillis) {
        this.tradeServiceClient = tradeServiceClient;
        this.tradeDelayInMillis = tradeDelayInMillis;
    }

    @Override
    public void write(final List<? extends SubmitTradeRequest> trades) {

        trades.forEach(trade -> {
                tradeServiceClient.submitTrade(trade);
                trade.setSubmitted(true);
                delayExecution();
            }
        );
    }

    private void delayExecution() {
        try {
            Thread.sleep(tradeDelayInMillis);
        }
        catch (final InterruptedException e) {
            logger.error("The current thread was interrupted while waiting on trade split interval", e);
        }
    }
}
