package com.test

import com.test.domain.UserProfileRepository
import com.test.service.CommandService
import com.test.util.MessageDecorator
import org.joda.time.DateTime
import org.scalatest.{Matchers, WordSpecLike}

class IntegrationSpec extends WordSpecLike with Matchers {

  val now = new DateTime(2014, 10, 10, 1, 1)
  val messageDecorator = new MessageDecorator()
  val profileRepository = new UserProfileRepository()
  val commandService = new CommandService(profileRepository, messageDecorator);

  "running the command line" should {
    "execute the commands correctly" in {

      commandService.command("Alvaro -> good morning everybody", now.minusMinutes(45)) shouldBe List()

      commandService.command("Alvaro", now.minusMinutes(40)) shouldBe List("good morning everybody (5 minutes ago)")

      commandService.command("Alvaro -> going to work by train", now.minusMinutes(35)) shouldBe List()

      commandService.command("Alvaro", now.minusMinutes(30)) shouldBe List(
        "going to work by train (5 minutes ago)", "good morning everybody (15 minutes ago)")


      commandService.command("Charlie -> I just left the gym, good work out", now.minusMinutes(25)) shouldBe List()

      commandService.command("Charlie", now.minusMinutes(15)) shouldBe List("I just left the gym, good work out (10 minutes ago)")

      commandService.command("Charlie follows Alvaro", now.minusMinutes(10)) shouldBe List()

      commandService.command("Alvaro", now.minusMinutes(5)) shouldBe List(
        "going to work by train (30 minutes ago)", "good morning everybody (40 minutes ago)"
      )


      commandService.command("Alvaro wall", now.minusMinutes(4)) shouldBe List(
        "Alvaro - going to work by train (31 minutes ago)", "Alvaro - good morning everybody (41 minutes ago)")

      commandService.command("Charlie wall", now) shouldBe List(
        "Charlie - I just left the gym, good work out (25 minutes ago)",
        "Alvaro - going to work by train (35 minutes ago)",
        "Alvaro - good morning everybody (45 minutes ago)")
    }
  }
}
