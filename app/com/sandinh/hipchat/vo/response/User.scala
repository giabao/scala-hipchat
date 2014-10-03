package com.sandinh.hipchat.vo.response

import com.sandinh.hipchat.vo.enums.Show.Show
import play.api.libs.json.Json

object User {
  case class Client(
    version: Option[String],
    `type`:  Option[String]
  )
  object Client { implicit val reads = Json.reads[Client] }

  case class Presence(
    status:    Option[String],
    idle:      Option[Int],
    show:      Option[Show],
    client:    Option[Client],
    is_online: Boolean
  )
  object Presence { implicit val reads = Json.reads[Presence] }

  implicit val reads = Json.reads[User]
}
case class User(
  xmpp_jid:       String,
  is_deleted:     Boolean,
  name:           String,
  last_active:    Option[String],
  title:          String,
  presence:       Option[User.Presence],
  created:        String,
  id:             Int,
  mention_name:   String,
  is_group_admin: Boolean,
  timezone:       String,
  is_guest:       Boolean,
  email:          Option[String],
  photo_url:      Option[String]
)

case class UserBrief(
  mention_name: String,
  id:           Int,
  links:        SelfLinks,
  name:         String
)
object UserBrief { implicit val reads = Json.reads[UserBrief] }
