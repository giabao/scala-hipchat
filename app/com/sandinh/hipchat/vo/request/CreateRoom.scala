package com.sandinh.hipchat.vo.request

import com.sandinh.hipchat.vo.enums.Privacy.{Privacy, Public}
import com.sandinh.util.JsBuilder
import play.api.libs.json.Json

/** @param ownerId The id of the room's owner
  * @param ownerEmail The email address, or mention name (beginning with an '@') of the room's owner */
class CreateRoom(name: String,
                 topic: String = null,
                 guestAccess: Boolean = false,
                 privacy: Privacy = Public,
                 ownerId: Int = -1,
                 ownerEmail: String = null) extends IdOrEmail(ownerId, ownerEmail) with ReqTransform {
  private[hipchat] def trans = _.withBody(
    Json.obj(
      "name" -> name,
      "topic" -> topic,
      //TODO according to https://www.hipchat.com/docs/apiv2/method/create_room
      //=> This field is: "Defaults to the current user"
      //But in https://bitbucket.org/atlassianlabs/ac-node-hipchat/src/master/lib/rest-client.js
      //=> owner_user_id: verify.defined
      "owner_user_id" -> idJs
    ) ++ (JsBuilder
      <+ ("guest_access", guestAccess, false)
      <+> ("privacy", privacy, Public))
  )
}
