package com.test.domain;

import org.joda.time.DateTime;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserProfileRepositoryTest {

    final DateTime now = new DateTime(2014,1,1,0,0);

    @Test
    public void postFirstMessageForAlvaroShouldReturnUserProfileWithOnlyOneMessage() {
        final ProfileRepository profileRepository = new UserProfileRepository();
        final UserProfile firstMessageUserProfile = profileRepository.post("Alvaro", "this is a test", now.minusMinutes(30));

        final List<Message> messgesUnderTest = new LinkedList();
        messgesUnderTest.add(new Message("Alvaro", "this is a test", now.minusMinutes(30)));

        assertTrue(firstMessageUserProfile.getMessages().equals(messgesUnderTest));
    }

    @Test
    public void postTwoMessagesForAlvaroShouldReturnUserProfileWithTwoMessages() {
        final ProfileRepository profileRepository = new UserProfileRepository();

        final UserProfile firstMessageProfile = profileRepository.post("Alvaro", "this is the first message", now.minusHours(1));
        final UserProfile secondMessageProfile = profileRepository.post("Alvaro", "this is the second message", now.minusMinutes(15));
        assertFalse(firstMessageProfile.equals(secondMessageProfile));

        final List<Message> messgesUnderTest = new LinkedList();
        messgesUnderTest.add(new Message("Alvaro", "this is the second message", now.minusMinutes(15)));
        messgesUnderTest.add(new Message("Alvaro", "this is the first message", now.minusHours(1)));

        assertTrue(secondMessageProfile.getMessages().equals(messgesUnderTest));

    }

    @Test
    public void readingAlvaroTimeLineWithTwoMessagesShouldReturnTwoMessages() {
        final ProfileRepository profileRepository = new UserProfileRepository();
        profileRepository.post("Alvaro2", "this is the first message to read the time line", now.minusHours(1));
        profileRepository.post("Alvaro2", "this is the second message to read the time line", now.minusSeconds(10));

        final List<Message> messgesUnderTest = new LinkedList();
        messgesUnderTest.add(new Message("Alvaro2", "this is the second message to read the time line", now.minusSeconds(10)));
        messgesUnderTest.add(new Message("Alvaro2", "this is the first message to read the time line", now.minusHours(1)));

        assertTrue(profileRepository.reading("Alvaro2").equals(messgesUnderTest));
    }

    @Test
    public void alvaroFollowsCharlieShouldReturnCharlieAsFollowed() {
        final ProfileRepository profileRepository = new UserProfileRepository();
        profileRepository.post("Alvaro", "my first messsage", now.minusMinutes(30));

        final UserProfile userProfileUnderTest = profileRepository.follows("Alvaro", "Charlie");

        final List<String> followersUnderTest = new LinkedList();
        followersUnderTest.add("Charlie");

        assertTrue(userProfileUnderTest.getFollow().equals(followersUnderTest));
    }

    @Test
    public void alvaroWallShouldReturnAllThePostFromAlvaro(){
        final ProfileRepository profileRepository = new UserProfileRepository();
        profileRepository.post("Alvaro", "my first messsage for the wall", now.minusHours(1));
        profileRepository.post("Alvaro", "my second messsage for the wall", now.minusMinutes(15));

        final List<Message> messgesUnderTest = new LinkedList();
        messgesUnderTest.add(new Message("Alvaro", "my second messsage for the wall", now.minusMinutes(15)));
        messgesUnderTest.add(new Message("Alvaro", "my first messsage for the wall", now.minusHours(1)));

        assertTrue(profileRepository.wall("Alvaro").equals(messgesUnderTest));
    }

    @Test
    public void alvaroWallShouldReturnAllThePostFromAlvaroAndCharlieWhenAlvaroFollowsCharlieOrderedByTimeDesc(){
        final ProfileRepository profileRepository = new UserProfileRepository();

        profileRepository.post("Alvaro", "my first messsage for the wall", now.minusHours(2));
        profileRepository.post("Alvaro", "my second messsage for the wall", now.minusHours(1));
        profileRepository.post("Charlie", "my first messsage for the wall - Charlie", now.minusMinutes(3));
        profileRepository.follows("Alvaro", "Charlie");

        final List<Message> messgesUnderTest = new LinkedList();
        messgesUnderTest.add(new Message("Charlie", "my first messsage for the wall - Charlie", now.minusMinutes(3)));
        messgesUnderTest.add(new Message("Alvaro", "my second messsage for the wall", now.minusHours(1)));
        messgesUnderTest.add(new Message("Alvaro", "my first messsage for the wall", now.minusHours(2)));

        assertTrue(profileRepository.wall("Alvaro").equals(messgesUnderTest));
    }
}
