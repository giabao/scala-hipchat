package com.sandinh.hipchat.vo.request

import com.sandinh.hipchat.vo.request.ReqTransform.{qs, timezoneQs}

/** use for getRecentRoomHistory & getRecentPrivateChatHistory */
case class GetRecentHistory(maxResults: Int = 75,
                            timezone: String = "UTC",
                            notBefore: String = null) extends ReqTransform {
  private[hipchat] def trans =
    timezoneQs(timezone).andThen(
    qs("max-results", maxResults, 75)).andThen(
    qs("not-before", notBefore, null))
}
