package fi.testee.example.rest;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class MyFacade {
    public String perform(final String input) {
        return input;
    }
}
