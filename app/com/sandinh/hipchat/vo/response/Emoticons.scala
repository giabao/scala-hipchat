package com.sandinh.hipchat.vo.response

import play.api.libs.json.{Json, JsArray}

case class Emoticons(items: JsArray,
                     startIndex: Int,
                     maxResults: Int,
                     links: MultiGetLinks) extends HasItems[EmoticonBrief, Emoticon]

object Emoticons {
  implicit val reads = Json.reads[Emoticons]
}
