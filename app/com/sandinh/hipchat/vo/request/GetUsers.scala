package com.sandinh.hipchat.vo.request

import com.sandinh.hipchat.vo.request.ReqTransform.qs

class GetUsers(
  startIndex:     Int     = 0,
  maxResults:     Int     = 100,
  includeGuests:  Boolean = false,
  includeDeleted: Boolean = false
) extends Paging(startIndex, maxResults) {
  override private[hipchat] def trans = super.trans.andThen(
    qs("include-guests", includeGuests, false)
  ).andThen(
      qs("include-deleted", includeDeleted, false)
    )
}
