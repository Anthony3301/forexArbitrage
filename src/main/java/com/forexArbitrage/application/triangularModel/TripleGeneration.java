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
        int k = 3;
        int[] s = new int[3];

        for (int i = 0; (s[i] = i) < 2; i++);
        triples.add(createSubset(s));
        for (;;) {
            int i;

            for (i = 2; i >= 0 && s[i] == i; i--);
            if (i < 0) {
                break;
            }
            s[i]++;
            for (++i; i < 3; i++) {
                s[i] = s[i-1] + 1;
            }
            triples.add(createSubset(s));
        }

    }


    private String[] createSubset(int[] subset) {
        String[] result = new String[3];

        for (int i = 0; i < 3; i++) {
            result[i] = currenciesWithExchangeRates.get(subset[i]);
        }

        return result;
    }

    public List<String[]> getTriples(ExchangeRates exchangeRates) {
        generateCurrenciesWithExchangeRates(exchangeRates);
        generateTriples();

        return triples;
    }
}
