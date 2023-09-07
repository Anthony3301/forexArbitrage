package com.forexArbitrage.application.Runner;

import com.forexArbitrage.application.verification.CurrencyVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.file.FileAlreadyExistsException;
import java.util.*;

@Component
public class Runner {
    @Autowired
    CurrencyVerifier currVer;

    public void run() {
        try {
            // load the cache of securities
            currVer.getCurrencyCache();

            // input currencies (change for later)
            ArrayList<String> triangularCurrencies = new ArrayList<String>() {
                {
                    add("USD");
                    add("EUR");
                    add("CAD");
                }
            };
            boolean isValid = true;

            // loop through currencies and determine if any are invalid
            for (String curr : triangularCurrencies) {
                if (!currVer.isValidCurrency(curr)) {
                    System.out.println(String.format("Currency %s is not a valid currency.", curr));
                    isValid = false;
                } else {
                    System.out.println(String.format("Currency %s is a valid currency!", curr));
                }
            }

            if (isValid) {
                // call currency api here
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
