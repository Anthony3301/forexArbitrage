package com.forexArbitrage.application.triangularModel;

import com.forexArbitrage.application.apiAccess.CurrencyAccess;
import com.forexArbitrage.application.apiAccess.ExchangeRates;
import com.forexArbitrage.application.verification.CurrencyVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TripleGeneration {
    // purpose of this class is to cross-check between valid currencies and currencies with exchange rates
    // to generate all possible triples of currencies to perform a simultaneous check of whether any exist on the
    // market at that instant

    @Autowired
    ExchangeRates exchangeRates;

    private List<String> currenciesWithExchangeRates = new ArrayList<String>();
    private List<List<String>> triples = new ArrayList<List<String>>();

    public void generateCurrenciesWithExchangeRates(ExchangeRates exchangeRates) {
        for (String curr : exchangeRates.getRates().keySet()) {
            currenciesWithExchangeRates.add(curr);
        }
    }

    public void generateTriples() {
        // todo: create triple combination functionality
    }
}
