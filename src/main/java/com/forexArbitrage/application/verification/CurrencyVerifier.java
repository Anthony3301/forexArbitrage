package com.forexArbitrage.application.verification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.apache.catalina.connector.InputBuffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Component
public class CurrencyVerifier {

    // utilities for reading the api for currencies
    private String apiVersion = "cbb41bd8ff49f43f98a68d9a4e0ff88947a07e97";
    private String date = "latest";
    private String endPoint = "currencies";
    private static HttpURLConnection connection;
    private StringBuffer response = new StringBuffer();

    // parsed json to hashMap
    private HashMap<String, String> currencies;

    // API call to cache of all valid currencies worldwide
    private void loadCurrencies() {
        String httpRequest = String.format("https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@%s/%s/%s.json",
                apiVersion, date, endPoint);

        try {
            URL url = new URL(httpRequest);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(2000);
            connection.setReadTimeout(2000);

            int status = connection.getResponseCode();
            System.out.println("Currency API connection code is " + status);

            // connection status code > 299 means it is up and running
            if (status >= 0) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
            }


        } catch (Exception e) {
            System.out.println("Malformed API URL or improper usage");
            e.printStackTrace();
        }
    }

    // parses the json string into a hashmap of (currency code, currency name)
    private void parseJson() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            TypeReference<HashMap<String, String>> typeReference = new TypeReference<HashMap<String, String>>(){};
            currencies = objectMapper.readValue(response.toString(), typeReference);

            System.out.printf("Loaded %d currencies!%n", currencies.size());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // wrapper function to simplify the cache process
    public boolean getCurrencyCache() {
        try {
            loadCurrencies();
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
