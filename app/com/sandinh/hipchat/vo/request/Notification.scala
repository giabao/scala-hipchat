package com.sandinh.hipchat.vo.request

import com.sandinh.hipchat.vo.enums.Color.{Color, yellow}
import com.sandinh.hipchat.vo.enums.MessageFormat.{MessageFormat, html}
import play.api.libs.json.Json

class Notification(message: String,
                   color: Color = yellow,
                   message_format: MessageFormat = html,
                   //java Object has notify method already
                   $notify: Boolean = false) extends ReqTransform {

  require(message.length >= 1 && message.length <= 10000)

  private[hipchat] def trans = _.withBody(Json.obj(
    "color" -> color.toString,
    "message" -> message,
    "notify" -> $notify,
    "message_format" -> message_format.toString
  ))
}
