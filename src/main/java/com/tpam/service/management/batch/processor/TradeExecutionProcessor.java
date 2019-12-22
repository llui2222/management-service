package com.tpam.service.management.batch.processor;

import com.tpam.service.management.client.trade.model.SubmitTradeRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class TradeExecutionProcessor implements ItemProcessor<SubmitTradeRequest, SubmitTradeRequest> {

    private final Logger logger = LoggerFactory.getLogger(TradeExecutionProcessor.class);

    @Override
    public SubmitTradeRequest process(final SubmitTradeRequest item) {
        if (item.isSubmitted()) {
            logger.info("Filtering trade request since it has been already submitted. Trade Request: {}", item);
            return null;
        }
        return item;
    }
}
