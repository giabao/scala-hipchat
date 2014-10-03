package com.sandinh.hipchat.vo.response

import play.api.libs.json.Json

/** @note according to https://www.hipchat.com/docs/apiv2/method/get_room_statistics
  *       , RoomStatistics not has links field.
  *       But I believe that is a documentation error!
  *       @see com.sandinh.hipchat.GetRoomSpec
 */
case class RoomStatistics(messages_sent: Int,
                          last_active: String,
                          links: SelfLinks)
object RoomStatistics {
  implicit val reads = Json.reads[RoomStatistics]
}
