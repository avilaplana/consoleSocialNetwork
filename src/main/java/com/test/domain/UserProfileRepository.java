package com.test.domain;

import com.test.util.MessageComparator;
import com.test.util.MessageDecorator;

import java.util.*;

/**
 * Created by alvarovilaplanagarcia on 21/04/15.
 */
public class UserProfileRepository implements ProfileRepository {

    final Map<String, UserProfile> profiles = new HashMap<String, UserProfile>();
    final MessageDecorator messageDecorator;

    public UserProfileRepository(final MessageDecorator messageDecorator) {
        this.messageDecorator = messageDecorator;

    }
    @Override
    public UserProfile post(final String user, final Message message) {
        if (profiles.containsKey(user)) {
            profiles.get(user).addMessage(message);
        } else {
            final UserProfile userProfile = new UserProfile(user);
            userProfile.addMessage(message);
            profiles.put(user, userProfile);
        }
        return profiles.get(user).copy();
    }

    @Override
    public List<String> reading(final String user) {
        return decorateMessages(profiles.get(user).getMessages());
    }

    @Override
    public UserProfile follows(final String userFollows, final String userFollowed) {
        final UserProfile userProfile = profiles.get(userFollows);
        userProfile.addFollow(userFollowed);
        return userProfile;
    }

    @Override
    public List<String> wall(final String user) {
        final List<Message> followMessages = new LinkedList<Message>();
        followMessages.addAll(profiles.get(user).getMessages());
        for (String followed: profiles.get(user).getFollow()){
            followMessages.addAll(profiles.get(followed).getMessages());
        }
        Collections.sort(followMessages, new MessageComparator());
        return decorateMessages(followMessages);
    }

    private List<String> decorateMessages(List<Message> messages){
        final List <String> messagesToDecorate = new LinkedList<String>();
        for (Message message: messages){
            messagesToDecorate.add(messageDecorator.decorate(message));
        }
        return messagesToDecorate;
    }
}
