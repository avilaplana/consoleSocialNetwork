package com.test.util

import com.test.domain.Message
import org.joda.time.DateTime
import org.scalatest.{Matchers, WordSpecLike}

class MessageDecoratorSpec extends WordSpecLike with Matchers {

  val now = new DateTime(2014, 10, 10, 10, 0, 0)
  val messageDecorator = new MessageDecorator

  "decorate" should {

    "decorate a message sent 10 seconds ago with the suffic '(10 seconds ago)'" in {
      val mesageDecorated = messageDecorator.decorate(Message("Alvaro", "message ten seconds ago", now.minusSeconds(10)), now)
      mesageDecorated shouldBe "message ten seconds ago (10 seconds ago)"
    }

    "decorate a message sent 2 minutes ago with the suffic '(2 minutes ago)'" in {
      val mesageDecorated = messageDecorator.decorate(Message("Alvaro", "message two minutes ago", now.minusMinutes(2)), now)
      mesageDecorated shouldBe "message two minutes ago (2 minutes ago)"
    }

    "decorate a message sent 4 hours ago with the suffic '(4 hours ago)'" in {
      val mesageDecorated = messageDecorator.decorate(Message("Alvaro", "message four hours ago", now.minusHours(4)), now)
      mesageDecorated shouldBe "message four hours ago (4 hours ago)"
    }
  }


  "decorateWithUser" should {
    "decorate a message sent 10 seconds ago with the suffic '(10 seconds ago)' and with the user as prefix 'Alvaro -'" in {
      val mesageDecorated = messageDecorator.decorateWithUser(Message("Alvaro", "message ten seconds ago", now.minusSeconds(10)), now)
      mesageDecorated shouldBe "Alvaro - message ten seconds ago (10 seconds ago)"
    }
  }
}
