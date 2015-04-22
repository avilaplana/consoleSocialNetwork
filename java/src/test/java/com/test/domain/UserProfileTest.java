package com.test.domain;

import org.joda.time.DateTime;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserProfileTest {

    final DateTime someTimeInPast = new DateTime(2014,1,1,0,0);

    @Test
    public void copyShouldReturnImmutableInstance() {
        UserProfile userProfile =  new UserProfile();
        userProfile.addMessage(new Message("Alvaro", "this is a message for immutability", someTimeInPast));

        UserProfile userProfileCopied = userProfile.copy();
        userProfileCopied.addMessage(new Message("Alvaro", "this is a second message for immutability", someTimeInPast.plusHours(1)));

        assertFalse(userProfileCopied.equals(userProfile));
        assertFalse(userProfileCopied == userProfile);
    }

    @Test
    public void sortMessagesbyDateDesc() {
        UserProfile userProfile =  new UserProfile();
        userProfile.addMessage(new Message("Alvaro", "this is a message for immutability - 1", someTimeInPast));
        userProfile.addMessage(new Message("Alvaro", "this is a message for immutability - 2", someTimeInPast.plusHours(2)));
        userProfile.addMessage(new Message("Alvaro", "this is a message for immutability - 3", someTimeInPast.plusHours(3)));

        final List<Message> messagesUnderTest = new LinkedList<Message>();
        messagesUnderTest.add(new Message("Alvaro", "this is a message for immutability - 3", someTimeInPast.plusHours(3)));
        messagesUnderTest.add(new Message("Alvaro", "this is a message for immutability - 2", someTimeInPast.plusHours(2)));
        messagesUnderTest.add(new Message("Alvaro", "this is a message for immutability - 1", someTimeInPast));

        assertTrue(messagesUnderTest.equals(userProfile.getMessages()));
    }

}
