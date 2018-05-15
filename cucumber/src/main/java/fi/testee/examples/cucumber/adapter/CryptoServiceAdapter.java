package fi.testee.examples.cucumber.adapter;

import javax.ejb.Singleton;
import javax.inject.Inject;

import fi.testee.examples.cucumber.auth.AuthStore;

@Singleton
public class CryptoServiceAdapter {

    @Inject
    private AuthStore authStore;

    public float getPrice(final String cryptoPair) {
        final String authToken = authStore.getAuthToken();
        // Code to receive the price of a given crypto-currency pair from another microservice
        return 0;
    }
}