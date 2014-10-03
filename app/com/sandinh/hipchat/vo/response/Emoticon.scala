package com.sandinh.hipchat.vo.response

import play.api.libs.json.Json

case class Emoticon(
  width:      Int,
  height:     Int,
  audio_path: Option[String],
  id:         String,
  shortcut:   String
)
object Emoticon { implicit val reads = Json.reads[Emoticon] }

case class EmoticonBrief(
  url:      String,
  links:    SelfLinks,
  id:       String,
  shortcut: String
)
object EmoticonBrief { implicit val reads = Json.reads[EmoticonBrief] }
