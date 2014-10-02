package com.sandinh.hipchat.vo.enums

import com.sandinh.util.EnumFormat

object Event extends Enumeration {
  type Event = Value
  val room_message, room_notification, room_exit, room_enter, room_topic_change = Value

  implicit val _format = EnumFormat.format(this)
}
