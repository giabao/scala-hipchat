package com.sandinh.hipchat.vo.response

import play.api.libs.json.{Reads, JsArray, JsObject}

trait HasRoomOpt {
  def room: Option[JsObject]
  lazy val roomBrief = room.map(_.as[RoomBrief])
  /** @note only available if expand query */
  lazy val roomExpand = room.map(_.as[Room])
}

trait HasOwner {
  def owner: JsObject
  lazy val ownerBrief = owner.as[UserBrief]
  /** @note only available if expand query */
  lazy val ownerExpand = owner.as[User]
}

trait HasOwnerOpt {
  def owner: Option[JsObject]
  lazy val ownerBrief = owner.map(_.as[UserBrief])
  /** @note only available if expand query */
  lazy val ownerExpand = owner.map(_.as[User])
}

trait HasCreatorOpt {
  def creator: Option[JsObject]
  lazy val creatorBrief = creator.map(_.as[UserBrief])
  /** @note only available if expand query */
  lazy val creatorExpand = creator.map(_.as[User])
}

/** @tparam B Brief type
  * @tparam E Expanded type */
abstract class HasItems[B: Reads, E: Reads] {
  def items: JsArray
  lazy val itemsBrief = items.as[Seq[B]]
  /** @note only available if getEmoticons is called with `items` expand */
  lazy val itemsExpand = items.as[Seq[E]]
}

trait HasMentions {
  def mentions: JsArray

  lazy val mentionsBrief = mentions.as[Seq[UserBrief]]
  /** @note only available if expand query */
  lazy val mentionsExpand = mentions.as[Seq[User]]
}

trait HasFromOpt {
  def from: Option[JsObject]

  lazy val fromBrief = from.map(_.as[UserBrief])
  /** @note only available if expand query */
  lazy val fromExpand = from.map(_.as[User])
}

trait HasStatistics {
  def statistics: JsObject
  lazy val statisticsBrief = statistics.as[SelfLinksOnly]
  lazy val statisticsExpand = statistics.as[RoomStatistics]
}

trait HasParticipants {
  def participants: JsArray
  lazy val participantsBrief = participants.as[Seq[UserBrief]]
  lazy val participantsExpand = participants.as[Seq[User]]
}
