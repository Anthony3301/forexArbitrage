package com.forexArbitrage.application.Runner;

import com.forexArbitrage.application.apiAccess.CurrencyAccess;
import com.forexArbitrage.application.apiAccess.ExchangeRates;
import com.forexArbitrage.application.triangularModel.TriangularModel;
import com.forexArbitrage.application.triangularModel.TripleGeneration;
import com.forexArbitrage.application.verification.CurrencyVerifier;
import com.forexArbitrage.application.verification.PropertyLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.datatransfer.FlavorEvent;
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

    @Autowired
    TriangularModel triangularModel;

    @Autowired
    TripleGeneration tripleGeneration;

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
                ExchangeRates exchangeRates = currencyAccess.getExchangeRates(propertyLoader.getExchangeKey());

                // testing of triple generation
                logger.info("Generated " + tripleGeneration.getTriples(exchangeRates).size() + " triples");

                triangularModel.runModel(exchangeRates, "USD", "EUR", "CAD");
                Float finalVal = triangularModel.getArbitrageValue();

                logger.info("Triangluar forex exchange is " + finalVal);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
