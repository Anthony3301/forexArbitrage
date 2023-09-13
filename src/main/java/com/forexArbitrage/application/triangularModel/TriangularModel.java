package com.forexArbitrage.application.triangularModel;

import com.forexArbitrage.application.apiAccess.ExchangeRates;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Component
public class TriangularModel {
    private List<String> currenciesName = new ArrayList<String>();
    private Integer total = 0;
    private HashMap<String, Float> currenciesRates = new HashMap<String, Float>();
    private List<Float> relativeRates = new ArrayList<Float>();
    private Boolean isArbitrageChance = false;
    private Float arbitrageValue = 1.000000000f;

    // logger
    private static final Logger logger = Logger.getLogger(TriangularModel.class.getName());


    public void setCurrencySymbols(String... currs) {
        // extendable input for if need more or less than three currencies
        logger.info("Adding Currencies to model");

        for (String curr : currs) {
            if (curr != null) {
                currenciesName.add(curr);
                total++;
            }
        }
    }

    public void setCurrencyRates(ExchangeRates exchangeRates) {
        logger.info("Adding rates to model");

        if (exchangeRates != null) {
            for (String currency : currenciesName) {
                Float rate = exchangeRates.getSpecificRate(currency);

                if (rate.equals(Float.MIN_VALUE)) {
                    logger.severe("Currency " + currency + " does not have an exchange rate");
                }

                logger.info("Currency " +  currency + " has an exchange rate of " + rate + " with " + exchangeRates.getBase());
                currenciesRates.put(currency, rate);
            }
        }

    }


    public void calculateRelativeRates() {
        // need to find relative exchange rates between the currencies for arbitrage calculation

        for (int i = 0; i < total; i++) {
            int j = (i + 1) % 3;
            String curr1 = currenciesName.get(i);
            String curr2 = currenciesName.get(j);
            Float rate1 = currenciesRates.get(curr1);
            Float rate2 = currenciesRates.get(curr2);

            Float relativeRate = (1 / rate1) / (1 / rate2);

            logger.info(String.format("Exchange rate %s/%s is %f", curr1, curr2, relativeRate));

            relativeRates.add(relativeRate);
        }
    }


    public void calculateArbitrageChance() {
        for (Float rate : relativeRates) {
            arbitrageValue *= rate;
        }

        if (arbitrageValue != 1.00000000f) {
            isArbitrageChance = true;
            logger.info("Arbitrage is possible");
        } else {
            logger.info("Arbitrage is not possible");
        }
    }

    public Float getArbitrageValue() {
        return arbitrageValue;
    }


    public void runModel(ExchangeRates exchangeRates, String... currs) {
        setCurrencySymbols(currs);
        setCurrencyRates(exchangeRates);
        calculateRelativeRates();
        calculateArbitrageChance();
    }
}
