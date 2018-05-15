package steps;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.EJB;
import javax.inject.Inject;

import org.jboss.weld.context.bound.BoundRequestContext;

import cucumber.api.DataTable;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import fi.testee.examples.cucumber.dataaccess.MessageDao;
import fi.testee.examples.cucumber.facade.MessageFacade;
import fi.testee.examples.cucumber.model.Message;

public class MessageSteps {
    @EJB
    private MessageDao messageDao;
    @EJB
    private MessageFacade messageFacade;

    @Inject
    private BoundRequestContext requestContext;
    private HashMap<String, Object> storage;

    @Before
    public void setUp() {
        storage = new HashMap<>();
        requestContext.associate(storage);
        requestContext.activate();
    }

    @After
    public void tearDown() {
        try {
            requestContext.invalidate();
            requestContext.deactivate();
        } finally {
            requestContext.dissociate(storage);
        }
    }

    @Given("^the message \"([^\"]*)\" already exists$")
    public void messageAlreadyExists(final String messageText) {
        messageDao.persist(new Message(1, messageText));
    }

    @When("^I add the message \"([^\"]*)\"")
    public void addMessage(final String messageText) {
        messageFacade.addMessage(messageText);
    }

    @Then("^the following messages should be available$")
    public void checkMessage(final DataTable table) {
        final HashSet<String> expected = new HashSet<>(table.asList(String.class));
        final Set<String> actual = messageFacade.getMessages();
        assertThat(actual, is(equalTo(expected)));
    }
}
