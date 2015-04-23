package com.test.domain

import org.joda.time.DateTime
import org.scalatest.{Matchers, WordSpecLike}

class UserProfileSpec extends WordSpecLike with Matchers {

  val someTimeInPast = DateTime.now

  "messagesOrdered" should {
    "return the list of the messages ordered descendant in time" in {
      val userProfile = UserProfile(
        List(
          Message("Alvaro", "messages 1", someTimeInPast.minusHours(3)),
          Message("Alvaro", "messages 2", someTimeInPast.minusHours(2)),
          Message("Alvaro", "messages 3", someTimeInPast.minusHours(1))
        ))

      userProfile.messagesOrdered() shouldBe List(
        Message("Alvaro", "messages 3", someTimeInPast.minusHours(1)),
        Message("Alvaro", "messages 2", someTimeInPast.minusHours(2)),
        Message("Alvaro", "messages 1", someTimeInPast.minusHours(3)))
    }
  }

}
