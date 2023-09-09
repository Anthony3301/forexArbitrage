package com.forexArbitrage.application.apiAccess;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CurrencyAccess {
    @Autowired
    RestCall restCall;

    // currency api access here
    private String APIkey = "704b65463e4eb7ecbe22f4ef3534e17b";
    private String http = String.format("http://api.exchangeratesapi.io/v1/latest?access_key=%s", APIkey);
    private String response;

    public void loadCurrencyRates() {
        response = restCall.jsonToStringAPI(http);

        System.out.println(response);
    }
}