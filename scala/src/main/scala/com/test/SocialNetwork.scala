package com.test

import java.io.{BufferedReader, InputStreamReader}

import com.test.domain.{Message, UserProfileRepository}
import com.test.service.CommandService
import com.test.util.{TimeProvider, MessageDecorator}


object SocialNetwork {
  def main(args: Array[String]): Unit = {
    val timeProvider = TimeProvider
    val messageDecorator = new MessageDecorator
    val profileRepository = new UserProfileRepository
    val command = new CommandService(profileRepository, messageDecorator)

    val br = new BufferedReader(new InputStreamReader(System.in))
    while (true) {
      println(">")
      val l = br.readLine()
      if (l.trim == "exit") return

      command.command(l, timeProvider.currentTime).map(println)
    }
  }

}