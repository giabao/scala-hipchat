package com.sandinh.hipchat.vo.response

import com.sandinh.hipchat.vo.enums.OwnerType.OwnerType
import com.sandinh.hipchat.vo.enums.Scope._
import play.api.libs.json.{Json, JsObject}

object Session {
  /** @param allowed_scopes List of possible scopes that are allowed for requested tokens */
  case class Client(
    id:             String,
    room:           Option[JsObject],
    allowed_scopes: Option[Set[Scope]],
    name:           Option[String]
  ) extends HasRoomOpt
  object Client { implicit val reads = Json.reads[Client] }

  implicit val reads = Json.reads[Session]
}

/** @param owner The user owner of the session, if owner_type is 'user' */
case class Session(
  scopes:       Set[Scope],
  access_token: String,
  expires_in:   Int,
  owner_type:   OwnerType,
  owner:        Option[JsObject],
  client:       Option[Session.Client]
) extends HasOwnerOpt
