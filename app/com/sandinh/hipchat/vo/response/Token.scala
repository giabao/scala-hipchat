package com.sandinh.hipchat.vo.response

import play.api.libs.json.Json

/** @see https://www.hipchat.com/docs/apiv2/method/generate_token */
case class Token(
  access_token: String,
  expires_in:   Int,
  group_name:   String,
  token_type:   String,
  scope:        String,
  group_id:     Int
)

object Token {
  implicit val reads = Json.reads[Token]
}