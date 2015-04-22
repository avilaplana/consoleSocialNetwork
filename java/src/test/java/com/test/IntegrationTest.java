package com.test;

import com.test.domain.ProfileRepository;
import com.test.domain.UserProfileRepository;
import com.test.service.CommandService;
import com.test.service.CommandServiceImpl;
import com.test.util.MessageDecorator;
import org.joda.time.DateTime;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class IntegrationTest {

    final DateTime now = new DateTime(2014, 10, 10, 0, 0);
    final MessageDecorator messageDecorator = new MessageDecorator();
    final ProfileRepository profileRepository = new UserProfileRepository();
    final CommandService commandService = new CommandServiceImpl(profileRepository, messageDecorator);

    @Test
    public void simulateCommandLine() {

        final List<String> m1 = commandService.command("Alvaro -> good morning everybody", now.minusMinutes(45));
        assertTrue(m1.isEmpty());

        final List<String> m2 = commandService.command("Alvaro", now.minusMinutes(40));
        assertTrue(m2.size() == 1);
        assertTrue(m2.get(0).equals("good morning everybody (5 minutes ago)"));

        final List<String> m3 = commandService.command("Alvaro -> going to work by train", now.minusMinutes(35));
        assertTrue(m3.isEmpty());

        final List<String> m4 = commandService.command("Alvaro", now.minusMinutes(30));
        assertTrue(m4.size() == 2);
        assertTrue(m4.get(0).equals("going to work by train (5 minutes ago)"));
        assertTrue(m4.get(1).equals("good morning everybody (15 minutes ago)"));

        final List<String> m5 = commandService.command("Charlie -> I just left the gym, good work out", now.minusMinutes(25));
        assertTrue(m5.isEmpty());

        final List<String> m6 = commandService.command("Charlie", now.minusMinutes(15));
        assertTrue(m6.size() == 1);
        assertTrue(m6.get(0).equals("I just left the gym, good work out (10 minutes ago)"));

        final List<String> m7 = commandService.command("Charlie follows Alvaro", now.minusMinutes(10));
        assertTrue(m7.isEmpty());

        final List<String> m8 = commandService.command("Alvaro", now.minusMinutes(5));
        assertTrue(m8.size() == 2);
        assertTrue(m8.get(0).equals("going to work by train (30 minutes ago)"));
        assertTrue(m8.get(1).equals("good morning everybody (40 minutes ago)"));

        final List<String> m9 = commandService.command("Alvaro wall", now.minusMinutes(4));
        assertTrue(m9.size() == 2);
        assertTrue(m9.get(0).equals("Alvaro - going to work by train (31 minutes ago)"));
        assertTrue(m9.get(1).equals("Alvaro - good morning everybody (41 minutes ago)"));

        final List<String> m10 = commandService.command("Charlie wall", now);
        assertTrue(m10.size() == 3);
        assertTrue(m10.get(0).equals("Charlie - I just left the gym, good work out (25 minutes ago)"));
        assertTrue(m10.get(1).equals("Alvaro - going to work by train (35 minutes ago)"));
        assertTrue(m10.get(2).equals("Alvaro - good morning everybody (45 minutes ago)"));
    }
}
