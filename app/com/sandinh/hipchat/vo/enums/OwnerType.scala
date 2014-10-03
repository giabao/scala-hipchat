package com.sandinh.hipchat.vo.enums

import com.sandinh.util.EnumFormat

object OwnerType extends Enumeration {
  type OwnerType = Value
  val client, user = Value

  implicit val _format = EnumFormat.format(this)
}
