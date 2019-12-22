package com.tpam.service.management.batch.processor;

import com.tpam.service.management.client.trade.model.SubmitTradeRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class TradeExecutionProcessorTest {

    private final TradeExecutionProcessor processor = new TradeExecutionProcessor();

    @Mock
    private SubmitTradeRequest tradeRequest;

    @Test
    public void shouldProcessRequest() {
        given(tradeRequest.isSubmitted()).willReturn(false);

        final SubmitTradeRequest processedRequest = processor.process(tradeRequest);

        assertThat(processedRequest).isNotNull();
        assertThat(processedRequest).isEqualTo(processedRequest);
    }

    @Test
    public void shouldSkipAlreadyProcessRequest() {
        given(tradeRequest.isSubmitted()).willReturn(true);

        final SubmitTradeRequest processedRequest = processor.process(tradeRequest);

        assertThat(processedRequest).isNull();
    }
}