package fi.testee.example.rest;

import java.util.HashMap;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

import org.jboss.weld.context.bound.BoundRequestContext;

@Provider
@Priority(0)
public class RequestScopeInitiatorFilter implements ContainerRequestFilter, ContainerResponseFilter {

    @Inject
    private BoundRequestContext boundRequestContext;
    private HashMap<String, Object> storage;

    @Override
    public void filter(final ContainerRequestContext requestContext) {
        storage = new HashMap<>();
        boundRequestContext.associate(storage);
        boundRequestContext.activate();
    }

    @Override
    public void filter(final ContainerRequestContext requestContext, final ContainerResponseContext responseContext) {
        try {
            boundRequestContext.invalidate();
            boundRequestContext.deactivate();
        } finally {
            boundRequestContext.dissociate(storage);
        }
    }
}
