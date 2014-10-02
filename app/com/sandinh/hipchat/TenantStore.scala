package com.sandinh.hipchat

import anorm._
import SqlParser._
import com.sandinh.hipchat.vo.Tenant
import play.api.db.DB
import play.api.Play.current

object TenantStore {
  def store(tenant: Tenant) = DB.withConnection { implicit conn =>
    import tenant._
    SQL"INSERT INTO tenant(id, secret, link_api, link_token) VALUES($id, $secret, $linkApi, $linkToken)"
      .executeInsert()
  }

  private val getAllParser = (str("id") ~ str("secret") ~ str("link_api") ~ str("link_token") map {
    case id ~ secret ~ linkApi ~ linkToken => Tenant(id, secret, linkApi, linkToken)
  }).*

  def getAll: Seq[Tenant] = DB.withConnection { implicit conn =>
    SQL"SELECT * FROM tenant".as(getAllParser)
  }

  def remove(tenantId: String) = DB.withConnection { implicit conn =>
    SQL"DELETE FROM tenant WHERE id = $tenantId".executeUpdate()
  }
}
