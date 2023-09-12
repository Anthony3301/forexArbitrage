package com.forexArbitrage.application.verification;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.forexArbitrage.application.apiAccess.RestCall;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

@Component
public class CurrencyVerifier {
    @Autowired
    RestCall restCall;

    private static final Logger logger = Logger.getLogger(CurrencyVerifier.class.getName());

    // utilities for reading the api for currencies
    private String date = "latest";
    private String endPoint = "currencies";
    private String response;

    // parsed json to hashMap
    private HashMap<String, String> currencies;

    // API call to cache of all valid currencies worldwide
    private void loadCurrencies(String apiVersion) {
        String httpRequest = String.format("https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@%s/%s/%s.json",
                apiVersion, date, endPoint);

        response = restCall.jsonToStringAPI(httpRequest);
    }

    // parses the json string into a hashmap of (currency code, currency name)
    private void parseJson() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<HashMap<String, String>> typeReference = new TypeReference<HashMap<String, String>>(){};
            currencies = objectMapper.readValue(response.toString(), typeReference);

            logger.info(String.format("Loaded %d currencies!%n", currencies.size()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // wrapper function to simplify the cache process
    public boolean getCurrencyCache(String apiVersion) {
        try {
            loadCurrencies(apiVersion);
            parseJson();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // checks if the code or name of the currency is valid
    public boolean isValidCurrency(String currency) {
        currency = currency.toLowerCase(Locale.ROOT).strip();

        for (Map.Entry<String,String> pair : currencies.entrySet()) {
            if (pair.getKey().equals(currency) || pair.getValue().equals(currency)) {
                return true;
            }
        }

        return false;
    }
}
