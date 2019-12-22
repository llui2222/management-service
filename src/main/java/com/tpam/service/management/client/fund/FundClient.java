package com.tpam.service.management.client.fund;

import org.springframework.stereotype.Component;

@Component
public class FundClient {

    private final FundRestClient fundRestClient;

    public FundClient(final FundRestClient fundRestClient) {
        this.fundRestClient = fundRestClient;
    }

    //TODO Uncomment to integrate with jFund
    public void stopRunningStrategy(final Long strategyId) {
        //fundRestClient.stopRunningStrategy(strategyId);
    }
}
