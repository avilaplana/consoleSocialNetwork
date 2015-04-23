package com.test.domain

import org.joda.time.DateTime

case class Message(user: String, message: String, created: DateTime)

case class UserProfile(messages: List[Message] = List.empty[Message], follow: List[String] = List.empty[String]) {

  def addMessage(message: Message) = this.copy(messages = this.messages :+ message)

  def addFollow(user: String) = this.copy(follow = this.follow :+ user)

  def messagesOrdered(): List[Message] = messages.sortWith((m1, m2) => m1.created.isAfter(m2.created))
}

trait ProfileRepository {
  def post(user: String, message: String, created: DateTime): UserProfile

  def reading(user: String): List[Message]

  def follows(userFollows: String, userFollowed: String): Option[UserProfile]

  def wall(use: String): List[Message]
}

class UserProfileRepository extends ProfileRepository {
  val profiles = scala.collection.mutable.Map.empty[String, UserProfile]

  override def post(user: String, message: String, created: DateTime): UserProfile =
    profiles.get(user) match {
      case Some(up) =>
        val profileUpdated = up.addMessage(Message(user, message, created))
        profiles += (user -> profileUpdated)
        profileUpdated
      case None =>
        val newProfile = UserProfile(List(Message(user, message, created)))
        profiles += (user -> newProfile)
        newProfile
    }

  override def reading(user: String): List[Message] =
    profiles.get(user) match {
      case Some(up) => up.messagesOrdered()
      case None => List()
    }

  override def follows(userFollows: String, userFollowed: String): Option[UserProfile] =
    profiles.get(userFollows) match {
      case Some(up) =>
        val profileUpdated = up.addFollow(userFollowed)
        profiles += (userFollows -> profileUpdated)
        Some(profileUpdated)
      case None => None
    }

  override def wall(user: String): List[Message] =
    profiles.get(user) match {
      case Some(up) =>
        val followMessages: List[Message] = up.follow.flatMap(fu => profiles.get(fu).map(_.messagesOrdered())).flatten.toList
        val allMessages = up.messagesOrdered() ++ followMessages
        allMessages.sortWith((m1, m2) => m1.created.isAfter(m2.created))
      case None => List.empty[Message]
    }
}
