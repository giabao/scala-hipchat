package com.sandinh.hipchat.vo.response

import play.api.libs.json.Json

case class MultiGetLinks(self: String,
                         prev: Option[String],
                         next: Option[String])

object MultiGetLinks {
  implicit val reads = Json.reads[MultiGetLinks]
}
