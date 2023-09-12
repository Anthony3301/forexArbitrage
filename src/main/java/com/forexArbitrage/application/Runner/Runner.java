package com.forexArbitrage.application.Runner;

import com.forexArbitrage.application.apiAccess.CurrencyAccess;
import com.forexArbitrage.application.apiAccess.CurrencyModel;
import com.forexArbitrage.application.apiAccess.ExchangeRates;
import com.forexArbitrage.application.verification.CurrencyVerifier;
import com.forexArbitrage.application.verification.PropertyLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.FileAlreadyExistsException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class Runner {
    @Autowired
    CurrencyVerifier currVer;

    @Autowired
    CurrencyAccess currencyAccess;

    @Autowired
    PropertyLoader propertyLoader;

    private static final Logger logger = Logger.getLogger(Runner.class.getName());


    public void run() {
        try {
            // load the properties of the project
            propertyLoader.loadProperties();

            // load the cache of securities
            currVer.getCurrencyCache(propertyLoader.getCurrencyKey());

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
                   logger.info(String.format("Currency %s is not a valid currency!", curr));
                    isValid = false;
                } else {
                    logger.info(String.format("Currency %s is a valid currency!", curr));
                }
            }

            if (isValid) {
                // call currency exchange rate api
                ExchangeRates exchangeRates = currencyAccess.getExchangeRates(propertyLoader.getExchangeKey());

                String base = exchangeRates.getBase();

                ArrayList<CurrencyModel> CurrencyModel = new ArrayList<CurrencyModel>();

                // load exchange rates
                for (String curr :  triangularCurrencies) {
                    Float exRate = exchangeRates.getSpecificRate(curr);
                    triangularExchangeRates.add(exRate);
                    logger.info(curr + " exchange rate with " +  base + " is " + exRate);
                }

                // calculate relative exchange rates
                Float CurrOnetoCurrTwo = (1/ triangularExchangeRates.get(0)) / (1 / triangularExchangeRates.get(1));
                Float CurrTwotoCurrThree = (1 / triangularExchangeRates.get(1)) / (1 / triangularExchangeRates.get(2));
                Float CurrThreetoCurrOne = (1 / triangularExchangeRates.get(2)) / (1 / triangularExchangeRates.get(0));

                logger.info(triangularCurrencies.get(0) + "/" + triangularCurrencies.get(1) + " exchange rate is " + CurrOnetoCurrTwo);
                logger.info(triangularCurrencies.get(1) + "/" + triangularCurrencies.get(2) + " exchange rate is " + CurrTwotoCurrThree);
                logger.info(triangularCurrencies.get(2) + "/" + triangularCurrencies.get(0) + " exchange rate is " + CurrThreetoCurrOne);

                Float finalVal = CurrOnetoCurrTwo * CurrTwotoCurrThree * CurrThreetoCurrOne;

                logger.info("Triangluar forex exchange is " + finalVal);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
