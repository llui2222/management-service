package com.tpam.service.management.messaging.payload;

import com.tpam.service.management.messaging.StrategySymbol;
import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import io.github.benas.randombeans.randomizers.range.BigDecimalRangeRandomizer;
import io.github.benas.randombeans.randomizers.range.IntegerRangeRandomizer;

import static io.github.benas.randombeans.FieldDefinitionBuilder.field;

public class StrategyTestFixture {

    private static final EnhancedRandom random = EnhancedRandomBuilder
        .aNewEnhancedRandomBuilder()
        .collectionSizeRange(1, 3)
        .randomize(field().named("maxInstrumentsPerSingleTrade").ofType(StrategySymbol.class).get(), new BigDecimalRangeRandomizer(1000L, 100000L))
        .randomize(field().named("tradeDelayInMillis").ofType(StrategySymbol.class).get(), new IntegerRangeRandomizer(0, 1000))
        .build();

    public static Strategy create() {

        return random.nextObject(Strategy.class);
    }
}