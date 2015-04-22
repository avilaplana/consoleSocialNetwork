package com.test.util;

import com.test.domain.Message;

import java.util.Comparator;

/**
 * Created by alvarovilaplanagarcia on 21/04/15.
 */
public class MessageComparator implements Comparator<Message> {
    @Override
    public int compare(Message m1, Message m2) {
        return m2.getCreated().compareTo(m1.getCreated());
    }
}
