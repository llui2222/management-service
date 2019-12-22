package com.tpam.service.management.batch.writer;

import com.tpam.service.management.client.trade.TradeServiceClient;
import com.tpam.service.management.client.trade.model.SubmitTradeRequest;
import io.github.benas.randombeans.api.EnhancedRandom;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;

@RunWith(MockitoJUnitRunner.class)
public class TradeExecutionWriterTest {

    @Mock
    private TradeServiceClient tradeServiceClient;

    @InjectMocks
    private TradeExecutionWriter writer;

    @Before
    public void setup() {
        ReflectionTestUtils.setField(writer, "tradeDelayInMillis", 0);
    }

    @Test
    public void shouldSubmitTradeRequests() {
        final SubmitTradeRequest tradeRequest1 = Mockito.mock(SubmitTradeRequest.class);
        final SubmitTradeRequest tradeRequest2 = Mockito.mock(SubmitTradeRequest.class);

        writer.write(Arrays.asList(tradeRequest1, tradeRequest2));

        Mockito.verify(tradeServiceClient).submitTrade(tradeRequest1);
        Mockito.verify(tradeServiceClient).submitTrade(tradeRequest2);
    }

    @Test
    public void shouldMarkRequestsAsSubmitted() {
        final SubmitTradeRequest tradeRequest1 = EnhancedRandom.random(SubmitTradeRequest.class);
        final SubmitTradeRequest tradeRequest2 = EnhancedRandom.random(SubmitTradeRequest.class);

        writer.write(Arrays.asList(tradeRequest1, tradeRequest2));

        Assertions.assertThat(tradeRequest1.isSubmitted()).isTrue();
        Assertions.assertThat(tradeRequest2.isSubmitted()).isTrue();
    }
}