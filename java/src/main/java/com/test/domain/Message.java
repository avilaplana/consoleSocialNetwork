package com.test.domain;

import org.joda.time.DateTime;

import java.util.Date;

public class Message {

    private final String message;
    private final String user;
    private final DateTime created;

    public Message(final String user, final String message, final DateTime created){
        this.message = message;
        this.created = created;
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public DateTime getCreated() {
        return created;
    }

    public String getUser() {
        return user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Message message1 = (Message) o;

        if (!created.equals(message1.created)) return false;
        if (!message.equals(message1.message)) return false;
        if (!user.equals(message1.user)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = message.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + created.hashCode();
        return result;
    }
}
