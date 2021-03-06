package com.sandinh.hipchat.vo.enums

import com.sandinh.util.EnumFormat

object MessageFormat extends Enumeration {
  type MessageFormat = Value
  val html, text = Value

  implicit val _format = EnumFormat.format(this)
}
