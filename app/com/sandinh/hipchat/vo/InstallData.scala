package com.sandinh.hipchat.vo

import play.api.libs.json.Json

//@see Receiving Registration Information / Installation part at https://www.hipchat.com/docs/apiv2/addons
//note that capabilitiesUrl is of HipChat server, not of the addon
//ex: https://chat.sandinh.com/v2/capabilities
case class InstallData(capabilitiesUrl: String,
                       oauthId: String,
                       oauthSecret: String,
                       roomId: Option[String])

object InstallData {
  implicit val reader = Json.reads[InstallData]
}
