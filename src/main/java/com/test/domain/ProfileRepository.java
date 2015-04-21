package com.test.domain;

import java.util.List;

/**
 * Created by alvarovilaplanagarcia on 21/04/15.
 */
public interface ProfileRepository {

    UserProfile post(final String user, final Message message);
    List<String> reading(final String user);
    UserProfile follows(final String userFollows, final String userFollowed);
    List<String> wall(final String user);

}
