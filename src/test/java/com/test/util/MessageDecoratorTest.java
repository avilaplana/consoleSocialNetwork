package com.test.util;

import com.test.domain.Message;
import junit.framework.TestCase;
import org.joda.time.DateTime;

/**
 * Created by alvarovilaplanagarcia on 21/04/15.
 */
public class MessageDecoratorTest extends TestCase {

    final TimeProvider timeProvider = new TimeProvider() {
        @Override
        public DateTime currentTime() {
            return new DateTime(2014, 1, 1, 0, 0);
        }
    };

    final MessageDecorator messageDecorator = new MessageDecorator(timeProvider);

    public void testDecorateMessageFrom10SecondsAgohouldReturnMessageWithSuffix10SecondsAgo() {
        final Message messages10SecondsAgo = new Message("message ten seconds ago", timeProvider.currentTime().minusSeconds(10));
        assertTrue(messageDecorator.decorate(messages10SecondsAgo).equals("message ten seconds ago (10 seconds ago)"));
    }

    public void testDecorateMessageFrom2MinutesAgohouldReturnMessageWithSuffix2MinutesAgo() {
        final Message messages10SecondsAgo = new Message("message two minutes ago", timeProvider.currentTime().minusMinutes(2));
        assertTrue(messageDecorator.decorate(messages10SecondsAgo).equals("message two minutes ago (2 minutes ago)"));
    }

    public void testDecorateMessageFrom4HoursAgohouldReturnMessageWithSuffix4HoursAgo() {
        final Message messages10SecondsAgo = new Message("message four hours ago", timeProvider.currentTime().minusHours(4));
        assertTrue(messageDecorator.decorate(messages10SecondsAgo).equals("message four hours ago (4 hours ago)"));
    }
}
