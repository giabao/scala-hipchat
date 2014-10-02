package com.sandinh.hipchat.vo.enums

import com.sandinh.util.EnumFormat

object Privacy extends Enumeration {
  type Privacy = Value
  val Public = Value("public")
  val Private = Value("private")

  implicit val _format = EnumFormat.format(this)
}
