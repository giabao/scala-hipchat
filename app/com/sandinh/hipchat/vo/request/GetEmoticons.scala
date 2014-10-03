package com.sandinh.hipchat.vo.request

import com.sandinh.hipchat.vo.enums.EmoticonType.{EmoticonType, all}
import com.sandinh.hipchat.vo.request.ReqTransform.qs

class GetEmoticons(
  startIndex: Int          = 0,
  maxResults: Int          = 100,
  tpe:        EmoticonType = all
) extends Paging(startIndex, maxResults) {

  override private[hipchat] def trans = super.trans andThen qs("type", tpe, all)
}
