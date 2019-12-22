package com.tpam.service.management.client.trade;

import com.tpam.service.management.client.trade.model.StrategySymbolExposure;
import com.tpam.service.management.client.trade.model.SubmitTradeRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@FeignClient(value = "backup-trade-service")
interface TradeServiceRestClient {

    @GetMapping("/trades/taker-names/{takerName}/taker-logins/{takerLogin}")
    List<StrategySymbolExposure> getStrategyExposures(@PathVariable("takerName") final String takerName, @PathVariable("takerLogin") final String takerLogin);

    @GetMapping("/trades/taker-names/{takerName}/taker-logins/{takerLogin}/symbols/{symbol}")
    StrategySymbolExposure getStrategySymbolExposures(@PathVariable("takerName") final String takerName, @PathVariable("takerLogin") final String takerLogin, @PathVariable("symbol") final String symbol);

    @PostMapping("/trades")
    ResponseEntity<URI> submitTrade(@Valid @RequestBody final SubmitTradeRequest request);
}
