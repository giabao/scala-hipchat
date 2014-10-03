package com.sandinh.hipchat.vo.request

import com.sandinh.hipchat.vo.request.ReqTransform.Trans
import play.api.libs.ws.WSRequestHolder

trait ReqTransform {
  private[hipchat] def trans: Trans
}

object ReqTransform {
  private[hipchat]type Trans = WSRequestHolder => WSRequestHolder
  private[hipchat] val idenTrans: Trans = h => h

  /** withQueryString Trans maker */
  def qs[A](k: String, v: A, default: A): Trans = h =>
    if (v == default) h else h.withQueryString(k -> v.toString)

  def timezoneQs(timezone: String) = qs("timezone", timezone, "UTC")

  def expandQs(expand: String) = qs("expand", expand, null)
}
