package com.sandinh.hipchat.vo.enums

import com.sandinh.util.EnumFormat

object Scope extends Enumeration {
  type Scope = Value
  val admin_group, admin_room, manage_rooms, send_message, send_notification, view_group, view_messages = Value

  implicit val _format = EnumFormat.format(this)
}
