package com.forexArbitrage.application.apiAccess;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.logging.Logger;

@Component
public class RestCall {
    private static HttpURLConnection connection;

    private static final Logger logger = Logger.getLogger(RestCall.class.getName());

    public String jsonToStringAPI(String http) {
        StringBuffer response = new StringBuffer();
        try {
            URL url = new URL(http);
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(2000);
            connection.setReadTimeout(2000);

            int status = connection.getResponseCode();
            logger.info("API connection code is " + status);

            if (status >= 0) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
            }
            return response.toString();

        } catch (Exception e) {
            logger.info("Malformed API URL or improper usage");
            e.printStackTrace();
            return "ERROR";
        }
    }
}
