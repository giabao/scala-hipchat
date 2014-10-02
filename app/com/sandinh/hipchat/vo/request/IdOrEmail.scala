package com.sandinh.hipchat.vo.request

import play.api.libs.json.{JsNumber, JsString, JsValue}

/** Must pass id != -1 OR email != null */
class IdOrEmail(id: Int = -1, email: String = null) {
  require(id != -1 || email != null)

  def idJs: JsValue = if (id != -1) JsNumber(id) else JsString(email)
}
