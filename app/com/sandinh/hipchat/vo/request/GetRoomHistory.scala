package com.sandinh.hipchat.vo.request

import com.sandinh.hipchat.vo.request.ReqTransform.{qs, timezoneQs}

/** @param startIndex, maxResults - Only valid with a non-recent data query */
class GetRoomHistory(
  date:       String  = "recent",
  timezone:   String  = "UTC",
  startIndex: Int     = 0,
  maxResults: Int     = 100,
  reverse:    Boolean = true
) extends Paging(startIndex, maxResults) {
  require(date != "recent" || startIndex == 0 && maxResults == 100)

  override private[hipchat] def trans = super.trans.andThen(
    timezoneQs(timezone)
  ).andThen(
      qs("date", date, "recent")
    ).andThen(
        qs("reverse", reverse, true)
      )
}
