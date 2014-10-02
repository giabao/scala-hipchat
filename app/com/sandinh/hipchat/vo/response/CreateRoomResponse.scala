package com.sandinh.hipchat.vo.response

import com.sandinh.util.StringOrIntReads.readsId
import SelfLinks.readsLinks
import play.api.libs.functional.syntax._

object CreateRoomResponse {
  implicit val reads = (readsId and readsLinks)(CreateRoomResponse.apply _)
}

case class CreateRoomResponse(id: String, links: SelfLinks)
