package com.sandinh.hipchat.vo.response

import play.api.libs.json._

case class SelfLinks(self: String)

object SelfLinks {
  implicit val reads = Json.reads[SelfLinks]
  val readsLinks = (__ \ "links").read[SelfLinks]
}
