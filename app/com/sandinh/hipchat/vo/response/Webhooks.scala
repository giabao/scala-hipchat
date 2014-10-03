package com.sandinh.hipchat.vo.response

import play.api.libs.json.{Json, JsArray}

case class Webhooks(
  items:      JsArray,
  startIndex: Int,
  maxResults: Int,
  links:      MultiGetLinks
) extends HasItems[WebhookBrief, Webhook]
object Webhooks {
  implicit val reads = Json.reads[Webhooks]
}
