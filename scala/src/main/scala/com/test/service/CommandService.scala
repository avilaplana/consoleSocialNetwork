package com.test.service

import com.test.domain.ProfileRepository
import com.test.util.MessageDecorator
import org.joda.time.DateTime

trait Command {
  def command(commandLine: String, created: DateTime): List[String]
}

class CommandService(profileRepository: ProfileRepository, messageDecorator: MessageDecorator) extends Command {

  override def command(cline: String, created: DateTime): List[String] = {
    (cline.contains("->"), cline.contains("wall"), cline.contains("follows")) match {
      case (true, _, _) =>
        val cl = cline.split("->")
        profileRepository.post(cl(0).trim, cl(1).trim, created)
        List.empty[String]

      case (_, true, _) =>
        val cl = cline.split("wall")
        profileRepository.wall(cl(0).trim()).map(messageDecorator.decorateWithUser(_, created))

      case (_, _, true) =>
        val cl = cline.split("follows")
        profileRepository.follows(cl(0).trim(), cl(1).trim())
        List.empty[String]

      case (_, _, _) =>
        profileRepository.reading(cline.trim()).map(messageDecorator.decorate(_, created))
    }
  }
}