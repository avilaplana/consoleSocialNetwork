package com.test.util;

import com.test.domain.Message;

/**
 * Created by alvarovilaplanagarcia on 21/04/15.
 */
public class MessageDecorator {
    final TimeProvider timeProvider;

    public MessageDecorator(final TimeProvider timeProvider) {
        this.timeProvider = timeProvider;
    }

    public String decorate(Message message) {
        long seconds = (timeProvider.currentTime().getMillis() - message.getCreated().getMillis()) / 1000;
        if (seconds < 60) {
            return message.getMessage() + " (" + seconds + " seconds ago)";
        } else {
            final long minutes = seconds / 60;
            if (minutes < 60) {
                return message.getMessage() + " (" + minutes + " minutes ago)";
            } else {
                final long hours = minutes / 60;
                if (hours < 24) {
                    return message.getMessage() + " (" + hours + " hours ago)";
                }
            }
        }
        return message.getMessage();
    }
}
