package com.forexArbitrage.application.apiAccess;

import org.springframework.stereotype.Component;

@Component
public class CurrencyModel {
    private String symbol;
    private String base;
    private Float rate;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }
}
