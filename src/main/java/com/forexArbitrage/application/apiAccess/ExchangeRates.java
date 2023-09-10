package com.forexArbitrage.application.apiAccess;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// pojo to map the json of exchange rates
@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRates {
    private String base;
    private Map<String, Float> rates;


    // getters and setters
    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Map<String, Float> getRates() {
        return rates;
    }

    public void setRates(Map<String, Float> rates) {
        this.rates = rates;
    }

    public Float getSpecificRate(String currency) {
        if (this.rates.containsKey(currency)) {
            return this.rates.get(currency);
        }

        return Float.MIN_VALUE;
    }
}
