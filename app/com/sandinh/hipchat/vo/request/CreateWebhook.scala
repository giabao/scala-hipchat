package com.sandinh.hipchat.vo.request

import com.sandinh.hipchat.vo.enums.Event.{Event, room_message}
import com.sandinh.util.JsBuilder
import play.api.libs.json.Json

/** @param pattern The regular expression pattern to match against messages. Only applicable for message events */
class CreateWebhook(
  url:     String,
  event:   Event,
  pattern: String = null,
  name:    String = null
) extends ReqTransform {
  require(pattern == null || event != room_message)

  private[hipchat] def trans = _.withBody(
    Json.obj(
      "url" -> url,
      "event" -> event
    ) ++ (JsBuilder
        <+ ("pattern", pattern, null)
        <+> ("name", name, null))
  )
}
