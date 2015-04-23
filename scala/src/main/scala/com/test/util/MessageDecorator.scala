package com.test.util

import com.test.domain.Message
import org.joda.time.DateTime

trait TimeProvider {
  def currentTime: DateTime
}

object TimeProvider extends TimeProvider {
  override def currentTime: DateTime = DateTime.now()
}

class MessageDecorator {
  def decorateWithUser(message: Message, currentTime: DateTime): String = s"${message.user} - ${decorate(message, currentTime)}"


  def decorate(m: Message, currentTime: DateTime): String = {
    val seconds = (currentTime.getMillis() - m.created.getMillis()) / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    (seconds, minutes, hours) match {
      case (s, _, _) if s < 60 => s"${m.message} ($seconds seconds ago)"
      case (_, min, _) if min < 60 => s"${m.message} ($minutes minutes ago)"
      case (_, _, h) if h < 24 => s"${m.message} ($hours hours ago)"
      case (_, _, _) => m.message

    }
  }

}
