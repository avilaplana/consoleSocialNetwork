package com.test.service

import com.test.domain.{ProfileRepository, Message, UserProfile}
import com.test.util.MessageDecorator
import org.joda.time.DateTime
import org.mockito.Mockito._
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, WordSpecLike}

class CommandServiceSpec extends WordSpecLike with Matchers with MockitoSugar {

  val now = new DateTime(2014, 10, 10, 1, 1)
  val profileRepository = mock[ProfileRepository]
  val messageDecorator = mock[MessageDecorator]
  val commandService = new CommandService(profileRepository, messageDecorator)

  "command" should {
    "post a message from Alvaro" in {

      val userProfile = UserProfile().addMessage(Message("Alvaro", "this is a message for testing", now.minusMinutes(1)))
      when(profileRepository.post("Alvaro", "this is a message for testing", now.minusMinutes(1))).thenReturn(userProfile)
      val commands = commandService.command("Alvaro -> this is a message for testing", now.minusMinutes(1))
      commands.isEmpty shouldBe (true)

      verify(profileRepository, times(1)).post("Alvaro", "this is a message for testing", now.minusMinutes(1))
    }

    "read messages from alvaro" in {
      val message = Message("Alvaro", "this is a message for testing", now.minusMinutes(40))
      val messages = List(message)

      when(profileRepository.reading("Alvaro")).thenReturn(messages)
      when(messageDecorator.decorate(message, now)).thenReturn("this is a message for testing (40 minutes ago)")

      commandService.command("Alvaro", now) shouldBe (List("this is a message for testing (40 minutes ago)"))

      verify(profileRepository, times(1)).reading("Alvaro")
      verify(messageDecorator, times(1)).decorate(message, now)
    }

    "Alvaro follows Charlie" in {
      val userProfile = new UserProfile().
        addMessage(new Message("Alvaro", "this is a message for testing", now.minusHours(1))).
        addFollow("Charlie")

      when(profileRepository.follows("Alvaro", "Charlie")).thenReturn(Some(userProfile))

      commandService.command("Alvaro follows Charlie", now) shouldBe (List())

      verify(profileRepository, times(1)).follows("Alvaro", "Charlie")
    }

    "shows Alvaro's wall" in {
      val message = Message("Alvaro", "this is a message for testing", now.minusHours(2))
      val messages = List(message)

      when(profileRepository.wall("Alvaro")).thenReturn(messages)
      when(messageDecorator.decorateWithUser(message, now)).
        thenReturn("Alvaro - this is a message for testing (2 hours ago)")
      commandService.command("Alvaro wall", now) shouldBe List("Alvaro - this is a message for testing (2 hours ago)")

      verify(profileRepository, times(1)).wall("Alvaro")
      verify(messageDecorator, times(1)).decorateWithUser(message, now)
    }
  }
}
