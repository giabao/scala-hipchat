package com.sandinh.hipchat.vo.enums

import com.sandinh.util.EnumFormat

object Show extends Enumeration {
  type Show = Value
  val away, chat, dnd, xa = Value

  implicit val _format = EnumFormat.format(this)
}
