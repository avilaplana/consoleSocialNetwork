package com.test.service;

import org.joda.time.DateTime;

import java.util.List;

public interface CommandService {

    List<String> command(final String commandLine, DateTime created);
}
