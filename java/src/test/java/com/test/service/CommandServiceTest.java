package com.test.service;

import com.test.domain.Message;
import com.test.domain.ProfileRepository;
import com.test.domain.UserProfile;
import com.test.util.MessageDecorator;
import org.joda.time.DateTime;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class CommandServiceTest {

    final DateTime now = new DateTime(2014, 10, 10, 1, 1);
    final ProfileRepository profileRepository = Mockito.mock(ProfileRepository.class);
    final MessageDecorator messageDecorator = Mockito.mock(MessageDecorator.class);
    final CommandService commandService = new CommandServiceImpl(profileRepository, messageDecorator);

    @Test
    public void commandShouldPostAMessageFromAlvaro() {
        final UserProfile userProfile = new UserProfile();
        userProfile.addMessage(new Message("Alvaro", "this is a message for testing", now.minusMinutes(1)));
        Mockito.when(profileRepository.post("Alvaro", "this is a message for testing", now.minusMinutes(1))).thenReturn(userProfile);

        List<String> result = commandService.command("Alvaro -> this is a message for testing", now.minusMinutes(1));
        assertTrue(result.isEmpty());

        Mockito.verify(profileRepository, Mockito.times(1)).post("Alvaro", "this is a message for testing", now.minusMinutes(1));
        Mockito.verify(messageDecorator, Mockito.never()).
                decorate(new Message("Alvaro", "this is a message for testing", now.minusMinutes(1)), now);

    }

    @Test
    public void commandShouldReadMessagesFromAlvaro() {
        final Message message = new Message("Alvaro", "this is a message for testing", now.minusMinutes(40));
        final List<Message> messages = new LinkedList<Message>();
        messages.add(message);
        Mockito.when(profileRepository.reading("Alvaro")).thenReturn(messages);
        Mockito.when(messageDecorator.decorate(message, now)).
                thenReturn("this is a message for testing (40 minutes ago)");

        List<String> result = commandService.command("Alvaro", now);
        assertTrue(result.size() == 1);
        assertTrue(result.get(0).equals("this is a message for testing (40 minutes ago)"));

        Mockito.verify(profileRepository, Mockito.times(1)).reading("Alvaro");
        Mockito.verify(messageDecorator, Mockito.times(1)).decorate(message, now);

    }

    @Test
    public void commandShouldAlvaroFollowsCharlie() {
        final UserProfile userProfile = new UserProfile();
        userProfile.addMessage(new Message("Alvaro", "this is a message for testing", now.minusHours(1)));
        userProfile.addFollow("Charlie");

        Mockito.when(profileRepository.follows("Alvaro", "Charlie")).thenReturn(userProfile);

        List<String> result = commandService.command("Alvaro follows Charlie", now);
        assertTrue(result.isEmpty());

        Mockito.verify(profileRepository, Mockito.times(1)).follows("Alvaro", "Charlie");
        Mockito.verify(messageDecorator, Mockito.never()).
                decorate(new Message("Alvaro", "this is a message for testing", now.minusHours(1)), now);
    }

    @Test
    public void commandShouldShowsAlvarosWall() {
        final Message message = new Message("Alvaro", "this is a message for testing", now.minusHours(2));
        final List<Message> messages = new LinkedList<Message>();
        messages.add(message);

        Mockito.when(profileRepository.wall("Alvaro")).thenReturn(messages);
        Mockito.when(messageDecorator.decorateWithUser(message, now)).
                thenReturn("Alvaro - this is a message for testing (2 hours ago)");

        List<String> result = commandService.command("Alvaro wall", now);

        assertTrue(result.size() == 1);
        assertTrue(result.get(0).equals("Alvaro - this is a message for testing (2 hours ago)"));

        Mockito.verify(profileRepository, Mockito.times(1)).wall("Alvaro");
        Mockito.verify(messageDecorator, Mockito.times(1)).decorateWithUser(message, now);

    }


}
