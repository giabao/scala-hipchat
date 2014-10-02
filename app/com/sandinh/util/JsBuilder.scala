package com.sandinh.util

import play.api.libs.json.{JsObject, Writes, JsValue}
import scala.collection.mutable

/** @see com.sandinh.hipchat.vo.UpdateUser for usage */
object JsBuilder {
  def <+[A](k: String, v: A, ifNot: A)(implicit wrt: Writes[A]): JsBuilder = new JsBuilder <+ (k, v, ifNot)
}

final class JsBuilder private {
  private[this] val jsSeq = mutable.ListBuffer.empty[(String, JsValue)]
  def <+[A](k: String, v: A, ifNot: A)(implicit wrt: Writes[A]): this.type = {
    if (v != ifNot) jsSeq += k -> wrt.writes(v)
    this
  }

  def <+>[A](k: String, v: A, ifNot: A)(implicit wrt: Writes[A]): JsObject = <+(k, v, ifNot).result

  def result = JsObject(jsSeq)
}
