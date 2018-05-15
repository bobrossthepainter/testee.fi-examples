package fi.testee.examples.cucumber.auth;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class AuthStore {

    private String authToken;

    public AuthStore() {
        this.authToken = "dummyToken";
    }

    public String getAuthToken() {
        return authToken;
    }
}
