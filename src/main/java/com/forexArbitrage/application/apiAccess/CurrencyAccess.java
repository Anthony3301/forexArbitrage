package com.forexArbitrage.application.apiAccess;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class CurrencyAccess {
    @Autowired
    RestCall restCall;

    private static final Logger logger = Logger.getLogger(CurrencyModel.class.getName());

    // currency api access here
    private String APIkey = "704b65463e4eb7ecbe22f4ef3534e17b";
    private String http = String.format("http://api.exchangeratesapi.io/v1/latest?access_key=%s", APIkey);
    private String response;

    private void loadCurrencyRates() {
        response = restCall.jsonToStringAPI(http);

        System.out.println(response);
    }

    private ExchangeRates parseJson() throws JsonProcessingException {
        ExchangeRates exchangeRates = new ObjectMapper().readerFor(ExchangeRates.class).readValue(response);

        logger.info("base currency of exchanges is " + exchangeRates.getBase());
        logger.info("loaded exchange rates of " + exchangeRates.getRates().size() + " currencies");

        return exchangeRates;
    }

    public ExchangeRates getExchangeRates() throws JsonProcessingException {
        loadCurrencyRates();
        return parseJson();
    }
}
