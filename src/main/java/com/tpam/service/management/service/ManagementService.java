package com.tpam.service.management.service;

import com.tpam.service.management.batch.common.JobConstants;
import com.tpam.service.management.batch.common.JobParams;
import com.tpam.service.management.client.fund.FundClient;
import com.tpam.service.management.client.trade.TradeServiceClient;
import com.tpam.service.management.client.trade.model.StrategySymbolExposure;
import com.tpam.service.management.client.trade.model.SubmitTradeRequest;
import com.tpam.service.management.messaging.payload.Strategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ManagementService {

    private final Logger logger = LoggerFactory.getLogger(ManagementService.class);

    private final FundClient fundClient;
    private final TradeServiceClient tradeServiceClient;
    private final JobService jobService;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public ManagementService(final FundClient fundClient, final TradeServiceClient tradeServiceClient, final JobService jobService) {
        this.fundClient = fundClient;
        this.tradeServiceClient = tradeServiceClient;
        this.jobService = jobService;
    }

    public void closeStrategyPositions(final Strategy strategy) {
        logger.info("Stopping running strategy {}...", strategy.getName());
        fundClient.stopRunningStrategy(strategy.getId());

        strategy.getSymbols().stream().map(symbol ->
            new JobParametersBuilder()
                .addString(JobParams.JOB_ID, UUID.randomUUID().toString())
                .addString(JobParams.TAKER_NAME, strategy.getTakerName())
                .addString(JobParams.TAKER_LOGIN, strategy.getTakerLogin())
                .addString(JobParams.SYMBOL_NAME, symbol.getSymbolName())
                .addString(JobParams.MAX_INSTRUMENTS_PER_SINGLE_TRADE, symbol.getMaxInstrumentsPerSingleTrade().toString())
                .addString(JobParams.TRADE_DELAY_IN_MILLIS, symbol.getTradeDelayInMillis().toString())
                .toJobParameters())
            .forEach(jobParameters -> jobService.launchJob(JobConstants.CLOSE_POSITIONS_JOB, jobParameters));
    }

    public List<SubmitTradeRequest> calculateTradeRequestsForClosingPositions(final String takerName, final String takerLogin, final String symbol, final BigDecimal maxInstrumentsPerSingleTrade) {
        final StrategySymbolExposure exposure = tradeServiceClient.getStrategySymbolExposures(takerName, takerLogin, symbol);
        final List<SubmitTradeRequest> requests = new ArrayList<>();
        final boolean buyOrSell = exposure.getTotalBaseCurrencyExposure().compareTo(BigDecimal.ZERO) > 0;
        final BigDecimal[] result = exposure.getTotalBaseCurrencyExposure().abs().divideAndRemainder(maxInstrumentsPerSingleTrade);
        if (BigDecimal.ZERO.compareTo(result[0]) < 0) {
            for (long i = 0; i < result[0].longValue(); i++) {
                requests.add(new SubmitTradeRequest(UUID.randomUUID().toString(), maxInstrumentsPerSingleTrade, BigDecimal.ZERO, exposure.getTakerName(), exposure.getTakerLogin(), exposure.getSymbol(), !buyOrSell));
            }
        }
        if (BigDecimal.ZERO.compareTo(result[1]) < 0) {
            requests.add(new SubmitTradeRequest(UUID.randomUUID().toString(), result[1], BigDecimal.ZERO, exposure.getTakerName(), exposure.getTakerLogin(), exposure.getSymbol(), !buyOrSell));
        }

        return requests;
    }
}
