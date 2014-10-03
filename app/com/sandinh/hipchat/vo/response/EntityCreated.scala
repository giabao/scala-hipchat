package com.sandinh.hipchat.vo.response

import com.sandinh.util.StringOrIntReads.readsId
import SelfLinks.readsLinks
import play.api.libs.functional.syntax._

case class EntityCreated(
  id:    String,
  links: SelfLinks
)

object EntityCreated {
  implicit val reads = (readsId and readsLinks)(EntityCreated.apply _)
}
