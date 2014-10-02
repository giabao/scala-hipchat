package com.sandinh.hipchat.vo.request

import com.sandinh.hipchat.vo.request.ReqTransform.qs

class Paging(startIndex: Int = 0,
             maxResults: Int = 100) extends ReqTransform {
  require(maxResults >= 0 && maxResults <= 1000)

  private[hipchat] def trans =
    qs("start-index", startIndex, 0).andThen(
    qs("max-results", maxResults, 100))
}
