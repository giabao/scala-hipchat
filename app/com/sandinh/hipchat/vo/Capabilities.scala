package com.sandinh.hipchat.vo

import java.nio.charset.Charset
import com.google.common.io.Resources
import com.sandinh.hipchat.RestClient
import com.sandinh.hipchat.vo.enums.Scope
import com.sandinh.hipchat.vo.enums.Scope._
import play.api.Application
import play.api.libs.json.{Json, JsValue}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class Capabilities(json: JsValue) {
  def scopes: Set[Scope] = (json \ "capabilities" \ "hipchatApiConsumer" \ "scopes").as[Seq[String]].map(Scope.withName).toSet

  def linkApi = (json \ "links" \ "api").as[String]
  def linkToken = (json \ "capabilities" \ "oauth2Provider" \ "tokenUrl").as[String]
}

object Capabilities {
  def serverCapabilities(capabilitiesUrl: String): Future[Capabilities] =
    RestClient.getCapabilities(capabilitiesUrl).map(new Capabilities(_))

  def clientCapabilities(implicit app: Application) = new Capabilities(
    Json.parse(Resources.toString(app.resource("capabilities.json").get, Charset forName "UTF-8"))
  )
}
