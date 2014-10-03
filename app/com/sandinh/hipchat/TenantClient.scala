package com.sandinh.hipchat

import com.sandinh.hipchat.vo.{Tenant, Capabilities}
import com.sandinh.hipchat.vo.enums.Scope._
import com.sandinh.hipchat.vo.request._
import com.sandinh.hipchat.vo.response.Token
import play.api.cache.Cache
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Success
import play.api.Play.current

object TenantClient {
  //The Cache object should provide a `narrow` method :D
  @inline private def cacheKey(tenantId: String) = "tenant." + tenantId

  def apply(tenant: Tenant): TenantClient = Cache.getOrElse[TenantClient](cacheKey(tenant.id))(
    new TenantClient(tenant, Capabilities.clientCapabilities.scopes)
  )

  def fromCache(tenantId: String): Option[TenantClient] = Cache.getAs[TenantClient](cacheKey(tenantId))

  def delCache(tenantId: String) = Cache.remove(cacheKey(tenantId))
}

class TenantClient(tenant: Tenant, scopes: Set[Scope]) {
  require(scopes.size > 0)

  private val client = new RestClient(tenant.linkApi)

  private val cacheKey = "token." + scopes.mkString("|")

  /** get token for all scope of this tenant */
  def getToken: Future[String] = {
    Cache.getAs[Token](cacheKey)
      .map(Future.successful)
      .getOrElse { RestClient
        .generateToken(tenant.linkToken, tenant.id, tenant.secret, scopes)
        .andThen { case Success(auth) => Cache.set(cacheKey, auth, auth.expires_in - 60) }
    }.map(_.access_token)
  }

  def getToken(scope: Scope): Future[String] = {
    assert(scopes.contains(scope))
    getToken
  }

  /** @param expand some of [items] */
  def getEmoticons(opt: => GetEmoticons = new GetEmoticons(), expand: String = null) =
    getToken(view_group) flatMap client.getEmoticons(opt, expand)

  def getEmoticon(idOrKey: String) = getToken(view_group) flatMap client.getEmoticon(idOrKey)

  @inline final def deleteSession(token: String) = client.deleteSession(token)

  /** @param expand some of [owner,client.room] */
  @inline final def getSession(token: String, expand: String = null) = client.getSession(token, expand)

  /** @param expand some of [message.from,message.mentions] */
  def getRoomMessage(idOrName: String, messageId: String,
                     timezone: String = "UTC", expand: String = null) =
    getToken(view_messages) flatMap client.getRoomMessage(idOrName, messageId, timezone, expand)

  def createRoom(room: CreateRoom) = getToken(manage_rooms) flatMap client.createRoom(room)

  /** @param expand some of [items] */
  def getRooms(opt: => GetRooms = new GetRooms(), expand: String = null) =
    getToken(view_group) flatMap client.getRooms(opt, expand)

  /** @param expand some of [items.from,items.mentions] */
  def getRecentRoomHistory(idOrName: String, opt: => GetRecentHistory = GetRecentHistory(), expand: String = null) =
    getToken(view_messages) flatMap client.getRecentRoomHistory(idOrName, opt, expand)

  def sendNotification(idOrName: String, notification: Notification) =
    getToken(send_notification) flatMap client.sendNotification(idOrName, notification)

  def updateRoom(room: UpdateRoom) = getToken(admin_room) flatMap client.updateRoom(room)

  /** @param expand some of statistics,participants,owner */
  def getRoom(idOrName: String, expand: String = null) = getToken(view_group) flatMap client.getRoom(idOrName, expand)

  def deleteRoom(idOrName: String) = getToken(manage_rooms) flatMap client.deleteRoom(idOrName)

  def createWebhook(roomIdOrName: String, webhook: CreateWebhook) =
    getToken(admin_room) flatMap client.createWebhook(roomIdOrName, webhook)

  /** @param expand some of [items] */
  def getWebhooks(roomIdOrName: String, paging: => Paging = new Paging(), expand: String = null) =
    getToken(admin_room) flatMap client.getWebhooks(roomIdOrName, paging, expand)

  def getRoomStatistics(roomIdOrName: String) = getToken(view_group) flatMap client.getRoomStatistics(roomIdOrName)

  def replyToMessage(roomIdOrName: String, reply: Reply) =
    getToken(send_message) flatMap client.replyToMessage(roomIdOrName, reply)

  /** @param expand some of [items] */
  def getRoomMembers(roomIdOrName: String, paging: => Paging = new Paging(), expand: String = null) =
    getToken(admin_room) flatMap client.getRoomMembers(roomIdOrName, paging, expand)

  def setRoomTopic(roomIdOrName: String, topic: String) =
    getToken(admin_room) flatMap client.setRoomTopic(roomIdOrName, topic)

  def shareLinkWithRoom(roomIdOrName: String, share: ShareLink) =
    getToken(send_message) flatMap client.shareLinkWithRoom(roomIdOrName, share)

  def addRoomMember(roomIdOrName: String, userIdOrEmail: String) =
    getToken(admin_room) flatMap client.addRoomMember(roomIdOrName, userIdOrEmail)

  def removeRoomMember(roomIdOrName: String, userIdOrEmail: String) =
    getToken(admin_room) flatMap client.removeRoomMember(roomIdOrName, userIdOrEmail)

  def deleteWebhook(roomIdOrName: String, webhookId: String) =
    getToken(admin_room) flatMap client.deleteWebhook(roomIdOrName, webhookId)

  /** @param expand some of [room,creator] */
  def getWebhook(roomIdOrName: String, webhookId: String, expand: String = null) =
    getToken(admin_room) flatMap client.getWebhook(roomIdOrName, webhookId, expand)

  /** @param expand some of [items.from,items.mentions] */
  def getRoomHistory(roomIdOrName: String, opt: GetRoomHistory, expand: String = null) =
    getToken(view_messages) flatMap client.getRoomHistory(roomIdOrName, opt, expand)

  /** @param expand some of [message.from,message.mentions] */
  def getPrivateChatMessage(userIdOrEmail: String, messageId: String,
                            timezone: String = "UTC", expand: String = null) =
    getToken(view_messages) flatMap client.getPrivateChatMessage(userIdOrEmail, messageId, timezone, expand)

  /** @param expand some of [items.from,items.mentions] */
  def getRecentPrivateChatHistory(userIdOrEmail: String, opt: => GetRecentHistory = GetRecentHistory(), expand: String = null) =
    getToken(view_messages) flatMap client.getRecentPrivateChatHistory(userIdOrEmail, opt, expand)

  /** @param photo Base64 encoded. Accepted image types are JPEG, PNG and GIF */
  def updateUserPhoto(userIdOrEmail: String, photo: String) =
    getToken(admin_group) flatMap client.updateUserPhoto(userIdOrEmail, photo)

  def deleteUserPhoto(userIdOrEmail: String) = getToken(admin_group) flatMap client.deleteUserPhoto(userIdOrEmail)

  def updateUser(userIdOrEmail: String, user: UpdateUser) =
    getToken(admin_group) flatMap client.updateUser(userIdOrEmail, user)

  def deleteUser(userIdOrEmail: String) = getToken(admin_group) flatMap client.deleteUser(userIdOrEmail)

  def getUser(userIdOrEmail: String) = getToken(view_group) flatMap client.getUser(userIdOrEmail)

  /** @param expand some of [items] */
  def getUsers(opt: GetUsers, expand: String = null) = getToken(view_group) flatMap client.getUsers(opt, expand)

  def createUser(user: CreateUser) = getToken(admin_group) flatMap client.createUser(user)

  def shareLinkWithUser(userIdOrEmail: String, share: ShareLink) =
    getToken(send_message) flatMap client.shareLinkWithUser(userIdOrEmail, share)
}
