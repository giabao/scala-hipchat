package com.sandinh.hipchat.vo.request

import com.sandinh.hipchat.vo.request.ReqTransform.qs

class GetRooms(startIndex: Int = 0,
               maxResults: Int = 100,
               includeArchived: Boolean = false) extends Paging(startIndex, maxResults) {

  override private[hipchat] def trans = super.trans andThen qs("include-archived", includeArchived, false)
}
