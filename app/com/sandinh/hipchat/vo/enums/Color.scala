package com.sandinh.hipchat.vo.enums

import com.sandinh.util.EnumFormat

object Color extends Enumeration {
  type Color = Value
  val yellow, green, red, purple, gray, random = Value

  implicit val _format = EnumFormat.format(this)
}
