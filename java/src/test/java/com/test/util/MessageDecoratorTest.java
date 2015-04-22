package com.test.util;

import com.test.domain.Message;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MessageDecoratorTest {

    final DateTime now = new DateTime(2014, 1, 1, 0, 0); 
    final MessageDecorator messageDecorator = new MessageDecorator();

    @Test
    public void decorateMessageFrom10SecondsAgohouldReturnMessageWithSuffix10SecondsAgo() {
        final Message messages10SecondsAgo = new Message("Alvaro", "message ten seconds ago", now.minusSeconds(10));
        assertTrue(messageDecorator.decorate(messages10SecondsAgo, now).equals("message ten seconds ago (10 seconds ago)"));
    }

    @Test
    public void decorateMessageFrom2MinutesAgohouldReturnMessageWithSuffix2MinutesAgo() {
        final Message messages10SecondsAgo = new Message("Alvaro", "message two minutes ago", now.minusMinutes(2));
        assertTrue(messageDecorator.decorate(messages10SecondsAgo, now).equals("message two minutes ago (2 minutes ago)"));
    }

    @Test
    public void decorateMessageFrom4HoursAgohouldReturnMessageWithSuffix4HoursAgo() {
        final Message messages10SecondsAgo = new Message("Alvaro", "message four hours ago", now.minusHours(4));
        assertTrue(messageDecorator.decorate(messages10SecondsAgo, now).equals("message four hours ago (4 hours ago)"));
    }

    @Test
    public void decorateWithUserMessageFrom4HoursAgoshouldReturnMessageWithSuffix4HoursAgoAndPrefixUser() {
        final Message messages10SecondsAgo = new Message("Alvaro", "message four hours ago", now.minusHours(4));
        assertTrue(messageDecorator.decorateWithUser(messages10SecondsAgo, now).equals("Alvaro - message four hours ago (4 hours ago)"));
    }
}
