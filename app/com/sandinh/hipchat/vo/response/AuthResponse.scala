package com.sandinh.hipchat.vo.response

import play.api.libs.json.Json

/** @see https://www.hipchat.com/docs/apiv2/method/generate_token */
case class AuthResponse(access_token: String,
                        expires_in: Int,
                        group_name: String,
                        token_type: String,
                        scope: String,
                        group_id: Int)

object AuthResponse {
  implicit val reads = Json.reads[AuthResponse]
}