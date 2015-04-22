package com.test.domain;

import com.test.util.MessageComparator;
import com.test.util.MessageDecorator;
import org.joda.time.DateTime;

import java.util.*;

public class UserProfileRepository implements ProfileRepository {

    final Map<String, UserProfile> profiles = new HashMap<String, UserProfile>();

    @Override
    public UserProfile post(final String user, final String message, final DateTime created) {
        if (profiles.containsKey(user)) {
            profiles.get(user).addMessage(new Message(user, message, created));
        } else {
            final UserProfile userProfile = new UserProfile();
            userProfile.addMessage(new Message(user, message, created));
            profiles.put(user, userProfile);
        }
        return profiles.get(user).copy();
    }

    @Override
    public List<Message> reading(final String user) {
        return profiles.get(user).getMessages();
    }

    @Override
    public UserProfile follows(final String userFollows, final String userFollowed) {
        final UserProfile userProfile = profiles.get(userFollows);
        userProfile.addFollow(userFollowed);
        return userProfile;
    }

    @Override
    public List<Message> wall(final String user) {
        final List<Message> followMessages = new LinkedList<Message>();
        followMessages.addAll(profiles.get(user).getMessages());
        for (String followed: profiles.get(user).getFollow()){
            followMessages.addAll(profiles.get(followed).getMessages());
        }
        Collections.sort(followMessages, new MessageComparator());
        return followMessages;
    }
}
