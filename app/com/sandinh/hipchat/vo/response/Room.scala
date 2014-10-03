package com.sandinh.hipchat.vo.response

import com.sandinh.hipchat.vo.enums.Privacy.Privacy
import play.api.libs.json._

object Room {
  /** @param members The URL to use to retrieve members for this room. Only available for private rooms */
  case class Links(self: String, webhooks: String, members: Option[String], participants: String)
  implicit val readsLinks = Json.reads[Links]
  implicit val reads = Json.reads[Room]
}

/** @param is_guest_accessible maybe null ??? */
case class Room(xmpp_jid: String,
                statistics: JsObject,
                name: String,
                links: Room.Links,
                created: String,
                is_archived: Boolean,
                privacy: Privacy,
                is_guest_accessible: Boolean,
                topic: String,
                participants: JsArray,
                owner: JsObject,
                id: Int,
                guest_access_url: Option[String]) extends HasOwner with HasStatistics with HasParticipants

case class RoomBrief(id: Int,
                     links: Room.Links,
                     name: String)

object RoomBrief {
  implicit val reads = Json.reads[RoomBrief]
}
