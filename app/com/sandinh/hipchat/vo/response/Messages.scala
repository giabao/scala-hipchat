package com.sandinh.hipchat.vo.response

import play.api.libs.json.Json

case class Messages(
  items:      Seq[Message],
  startIndex: Int,
  maxResults: Int,
  links:      MultiGetLinks
)

object Messages {
  implicit val reads = Json.reads[Messages]
}