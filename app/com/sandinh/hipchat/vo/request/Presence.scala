package com.sandinh.hipchat.vo.request

import com.sandinh.hipchat.vo.enums.Show.Show
import play.api.libs.json.Json

case class Presence(status: String, show: Show) {
  require(status.length >= 0 && status.length <= 50)
}

object Presence {
  implicit val wrt = Json.writes[Presence]
}
