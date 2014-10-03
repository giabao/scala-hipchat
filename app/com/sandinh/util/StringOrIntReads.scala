package com.sandinh.util

import play.api.data.validation.ValidationError
import play.api.libs.json._

object StringOrIntReads extends Reads[String] {
  def reads(json: JsValue) = json match {
    case JsString(s) => JsSuccess(s)
    case JsNumber(i) => JsSuccess(i.toInt.toString)
    case _           => JsError(Seq(JsPath() -> Seq(ValidationError("error.expected.jsstring|jsnumber"))))
  }

  val readsId = (__ \ "id").read[String](this)
}