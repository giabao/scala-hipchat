package com.sandinh.hipchat.vo.request

import play.api.libs.json.Json

case class Reply(parentMessageId: String, message: String) extends ReqTransform {
  require(message.length >= 1 && message.length <= 10000)

  private[hipchat] def trans = _.withBody(Json.toJson(this)(Reply.writes))
}

object Reply { implicit val writes = Json.writes[Reply] }
