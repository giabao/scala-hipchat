package com.sandinh.hipchat.vo.response

import play.api.libs.json.Json

case class RoomStatistics(messages_sent: Int,
                          last_active: String)
object RoomStatistics {
  implicit val reads = Json.reads[RoomStatistics]
}
