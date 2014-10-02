package com.sandinh.hipchat.vo.request

import com.sandinh.util.JsBuilder
import play.api.libs.json.Json

class ShareLink(link: String, message: String = null) extends ReqTransform {
  require(link.length >= 10 && link.length <= 1000)
  require(message == null || message.length <= 1000)

  private[hipchat] def trans = _.withBody(
    Json.obj(
      "link" -> link
    ) ++ JsBuilder.<+(
      "message", message, null
    ).result
  )
}
