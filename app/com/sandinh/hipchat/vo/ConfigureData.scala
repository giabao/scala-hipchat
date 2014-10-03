package com.sandinh.hipchat.vo

import play.api.libs.json.Json

/** @param iss The oauth client ID provided in installation
 *  @param iat When the token was issued, given in the number of milliseconds since epoch
 *  @param prn The currently-logged in user's ID
 *  @param context A map of extra context information, such as the user's preferred timezone */
case class ConfigureData(
  iss:     String,
  iat:     Long,
  prn:     Long,
  context: String
)

object ConfigureData { implicit val reader = Json.reads[ConfigureData] }
