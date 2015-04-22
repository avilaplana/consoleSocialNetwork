package com.test.util;

import org.joda.time.DateTime;

/**
 * Created by alvarovilaplanagarcia on 21/04/15.
 */
public class TimeProviderImpl implements TimeProvider {
    @Override
    public DateTime currentTime() {
        return DateTime.now();
    }
}
