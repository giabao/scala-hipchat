package com.sandinh.hipchat.vo.enums

object Scope extends Enumeration {
  type Scope = Value
  val admin_group, admin_room, manage_rooms, send_message, send_notification, view_group, view_messages = Value
}
