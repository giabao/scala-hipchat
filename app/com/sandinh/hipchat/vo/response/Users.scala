package com.sandinh.hipchat.vo.response

import play.api.libs.json.{Json, JsArray}

case class Users(
  items:      JsArray,
  startIndex: Int,
  maxResults: Int,
  links:      MultiGetLinks
) extends HasItems[UserBrief, User]
object Users {
  implicit val reads = Json.reads[Users]
}
