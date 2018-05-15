package fi.testee.examples.cucumber.business;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.ejb.Singleton;

import fi.testee.examples.cucumber.adapter.CryptoServiceAdapter;
import fi.testee.examples.cucumber.adapter.TwitterAdapter;
import fi.testee.examples.cucumber.dataaccess.MessageDao;
import fi.testee.examples.cucumber.model.Message;

@Singleton
public class MessageService {
    private final static String REGEX = "[A-Z]*\\/[A-Z]*";
    private final static Pattern REGEX_PATTERN = Pattern.compile(REGEX, Pattern.MULTILINE);
    @EJB
    private MessageDao messageDao;
    @EJB
    private TwitterAdapter twitterAdapter;
    @EJB
    private CryptoServiceAdapter cryptoServiceAdapter;

    public void addMessage(final Message message) {
        if (containsCryptoPair(message.getText())) {
            message.setText(enrichMessageWithCryptoPrice(message.getText()));
        }
        twitterAdapter.publishOnTwitter(message.getText());
        messageDao.persist(message);
    }

    public Set<Message> getAllMessages() {
        return messageDao.getAll();
    }

    private String enrichMessageWithCryptoPrice(final String text) {
        final Matcher matcher = REGEX_PATTERN.matcher(text);
        while (matcher.find()) {
            final String group = matcher.group(0);
            final float price = cryptoServiceAdapter.getPrice(group);
            text.replace(group, group + "-" + price);
        }
        return text;
    }

    private boolean containsCryptoPair(final String text) {
        return REGEX_PATTERN.matcher(text).find();
    }
}
