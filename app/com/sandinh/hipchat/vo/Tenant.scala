package com.sandinh.hipchat.vo

object Tenant {
  def apply(d: InstallData, capabilities: Capabilities): Tenant =
    Tenant(d.oauthId, d.oauthSecret, capabilities.linkApi, capabilities.linkToken)
}

//see ac-koa-hipchat / tenant-factory.js
case class Tenant(
  id:     String,
  secret: String,
  // group: String, ???
  linkApi:   String,
  linkToken: String
)
