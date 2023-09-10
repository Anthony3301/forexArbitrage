package com.forexArbitrage.application.Runner;

import com.forexArbitrage.application.apiAccess.CurrencyAccess;
import com.forexArbitrage.application.apiAccess.ExchangeRates;
import com.forexArbitrage.application.verification.CurrencyVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.FileAlreadyExistsException;
import java.util.*;

@Component
public class Runner {
    @Autowired
    CurrencyVerifier currVer;

    @Autowired
    CurrencyAccess currencyAccess;

    public void run() {
        try {
            // load the cache of securities
            currVer.getCurrencyCache();

            // input currencies (change for later)
            ArrayList<String> triangularCurrencies = new ArrayList<String>() {
                {
                    add("USD");
                    add("EUR");
                    add("CAD");
                }
            };
            ArrayList<Float> triangularExchangeRates = new ArrayList<>();
            boolean isValid = true;

            // loop through currencies and determine if any are invalid
            for (String curr : triangularCurrencies) {
                if (!currVer.isValidCurrency(curr)) {
                    System.out.println(String.format("Currency %s is not a valid currency.", curr));
                    isValid = false;
                } else {
                    System.out.println(String.format("Currency %s is a valid currency!", curr));
                }
            }

            if (isValid) {
                // call currency exchange rate api
                ExchangeRates exchangeRates = currencyAccess.getExchangeRates();

                String base = exchangeRates.getBase();

                // load exchange rates
                for (String curr :  triangularCurrencies) {
                    Float exRate = exchangeRates.getSpecificRate(curr);
                    triangularExchangeRates.add(exRate);
                    System.out.println(curr + " exchange rate with " +  base + " is " + exRate);
                }

                
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
