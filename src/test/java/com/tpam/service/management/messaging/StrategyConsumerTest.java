package com.tpam.service.management.messaging;

import com.tpam.service.management.messaging.payload.Strategy;
import com.tpam.service.management.messaging.payload.StrategyTestFixture;
import com.tpam.service.management.service.ManagementService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SuppressWarnings("ALL")
public class StrategyConsumerTest {

    @Autowired
    private StrategySink strategySink;

    @Autowired
    private StrategyConsumer strategyConsumer;

    @MockBean
    private ManagementService managementService;

    @Test
    public void onStrategySuspendedTest() {

        Message<Strategy> strategySuspendedMessage = new GenericMessage<>(StrategyTestFixture.create());

        strategySink.strategySuspendedInput().send(strategySuspendedMessage);

        Mockito.verify(managementService).closeStrategyPositions(strategySuspendedMessage.getPayload());
    }
}