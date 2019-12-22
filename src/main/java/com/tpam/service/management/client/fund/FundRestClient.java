package com.tpam.service.management.client.fund;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(value = "fund-application")
interface FundRestClient {

    @PutMapping("/strategies/{strategyId}/stop")
    void stopRunningStrategy(@PathVariable("strategyId") final Long strategyId);
}
