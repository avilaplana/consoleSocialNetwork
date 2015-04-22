package com.test.service;

import com.test.domain.Message;
import com.test.domain.ProfileRepository;
import com.test.util.MessageDecorator;
import org.joda.time.DateTime;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommandServiceImpl implements CommandService {

    final Pattern follows = Pattern.compile("(.*)follows(.*)");
    final Pattern wall = Pattern.compile("(.*)wall");
    final Pattern post = Pattern.compile("(.*)->(.*)");


    final ProfileRepository profileRepository;
    final MessageDecorator messageDecorator;

    public CommandServiceImpl(final ProfileRepository profileRepository, final MessageDecorator messageDecorator) {
        this.profileRepository = profileRepository;
        this.messageDecorator = messageDecorator;
    }


    @Override
    public List<String> command(String commandLine, DateTime created) {

        final Matcher f = follows.matcher(commandLine);
        if (f.find()) {
            return follows(f.group(1).trim(), f.group(2).trim());
        }

        final Matcher w = wall.matcher(commandLine);
        if (w.find()) {
            return wall(w.group(1).trim(), created);
        }

        final Matcher p = post.matcher(commandLine);
        if (p.find()) {
            return post(p.group(1).trim(), p.group(2).trim(), created);
        }

        return reading(commandLine.trim(), created);
    }


    private List<String> follows(String userFrom, String userTo) {
        profileRepository.follows(userFrom, userTo);
        return Collections.EMPTY_LIST;
    }


    private List<String> wall(String user,  DateTime created) {
        final List<Message> messages = profileRepository.wall(user);
        final List<String> messagesDecorated = new LinkedList<String>();
        for (Message messageToDecorate : messages) {
            messagesDecorated.add(messageDecorator.decorateWithUser(messageToDecorate, created));
        }
        return messagesDecorated;
    }

    private List<String> post(String user, String message, DateTime created) {
        profileRepository.post(user, message, created);
        return Collections.EMPTY_LIST;
    }

    private List<String> reading(String user, DateTime created) {
        final List<Message> messages = profileRepository.reading(user);
        final List<String> messagesDecorated = new LinkedList<String>();
        for (Message messageToDecorate : messages) {
            messagesDecorated.add(messageDecorator.decorate(messageToDecorate, created));
        }
        return messagesDecorated;
    }


}
