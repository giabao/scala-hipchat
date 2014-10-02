package com.sandinh.hipchat.vo.request

import com.sandinh.hipchat.vo.enums.Privacy.{Privacy, Public}
import play.api.libs.json._

/** Must pass ownerId != -1 OR ownerEmail != null */
class UpdateRoom(val name: String,
                 topic: String,
                 privacy: Privacy = Public,
                 isArchived: Boolean = false,
                 isGuestAccessible: Boolean = false,
                 /** The id of the room's owner */
                 ownerId: Int = -1,
                 /** The email address, or mention name (beginning with an '@') of the room's owner */
                 ownerEmail: String = null) extends IdOrEmail(ownerId, ownerEmail) with ReqTransform {
  require(name.length >= 1 && name.length <= 50)

  private[hipchat] def trans = _.withBody(
    Json.obj(
      "name" -> name,
      "topic" -> topic,
      "privacy" -> privacy.toString,
      "is_archived" -> isArchived,
      "is_guest_accessible" -> isGuestAccessible,
      "owner" -> Json.obj("id" -> idJs))
  )
}
