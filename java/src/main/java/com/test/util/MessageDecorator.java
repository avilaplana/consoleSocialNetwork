package com.test.util;

import com.test.domain.Message;
import org.joda.time.DateTime;

/**
 * Created by alvarovilaplanagarcia on 21/04/15.
 */
public class MessageDecorator {

    public String decorateWithUser(Message message, DateTime currentDate) {
        return message.getUser() + " - " + decorate(message, currentDate);
    }

    public String decorate(Message message, DateTime currentDate) {
        long seconds = (currentDate.getMillis() - message.getCreated().getMillis()) / 1000;
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
