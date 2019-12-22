package com.tpam.service.management.messaging.payload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tpam.service.management.messaging.StrategySymbol;

import java.util.List;

public class Strategy {

    private final Long id;
    private final String name;
    private final String takerLogin;
    private final String takerName;
    private final List<StrategySymbol> symbols;

    @JsonCreator
    public Strategy(@JsonProperty("id")final Long id,
                    @JsonProperty("name")final String name,
                    @JsonProperty("takerLogin")final String takerLogin,
                    @JsonProperty("takerName")final String takerName,
                    @JsonProperty("symbols")final List<StrategySymbol> symbols) {
        this.id = id;
        this.name = name;
        this.takerLogin = takerLogin;
        this.takerName = takerName;
        this.symbols = symbols;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTakerLogin() {
        return takerLogin;
    }

    public String getTakerName() {
        return takerName;
    }

    public List<StrategySymbol> getSymbols() {
        return symbols;
    }
}
