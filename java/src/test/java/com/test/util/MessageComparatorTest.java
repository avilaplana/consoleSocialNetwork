package com.test.util;

import com.test.domain.Message;
import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertTrue;


public class MessageComparatorTest {

    @Test
    public void compareShouldReturnTrueWhenTheOrderIsDescendant(){
        final MessageComparator messageComparator = new MessageComparator();
        final DateTime olderDate = new DateTime(2014,1,1,0,0);
        final DateTime newerDate = olderDate.plusHours(1);

        final Message oldMessage = new Message("Alvaro", "message 1", olderDate);
        final Message newMessage = new Message("Alvaro", "message 2", newerDate);
        assertTrue(messageComparator.compare(oldMessage, newMessage) == 1);
    }
}
