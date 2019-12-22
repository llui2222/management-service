package com.tpam.service.management.batch.reader;

import com.tpam.service.management.client.trade.model.SubmitTradeRequest;
import com.tpam.service.management.service.ManagementService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class SymbolExposureReaderTest {

    private static final String TAKER_NAME = "dummyTaker";
    private static final String TAKER_LOGIN = "dummyLogin";
    private static final String SYMBOL = "symbol";
    private static final BigDecimal MAX_INSTRUMENTS_PER_SINGLE_TRADE = BigDecimal.valueOf(30.4);

    private SymbolExposureReader reader;

    @Mock
    private ManagementService managementService;

    @Before
    public void setup() {
        reader = new SymbolExposureReader(managementService, TAKER_NAME, TAKER_LOGIN, SYMBOL, MAX_INSTRUMENTS_PER_SINGLE_TRADE);
    }

    @Test
    public void readTest() {

        given(managementService.calculateTradeRequestsForClosingPositions(TAKER_NAME, TAKER_LOGIN, SYMBOL, MAX_INSTRUMENTS_PER_SINGLE_TRADE)).willReturn(Arrays.asList(Mockito.mock(SubmitTradeRequest.class), Mockito.mock(SubmitTradeRequest.class), Mockito.mock(SubmitTradeRequest.class)));

        reader.afterPropertiesSet();

        assertThat(reader.read()).isNotNull();
        assertThat(reader.read()).isNotNull();
        assertThat(reader.read()).isNotNull();
        assertThat(reader.read()).isNull();
    }

    @Test
    public void readEmptyTest() {

        given(managementService.calculateTradeRequestsForClosingPositions(TAKER_NAME, TAKER_LOGIN, SYMBOL, MAX_INSTRUMENTS_PER_SINGLE_TRADE)).willReturn(Collections.emptyList());

        reader.afterPropertiesSet();

        assertThat(reader.read()).isNull();
    }
}