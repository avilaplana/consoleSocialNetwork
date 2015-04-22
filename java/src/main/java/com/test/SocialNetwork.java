package com.test;

import com.test.domain.Message;
import com.test.domain.ProfileRepository;
import com.test.domain.UserProfile;
import com.test.domain.UserProfileRepository;
import com.test.service.CommandService;
import com.test.service.CommandServiceImpl;
import com.test.util.MessageDecorator;
import com.test.util.TimeProvider;
import com.test.util.TimeProviderImpl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class SocialNetwork {
    public static void main(String[] args) {

        final TimeProvider timeProvider = new TimeProviderImpl();
        final MessageDecorator messageDecorator = new MessageDecorator();
        final ProfileRepository profileRepository = new UserProfileRepository();
        final CommandService commandService = new CommandServiceImpl(profileRepository, messageDecorator);

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                System.out.println(">");
                String line = br.readLine();
                if (line.trim().equals("exit")) break;
                printMessages(commandService.command(line, timeProvider.currentTime()));
            } catch (IOException e) {
                System.out.println("Error in the app");
            }
        }
    }

    private static void printMessages(final List<String> messages) {
        for (String message : messages) {
            System.out.println(message);
        }
    }

}