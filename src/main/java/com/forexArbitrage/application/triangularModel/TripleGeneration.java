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
    private List<String[]> triples = new ArrayList<String[]>();

    public void generateCurrenciesWithExchangeRates(ExchangeRates exchangeRates) {
        for (String curr : exchangeRates.getRates().keySet()) {
            currenciesWithExchangeRates.add(curr);
        }
    }

    public void generateTriples() {
        int len = currenciesWithExchangeRates.size();

        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                for (int k = 0; k < len; k++) {
                    if (i != k && i != j && j != k) {
                        String[] tmp = {currenciesWithExchangeRates.get(i), currenciesWithExchangeRates.get(j), currenciesWithExchangeRates.get(k)};

                        triples.add(tmp);
                    }
                }
            }
        }
    }




    public List<String[]> getTriples(ExchangeRates exchangeRates) {
        generateCurrenciesWithExchangeRates(exchangeRates);
        generateTriples();

        return triples;
    }
}
