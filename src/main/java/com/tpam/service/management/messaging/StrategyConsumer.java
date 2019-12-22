package com.tpam.service.management.messaging;

import com.tpam.service.management.messaging.payload.Strategy;
import com.tpam.service.management.service.ManagementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.Map;

@EnableBinding(StrategySink.class)
public class StrategyConsumer {

    private static final Logger logger = LoggerFactory.getLogger(StrategyConsumer.class);

    private final ManagementService managementService;

    public StrategyConsumer(final ManagementService managementService) {
        this.managementService = managementService;
    }

    @StreamListener(target = StrategySink.STRATEGY_SUSPENDED_INPUT)
    public void onStrategySuspended(@Payload final Strategy strategy, @Headers final Map<String, Object> headers) {
        logger.info("Received strategy suspended event{} with headers {}", strategy, headers);

        managementService.closeStrategyPositions(strategy);
    }
}
