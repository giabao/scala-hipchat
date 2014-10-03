package com.sandinh.hipchat.vo.response

import play.api.libs.json.{Json, JsArray}

case class Rooms(items: JsArray,
                 startIndex: Int,
                 maxResults: Int,
                 links: MultiGetLinks) extends HasItems[RoomBrief, Room]
object Rooms {
  implicit val reads = Json.reads[Rooms]
}
