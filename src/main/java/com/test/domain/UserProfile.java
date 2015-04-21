package com.test.domain;

import com.test.util.MessageComparator;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by alvarovilaplanagarcia on 21/04/15.
 */
public class UserProfile {
    private final String user;
    private final List<Message> messages = new LinkedList<Message>();
    private final List<String> follow = new LinkedList<String>();

    public UserProfile(final String user) {
        this.user = user;
    }

    public List<Message> addMessage(final Message message) {
        messages.add(message);
        return messages;
    }

    public List<Message> getMessages() {
        Collections.sort(messages, new MessageComparator());
        return Collections.unmodifiableList(messages);
    }

    public List<String> addFollow(final String user) {
        follow.add(user);
        return follow;
    }

    public List<String> getFollow() {
        return Collections.unmodifiableList(follow);
    }

    public String getUser() {
        return user;
    }

    public UserProfile copy() {
        UserProfile userProfileCopied = new UserProfile(this.user);
        for (Message message : messages) {
            userProfileCopied.addMessage(message);
        }

        for (String user : follow) {
            userProfileCopied.addFollow(user);
        }
        return userProfileCopied;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserProfile)) return false;

        UserProfile that = (UserProfile) o;

        if (!follow.equals(that.follow)) return false;
        if (!messages.equals(that.messages)) return false;
        if (!user.equals(that.user)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + messages.hashCode();
        result = 31 * result + follow.hashCode();
        return result;
    }
}
