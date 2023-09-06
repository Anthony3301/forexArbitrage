package com.forexArbitrage.application.verification;

import org.apache.catalina.connector.InputBuffer;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

@Component
public class CurrencyVerifier {
    private String apiVersion = "cbb41bd8ff49f43f98a68d9a4e0ff88947a07e97";
    private String date = "latest";
    private String endPoint = "currencies";

    private static HttpURLConnection connection;

    private StringBuffer response = new StringBuffer();

    public void loadCurrencies() {
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
        } finally {
            System.out.println(response.toString());
        }


    }
}
