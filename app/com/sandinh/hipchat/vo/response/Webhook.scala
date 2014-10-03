package com.sandinh.hipchat.vo.response

import com.sandinh.hipchat.vo.enums.Event.Event
import play.api.libs.json.{JsObject, Json}

/** @param room The room, if specified, that this webhook is restricted to. Will be null for global extensions */
case class Webhook(url: String,
                   pattern: Option[String],
                   event: Event,
                   links: SelfLinks,
                   name: Option[String],
                   room: Option[JsObject],
                   creator: Option[JsObject],
                   created: String) extends HasRoomOpt with HasCreatorOpt
object Webhook {
  implicit val reads = Json.reads[Webhook]
}

case class WebhookBrief(url: String,
                        pattern: Option[String],
                        event: Event,
                        links: SelfLinks,
                        name: Option[String])
object WebhookBrief {
  implicit val reads = Json.reads[WebhookBrief]
}
