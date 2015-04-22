package com.test.domain;

import org.joda.time.DateTime;

import java.util.List;

public interface ProfileRepository {

    UserProfile post(final String user, final String message, final DateTime created);
    List<Message> reading(final String user);
    UserProfile follows(final String userFollows, final String userFollowed);
    List<Message> wall(final String user);

}
