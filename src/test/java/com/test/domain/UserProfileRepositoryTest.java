package com.test.domain;

import com.test.util.MessageDecorator;
import com.test.util.TimeProvider;
import junit.framework.TestCase;
import org.joda.time.DateTime;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by alvarovilaplanagarcia on 21/04/15.
 */
public class UserProfileRepositoryTest extends TestCase {

    final DateTime now = new DateTime(2014,1,1,0,0);
    final TimeProvider timeProvider = new TimeProvider() {
        @Override
        public DateTime currentTime() {
            return now;
        }
    };

    final MessageDecorator messageDecorator =  new MessageDecorator(timeProvider);

    public void testPostFirstMessageForAlvaroShouldReturnUserProfileWithOnlyOneMessage() {
        final ProfileRepository profileRepository = new UserProfileRepository(messageDecorator);
        final UserProfile firstMessageUserProfile = profileRepository.post("Alvaro", new Message("this is a test", now.minusMinutes(30)));

        final List<Message> messgesUnderTest = new LinkedList();
        messgesUnderTest.add(new Message("this is a test", now.minusMinutes(30)));

        assertTrue(firstMessageUserProfile.getUser().equals("Alvaro"));
        assertTrue(firstMessageUserProfile.getMessages().equals(messgesUnderTest));
    }

    public void testPostTwoMessagesForAlvaroShouldReturnUserProfileWithTwoMessages() {
        final ProfileRepository profileRepository = new UserProfileRepository(messageDecorator);

        final UserProfile firstMessageProfile = profileRepository.post("Alvaro", new Message("this is the first message", now.minusHours(1)));
        final UserProfile secondMessageProfile = profileRepository.post("Alvaro", new Message("this is the second message", now.minusMinutes(15)));
        assertFalse(firstMessageProfile.equals(secondMessageProfile));

        final List<Message> messgesUnderTest = new LinkedList();
        messgesUnderTest.add(new Message("this is the second message", now.minusMinutes(15)));
        messgesUnderTest.add(new Message("this is the first message", now.minusHours(1)));

        assertTrue(secondMessageProfile.getMessages().equals(messgesUnderTest));

    }

    public void testReadingAlvaroTimeLineWithTwoMessagesShouldReturnTwoMessages() {
        final ProfileRepository profileRepository = new UserProfileRepository(messageDecorator);
        profileRepository.post("Alvaro2", new Message("this is the first message to read the time line", now.minusHours(1)));
        profileRepository.post("Alvaro2", new Message("this is the second message to read the time line", now.minusSeconds(10)));

        final List<String> messgesUnderTest = new LinkedList();
        messgesUnderTest.add("this is the second message to read the time line (10 seconds ago)");
        messgesUnderTest.add("this is the first message to read the time line (1 hours ago)");

        assertTrue(profileRepository.reading("Alvaro2").equals(messgesUnderTest));
    }

    public void testAlvaroFollowsCharlieShouldReturnCharlieAsFollowed() {
        final ProfileRepository profileRepository = new UserProfileRepository(messageDecorator);
        profileRepository.post("Alvaro", new Message("my first messsage", now.minusMinutes(30)));

        final UserProfile userProfileUnderTest = profileRepository.follows("Alvaro", "Charlie");

        final List<String> followersUnderTest = new LinkedList();
        followersUnderTest.add("Charlie");

        assertTrue(userProfileUnderTest.getFollow().equals(followersUnderTest));
    }

    public void testAlvaroWallShouldReturnAllThePostFromAlvaro(){
        final ProfileRepository profileRepository = new UserProfileRepository(messageDecorator);
        profileRepository.post("Alvaro", new Message("my first messsage for the wall", now.minusHours(1)));
        profileRepository.post("Alvaro", new Message("my second messsage for the wall", now.minusMinutes(15)));

        final List<String> messgesUnderTest = new LinkedList();
        messgesUnderTest.add("my second messsage for the wall (15 minutes ago)");
        messgesUnderTest.add("my first messsage for the wall (1 hours ago)");

        assertTrue(profileRepository.wall("Alvaro").equals(messgesUnderTest));
    }

    public void testAlvaroWallShouldReturnAllThePostFromAlvaroAndCharlieWhenAlvaroFollowsCharlieOrderedByTimeDesc(){
        final ProfileRepository profileRepository = new UserProfileRepository(messageDecorator);
        profileRepository.post("Alvaro", new Message("my first messsage for the wall", now.minusHours(2)));
        profileRepository.post("Alvaro", new Message("my second messsage for the wall", now.minusHours(1)));

        profileRepository.post("Charlie", new Message("my firs messsage for the wall - Charlie", now.minusMinutes(3)));
        profileRepository.follows("Alvaro", "Charlie");

        final List<String> messgesUnderTest = new LinkedList();
        messgesUnderTest.add("my firs messsage for the wall - Charlie (3 minutes ago)");
        messgesUnderTest.add("my second messsage for the wall (1 hours ago)");
        messgesUnderTest.add("my first messsage for the wall (2 hours ago)");

        assertTrue(profileRepository.wall("Alvaro").equals(messgesUnderTest));
    }
}
