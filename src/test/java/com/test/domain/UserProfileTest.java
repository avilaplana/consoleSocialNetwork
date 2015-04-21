package com.test.domain;

import junit.framework.TestCase;
import org.joda.time.DateTime;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by alvarovilaplanagarcia on 21/04/15.
 */
public class UserProfileTest extends TestCase {

    final DateTime someTimeInPast = new DateTime(2014,1,1,0,0);

    public void testCopyShouldReturnImmutableCopye() {
        UserProfile userProfile =  new UserProfile("Alvaro");
        userProfile.addMessage(new Message("this is a message for immutability", someTimeInPast));

        UserProfile userProfileCopied = userProfile.copy();
        userProfileCopied.addMessage(new Message("this is a second message for immutability", someTimeInPast.plusHours(1)));

        assertFalse(userProfileCopied.equals(userProfile));
        assertFalse(userProfileCopied == userProfile);
    }

    public void testSortMessagesbyDateDesc() {
        UserProfile userProfile =  new UserProfile("Alvaro");
        userProfile.addMessage(new Message("this is a message for immutability - 1", someTimeInPast));
        userProfile.addMessage(new Message("this is a message for immutability - 2", someTimeInPast.plusHours(2)));
        userProfile.addMessage(new Message("this is a message for immutability - 3", someTimeInPast.plusHours(3)));

        final List<Message> messagesUnderTest = new LinkedList<Message>();
        messagesUnderTest.add(new Message("this is a message for immutability - 3", someTimeInPast.plusHours(3)));
        messagesUnderTest.add(new Message("this is a message for immutability - 2", someTimeInPast.plusHours(2)));
        messagesUnderTest.add(new Message("this is a message for immutability - 1", someTimeInPast));

        assertTrue(messagesUnderTest.equals(userProfile.getMessages()));
    }

}
