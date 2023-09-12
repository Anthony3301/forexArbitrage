package com.forexArbitrage.application.verification;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

@Component
public class PropertyLoader {
    private static final Logger logger = Logger.getLogger(PropertyLoader.class.getName());

    private String currencyKey;
    private String exchangeKey;
    private String path = "C:/Users/Anthony/Documents/forexArbitrage/src/main/resources/application.properties";

    Properties properties = new Properties();
    FileInputStream props;

    public void loadProperties() {
        try {
            logger.info("Loading properties...");

            FileInputStream props = new FileInputStream(path);
            properties.load(props);

            currencyKey = properties.getProperty("currencyAPI.key");
            exchangeKey = properties.getProperty("exchangeRateAPI.key");

            logger.info("Currency API key is " + currencyKey);
            logger.info("ExchangeRate API key is " + exchangeKey);

        } catch (FileNotFoundException e) {
            logger.severe("Property File not found");
        } catch (IOException e) {
            logger.severe("Malformed property file");
        } finally {
            try {
               if (props != null) props.close();
            } catch (IOException e) {
                logger.severe("Cannot close property file");
            }
        }
    }

    public String getCurrencyKey() {
        return currencyKey;
    }

    public String getExchangeKey() {
        return exchangeKey;
    }

}
