package com.forexArbitrage.application.apiAccess;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.forexArbitrage.application.verification.PropertyLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class CurrencyAccess {
    @Autowired
    RestCall restCall;

    @Autowired
    PropertyLoader propertyLoader;

    private static final Logger logger = Logger.getLogger(CurrencyModel.class.getName());

    // currency api access here
    private String response;

    private void loadCurrencyRates(String APIkey) {
        String http = String.format("http://api.exchangeratesapi.io/v1/latest?access_key=%s", APIkey);
        response = restCall.jsonToStringAPI(http);
    }

    private ExchangeRates parseJson() throws JsonProcessingException {
        ExchangeRates exchangeRates = new ObjectMapper().readerFor(ExchangeRates.class).readValue(response);

        logger.info("base currency of exchanges is " + exchangeRates.getBase());
        logger.info("loaded exchange rates of " + exchangeRates.getRates().size() + " currencies");

        return exchangeRates;
    }

    public ExchangeRates getExchangeRates(String APIKey) throws JsonProcessingException {
        loadCurrencyRates(APIKey);
        return parseJson();
    }
}
