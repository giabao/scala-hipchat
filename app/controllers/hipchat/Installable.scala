package controllers.hipchat

import com.sandinh.hipchat.TenantInstallable
import com.sandinh.hipchat.vo.InstallData
import play.api.Logger
import play.api.mvc.{Action, Controller, Result}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object Installable extends Controller {
  def install = Action.async(parse.json) {
    _.body.validate[InstallData]
      .map(doInstall)
      .recoverTotal { _ => Future successful BadRequest}
  }

  //@see Receiving Registration Information / Installation part at https://www.hipchat.com/docs/apiv2/addons
  //@see mountInstalledWebhook at https://bitbucket.org/atlassianlabs/ac-koa-hipchat/src/master/lib/index.js
  private def doInstall(d: InstallData): Future[Result] =
    TenantInstallable.install(d).map(_ => Ok) recover {
      case e: Exception =>
        Logger.error("Can NOT install addon", e)
        Forbidden
    }

  def uninstall(oauthId: String) = Action async {
    TenantInstallable.uninstall(oauthId) map (_ => Ok)
  }
}
