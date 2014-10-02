package com.sandinh.hipchat.vo.response

import com.sandinh.hipchat.vo.enums.Privacy.Privacy
import play.api.libs.json._

object GetRoomResponse {
  case class Statistics(links: SelfLinks)
  case class Links(self: String, webhooks: String, members: Option[String], participants: String)
  case class Owner(mention_name: String, id: Int, links: SelfLinks, name: String)

  implicit val readsStatistics = Json.reads[Statistics]
  implicit val readsLinks = Json.reads[Links]
  implicit val readsOwner = Json.reads[Owner]
  implicit val reads = Json.reads[GetRoomResponse]
}

/** @param is_guest_accessible maybe null */
case class GetRoomResponse(xmpp_jid: String,
                           statistics: GetRoomResponse.Statistics,
                           name: String,
                           links: GetRoomResponse.Links,
                           created: String,
                           is_archived: Boolean,
                           privacy: Privacy,
                           is_guest_accessible: Boolean,
                           topic: String,
                           //participants: Deprecated: Use the participants link
                           owner: GetRoomResponse.Owner,
                           id: Int,
                           guest_access_url: String)
