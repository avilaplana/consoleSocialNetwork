package com.test.util;

import com.test.domain.Message;
import junit.framework.TestCase;
import org.joda.time.DateTime;

/**
 * Created by alvarovilaplanagarcia on 21/04/15.
 */
public class MessageComparatorTest extends TestCase {

    public void testCompareShouldReturnTrueWhenTheOrderIsDescendant(){
        final MessageComparator messageComparator = new MessageComparator();
        final DateTime olderDate = new DateTime(2014,1,1,0,0);
        final DateTime newerDate = olderDate.plusHours(1);

        final Message oldMessage = new Message("message 1", olderDate);
        final Message newMessage = new Message("message 2", newerDate);
        assertTrue(messageComparator.compare(oldMessage, newMessage) == 1);
    }
}
