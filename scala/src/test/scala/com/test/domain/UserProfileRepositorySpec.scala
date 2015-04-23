package com.test.domain

import com.test.util.TimeProvider
import org.joda.time.DateTime
import org.scalatest.{Matchers, WordSpecLike}

class UserProfileRepositorySpec extends WordSpecLike with Matchers {

  val now = new DateTime(2014, 10, 10, 10, 0, 0)
  val timeProvider = new TimeProvider {
    override def currentTime: DateTime = now
  }

  "post" should {
    "return UserProfile with only one message when user posted first message" in {
      val profileRepository = new UserProfileRepository
      val profileUnderTest = profileRepository.post("Alvaro", "this is a test", now.minusMinutes(30))
      profileUnderTest shouldBe UserProfile(List(Message("Alvaro", "this is a test", now.minusMinutes(30))))

    }

    "return UserProfile with two messages when user posted two messages" in {
      val profileRepository = new UserProfileRepository
      val firstMessageProfile = profileRepository.post("Alvaro", "this is the first message", now.minusHours(1))
      val secondMessageProfile = profileRepository.post("Alvaro", "this is the second message", now.minusMinutes(15))

      secondMessageProfile shouldBe UserProfile(List(
        Message("Alvaro", "this is the first message", now.minusHours(1)),
        Message("Alvaro", "this is the second message", now.minusMinutes(15))
      ))
    }
  }

  "reading" should {
    "return the time line of Alvaro with two messages ordered by time descendant" in {
      val profileRepository = new UserProfileRepository

      profileRepository.post("Alvaro2", "this is the first message to read the time line", now.minusHours(1))
      profileRepository.post("Alvaro2", "this is the second message to read the time line", now.minusSeconds(10))

      profileRepository.reading("Alvaro2") shouldBe List(
        Message("Alvaro2", "this is the second message to read the time line", now.minusSeconds(10)),
        Message("Alvaro2", "this is the first message to read the time line", now.minusHours(1))
      )
    }
  }

  "follows" should {
    "return an user Profile with the one user followed" in {
      val profileRepository = new UserProfileRepository
      profileRepository.post("Alvaro", "my first messsage", now.minusMinutes(30))
      val userProfileUnderTest = profileRepository.follows("Alvaro", "Charlie")
      userProfileUnderTest shouldBe Some(UserProfile(List(Message("Alvaro", "my first messsage", now.minusMinutes(30))), List("Charlie")))
    }

    "return an user Profile with the 2 user followed" in {
      val profileRepository = new UserProfileRepository
      profileRepository.post("Alvaro", "my first messsage", now.minusMinutes(30))
      profileRepository.follows("Alvaro", "Charlie")
      val userProfileUnderTest = profileRepository.follows("Alvaro", "John")
      userProfileUnderTest shouldBe Some(UserProfile(List(Message("Alvaro", "my first messsage", now.minusMinutes(30))), List("Charlie", "John")))
    }
  }

  "wall" should {
    "return  all the posts from Alvaro by time descendant" in {
      val profileRepository = new UserProfileRepository
      profileRepository.post("Alvaro", "my first messsage for the wall", now.minusHours(1))
      profileRepository.post("Alvaro", "my second messsage for the wall", now.minusMinutes(15))

      profileRepository.wall("Alvaro") shouldBe List(
        Message("Alvaro", "my second messsage for the wall", now.minusMinutes(15)),
        Message("Alvaro", "my first messsage for the wall", now.minusHours(1))
      )
    }

    "treturn all The post from alvaro and Charlie when Alvaro Follows Charlie  by time descendant" in {
      val profileRepository = new UserProfileRepository
      profileRepository.post("Alvaro", "my first messsage for the wall", now.minusHours(2))
      profileRepository.post("Alvaro", "my second messsage for the wall", now.minusHours(1))

      profileRepository.post("Charlie", "my firs messsage for the wall, I am Charlie", now.minusMinutes(3))
      profileRepository.follows("Alvaro", "Charlie")

      profileRepository.wall("Alvaro") shouldBe List(
        Message("Charlie", "my firs messsage for the wall, I am Charlie", now.minusMinutes(3)),
        Message("Alvaro", "my second messsage for the wall", now.minusHours(1)),
        Message("Alvaro", "my first messsage for the wall", now.minusHours(2))
      )
    }
  }
}
