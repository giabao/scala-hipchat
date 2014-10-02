package com.sandinh.hipchat

import com.sandinh.hipchat.vo.{Tenant, Capabilities, InstallData}
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Success

object TenantInstallable {
  /** @see Receiving Registration Information / Installation part at https://www.hipchat.com/docs/apiv2/addons */
  private def fetchTenant(d: InstallData): Future[Tenant] = Capabilities
    .serverCapabilities(d.capabilitiesUrl)
    .map(Tenant(d, _))

  def install(d: InstallData): Future[_] = fetchTenant(d) flatMap { tenant =>
    TenantClient(tenant)
      .getToken //try getToken. If we can't get => install will be Failure
      .andThen { case Success(_) => TenantStore.store(tenant) }
  }

  /** the return underlying type has no meaning */
  def uninstall(oauthId: String): Future[_] = {
    //To verify the uninstallation request, you can again choose to attempt to request an OAuth token,
    //which should be rejected due to the uninstallation already taking place on the HipChat server
    TenantClient.fromCache(oauthId) match {
      case None => Future successful "not installed"
      case Some(tenantClient) => tenantClient.getToken.recover {
        //Only do uninstall if we can NOT getToken
        case e: Exception =>
          TenantClient.delCache(oauthId)
          TenantStore.remove(oauthId)
      }
    }
  }
}
