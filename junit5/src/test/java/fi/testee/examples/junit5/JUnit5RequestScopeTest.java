package fi.testee.examples.junit5;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;

import javax.enterprise.concurrent.ManagedExecutorService;
import javax.inject.Inject;

import org.jboss.weld.context.bound.BoundRequestContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import fi.testee.junit5.TestEEfi;
import fi.testee.util.resourcedef.ResourceDef;

@ExtendWith(TestEEfi.class)
public class JUnit5RequestScopeTest {
    @ResourceDef
    @Mock
    private ManagedExecutorService managedExecutorService;

    @Inject
    private MyRequestScopeBean subject;

    @Inject
    private BoundRequestContext requestContext;
    private HashMap<String, Object> storage;

    @BeforeEach
    public void setUp() {
        storage = new HashMap<>();
        requestContext.associate(storage);
        requestContext.activate();
    }

    @AfterEach
    public void tearDown() {
        try {
            requestContext.invalidate();
            requestContext.deactivate();
        } finally {
            requestContext.dissociate(storage);
        }
    }

    @Test
    public void some_test_method() {
        assertNotNull(subject.getExecutorService());
    }
}
