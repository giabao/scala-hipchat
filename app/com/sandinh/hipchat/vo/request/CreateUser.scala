package com.sandinh.hipchat.vo.request

import com.sandinh.util.JsBuilder
import play.api.libs.json.Json

class CreateUser(
  name:           String,
  email:          String,
  title:          String  = null,
  mention_name:   String  = null,
  is_group_admin: Boolean = false,
  timezone:       String  = "UTC",
  password:       String  = null
) extends ReqTransform {
  require(name.length >= 1 && name.length <= 50)

  private[hipchat] def trans = _.withBody(
    Json.obj(
      "name" -> name,
      "email" -> email
    ) ++ (JsBuilder
        <+ ("title", title, null)
        <+ ("mention_name", mention_name, null)
        <+ ("is_group_admin", is_group_admin, false)
        <+ ("timezone", timezone, "UTC")
        <+> ("password", password, null))
  )
}
