package com.sandinh.hipchat.vo.response

import com.sandinh.hipchat.vo.enums.Color._
import com.sandinh.hipchat.vo.enums.MessageFormat.MessageFormat
import com.sandinh.util.EnumFormat
import play.api.libs.json._
import play.api.libs.functional.syntax._

case class MessageWrapper(message: Option[Message])
object MessageWrapper {
  implicit val reads = Json.reads[MessageWrapper]
}

sealed trait Message
object Message {
  implicit val reads: Reads[Message] = MessageFmt1.reads | MessageFmt2.reads.asInstanceOf[Reads[Message]]
}

object MessageLinks {
  case class TwitterUser(
    followers:       Int,
    name:            String,
    profileImageUrl: String,
    screenName:      String
  )
  object TwitterUser { implicit val reads = Json.reads[TwitterUser] }

  /** @param name The name of the Twitter user who posted the update
   *  @param created The date of the post in UTC, formatted in ISO-8601 */
  case class TwitterStatus(
    name:            Option[String],
    created:         Option[String],
    text:            String,
    profileImageUrl: Option[String],
    source:          Option[String],
    screenName:      Option[String]
  )
  object TwitterStatus { implicit val reads = Json.reads[TwitterStatus] }

  /** FIXME I doubt that an Image can have both url & name be None ?
   *  @see https://www.hipchat.com/docs/apiv2/method/get_room_message
   *  @param image The URL of the image
   *  @param name A name for the image */
  case class Image(image: Option[String], name: Option[String])
  object Image { implicit val reads = Json.reads[Image] }

  case class Video(
    thumbnailUrl: Option[String],
    views:        Option[Int],
    author:       Option[String],
    title:        Option[String]
  )
  object Video { implicit val reads = Json.reads[Video] }

  case class Link(
    description: Option[String],
    title:       Option[String],
    headerText:  Option[String],
    linkText:    Option[String],
    faviconUrl:  Option[String],
    fullUrl:     Option[String]
  )
  object Link { implicit val reads = Json.reads[Link] }

  object Type extends Enumeration {
    val image, video, link, twitter_status, twitter_user = Value

    implicit val _format = EnumFormat.format(this)
  }

  implicit val reads = Json.reads[MessageLinks]
}

case class MessageLinks(
  url:            String,
  twitter_user:   Option[MessageLinks.TwitterUser],
  twitter_status: Option[MessageLinks.TwitterStatus],
  image:          Option[MessageLinks.Image],
  video:          Option[MessageLinks.Video],
  link:           Option[MessageLinks.Link],
  `type`:         MessageLinks.Type.Value
)

/** @param message_links if json response omit `message_links` field then MessageFmt1.message_links wil be empty Seq
 *  @param mentions if json response omit `mentions` field then MessageFmt1.message_links wil be empty JsArray */
case class MessageFmt1(
  from:          Option[JsObject],
  message_links: Seq[MessageLinks],
  file:          Option[MessageFmt1.File],
  date:          String,
  mentions:      JsArray,
  message:       String,
  id:            String
) extends HasMentions with HasFromOpt with Message

object MessageFmt1 {
  case class File(
    url:       String,
    thumb_url: Option[String],
    name:      String,
    size:      Int
  )
  object File { implicit val reads = Json.reads[File] }

  //we can't simply use Json.reads[MessageFmt1]
  //because we want to map `null` or omit `message_links`|`mentions` to an empty value
  implicit val reads = (
    (__ \ 'from).readNullable[JsObject] and
    (__ \ 'message_links).readNullable[Seq[MessageLinks]].map(_.getOrElse(Seq.empty[MessageLinks])) and
    (__ \ 'file).readNullable[MessageFmt1.File] and
    (__ \ 'date).read[String] and
    (__ \ 'mentions).readNullable[JsArray].map(_.getOrElse(JsArray())) and
    (__ \ 'message).read[String] and
    (__ \ 'id).read[String]
  )(MessageFmt1.apply _)
}

object MessageFmt2 {
  implicit val reads = Json.reads[MessageFmt2]
}

/** @param from The add-on name that sent the message.
 *  TODO ask:
 *    why message_format is required but color is not?
 *    What is the meaning of: "default = 'yellow'" in the response?
 *    @see https://www.hipchat.com/docs/apiv2/method/get_room_message */
case class MessageFmt2(
  from:           Option[String],
  message_format: MessageFormat,
  color:          Color,
  date:           String,
  mentions:       JsArray,
  message:        String,
  id:             String
) extends HasMentions with Message
