package com.test.domain;

import org.joda.time.DateTime;

import java.util.Date;

/**
 * Created by alvarovilaplanagarcia on 21/04/15.
 */
public class Message {

    private final String message;
    private final DateTime created;

    public Message(final String message, final DateTime created){
        this.message = message;
        this.created = created;
    }

    public String getMessage() {
        return message;
    }

    public DateTime getCreated() {
        return created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;

        Message message1 = (Message) o;

        if (!created.equals(message1.created)) return false;
        if (!message.equals(message1.message)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = message.hashCode();
        result = 31 * result + created.hashCode();
        return result;
    }
}
