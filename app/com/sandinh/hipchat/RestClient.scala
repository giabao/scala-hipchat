package com.sandinh.hipchat

import com.sandinh.hipchat.vo.request._
import ReqTransform.{Trans, idenTrans, timezoneQs}
import com.sandinh.hipchat.vo.enums.Scope
import Scope.Scope
import com.sandinh.hipchat.vo.response.{GetRoomResponse, CreateRoomResponse, AuthResponse}
import play.api.Logger
import play.api.Play.current
import play.api.http.HeaderNames.CONTENT_TYPE
import play.api.http.MimeTypes.FORM
import play.api.libs.json.{Json, JsValue}
import play.api.libs.ws.{WS, WSAuthScheme, WSResponse}
import play.utils.UriEncoding
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/** @see https://bitbucket.org/atlassianlabs/ac-node-hipchat/src/master/lib/rest-client.js */
object RestClient {
  private[this] def req(url: String, method: String, expectStatus: Int,
                        token: String = null,
                        trans: Trans = idenTrans): Future[WSResponse] = {
    var h = WS.url(url).withMethod(method)
    if (token != null) h = h.withHeaders("Authorization" -> s"Bearer $token")
    h = trans(h)
    Logger.debug(s">>>>${h.method} ${h.url}\n${h.auth}\n${h.headers}\n${h.queryString}\n${h.body}\n--\n")
    h.execute() filter { res =>
      Logger.debug(s"<<<<${res.status}\n${res.allHeaders}\n${res.body}\n--\n")
      res.status == expectStatus
    }
  }

  /** get always expect status 200 & return Json */
  private def get(url: String, token: String, trans: Trans = idenTrans): Future[JsValue] =
    req(url, "GET", 200, token, trans).map(_.json)

  private def post(url: String, token: String, expectStatus: Int, trans: Trans = idenTrans): Future[WSResponse] =
    req(url, "POST", expectStatus, token, trans)

  /** put always expect status 204 & return Bool */
  private def put(url: String, token: String, trans: Trans = idenTrans): Future[Boolean] =
    req(url, "PUT", 204, token, trans).map(_ => true)

  /** del always expect status 204 & return Bool */
  private def del(url: String, token: String): Future[Boolean] =
    req(url, "DELETE", 204, token).map(_ => true)

  @inline private def enc(uriSegment: String) = UriEncoding.encodePathSegment(uriSegment, "UTF-8")

  def getCapabilities(url: String): Future[JsValue] = get(url, null)

  def generateToken(linkToken: String, username: String, password: String, scopes: Set[Scope]): Future[AuthResponse] =
    post(linkToken, null, 200,
      _.withHeaders(CONTENT_TYPE -> FORM)
        .withAuth(username, password, WSAuthScheme.BASIC)
        .withQueryString(
          "grant_type" -> "client_credentials",
          "scope" -> scopes.mkString(" ")
        )
    ) map (_.json.as[AuthResponse])
}

class RestClient(apiUrl: String = "https://api.hipchat.com/v2") { import RestClient._
  def getEmoticons(opt: => GetEmoticons = new GetEmoticons())(token: String) = get(s"$apiUrl/emoticon", token, opt.trans)

  def getEmoticon(idOrKey: String)(token: String) = get(s"$apiUrl/emoticon/${enc(idOrKey)}", token)

  def deleteSession(token: String) = del(s"$apiUrl/oauth/token/${enc(token)}", token)

  def getSession(token: String) = get(s"$apiUrl/oauth/token/${enc(token)}", token)

  def getRoomMessage(idOrName: String, messageId: String, timezone: String = "UTC")(token: String) =
    get(s"$apiUrl/room/${enc(idOrName)}/history/${enc(messageId)}", token, timezoneQs(timezone))

  def createRoom(room: CreateRoom)(token: String) =
    post(s"$apiUrl/room", token, 201, room.trans).map(_.json.as[CreateRoomResponse])

  def getRooms(opt: => GetRooms = new GetRooms())(token: String) = get(s"$apiUrl/room", token, opt.trans)

  def getRecentRoomHistory(idOrName: String, opt: => GetRecentHistory = GetRecentHistory())(token: String) =
    get(s"$apiUrl/room/${enc(idOrName)}/history/latest", token, opt.trans)

  def sendNotification(idOrName: String, notification: Notification)(token: String) =
    post(s"$apiUrl/room/${enc(idOrName)}/notification", token, 204, notification.trans)

  def updateRoom(room: UpdateRoom)(token: String) = put(s"$apiUrl/room/${enc(room.name)}", token, room.trans)

  def getRoom(idOrName: String)(token: String) = get(s"$apiUrl/room/${enc(idOrName)}", token).map(_.as[GetRoomResponse])

  def deleteRoom(idOrName: String)(token: String) = del(s"$apiUrl/room/${enc(idOrName)}", token)

  def createWebhook(roomIdOrName: String, webhook: CreateWebhook)(token: String) =
    post(s"$apiUrl/room/${enc(roomIdOrName)}/webhook", token, 201, webhook.trans)

  def getWebhooks(roomIdOrName: String, paging: => Paging = new Paging())(token: String) =
    get(s"$apiUrl/room/${enc(roomIdOrName)}/webhook", token, paging.trans)

  def getRoomStatistics(roomIdOrName: String)(token: String) = get(s"$apiUrl/room/${enc(roomIdOrName)}/statistics", token)

  def replyToMessage(roomIdOrName: String, reply: Reply)(token: String): Future[Boolean] =
    post(s"$apiUrl/room/${enc(roomIdOrName)}/reply", token, 204, reply.trans).map(_ => true)

  def getRoomMembers(roomIdOrName: String, paging: => Paging = new Paging())(token: String) =
    get(s"$apiUrl/room/${enc(roomIdOrName)}/member", token, paging.trans)

  def setRoomTopic(roomIdOrName: String, topic: String)(token: String) =
    put(s"$apiUrl/room/${enc(roomIdOrName)}/topic", token, _.withBody(Json.obj("topic" -> topic)))

  def shareLinkWithRoom(roomIdOrName: String, share: ShareLink)(token: String): Future[Boolean] =
    post(s"$apiUrl/room/${enc(roomIdOrName)}/share/link", token, 204, share.trans).map(_ => true)

  def addRoomMember(roomIdOrName: String, userIdOrEmail: String)(token: String) =
    put(s"$apiUrl/room/${enc(roomIdOrName)}/member/${enc(userIdOrEmail)}", token)

  def removeRoomMember(roomIdOrName: String, userIdOrEmail: String)(token: String) =
    del(s"$apiUrl/room/${enc(roomIdOrName)}/member/${enc(userIdOrEmail)}", token)

  def deleteWebhook(roomIdOrName: String, webhookId: String)(token: String) =
    del(s"$apiUrl/room/${enc(roomIdOrName)}/webhook/${enc(webhookId)}", token)

  def getWebhook(roomIdOrName: String, webhookId: String)(token: String) =
    get(s"$apiUrl/room/${enc(roomIdOrName)}/webhook/${enc(webhookId)}", token)

  def getRoomHistory(roomIdOrName: String, opt: GetRoomHistory)(token: String) =
    get(s"$apiUrl/room/${enc(roomIdOrName)}/history", token, opt.trans)

  def getPrivateChatMessage(userIdOrEmail: String, messageId: String, timezone: String = "UTC")(token: String) =
    get(s"$apiUrl/user/${enc(userIdOrEmail)}/history/${enc(messageId)}", token, timezoneQs(timezone))

  def getRecentPrivateChatHistory(userIdOrEmail: String, opt: => GetRecentHistory = GetRecentHistory())(token: String) =
    get(s"$apiUrl/user/${enc(userIdOrEmail)}/history/latest", token, opt.trans)

  /** @param photo Base64 encoded. Accepted image types are JPEG, PNG and GIF */
  def updateUserPhoto(userIdOrEmail: String, photo: String)(token: String) =
    put(s"$apiUrl/user/${enc(userIdOrEmail)}/photo", token, _.withBody(Json.obj("photo" -> photo)))

  def deleteUserPhoto(userIdOrEmail: String)(token: String) =
    del(s"$apiUrl/user/${enc(userIdOrEmail)}/photo", token)

  def updateUser(userIdOrEmail: String, user: UpdateUser)(token: String) =
    put(s"$apiUrl/user/${enc(userIdOrEmail)}", token, user.trans)

  def deleteUser(userIdOrEmail: String)(token: String) = del(s"$apiUrl/user/${enc(userIdOrEmail)}", token)

  def getUser(userIdOrEmail: String)(token: String) = get(s"$apiUrl/user/${enc(userIdOrEmail)}", token)

  def getUsers(opt: GetUsers)(token: String) = get(s"$apiUrl/user", token, opt.trans)

  def createUser(user: CreateUser)(token: String) = post(s"$apiUrl/user", token, 201, user.trans)

  def shareLinkWithUser(userIdOrEmail: String, share: ShareLink)(token: String): Future[Boolean] =
    post(s"$apiUrl/user/${enc(userIdOrEmail)}/share/link", token, 204, share.trans).map(_ => true)
}
