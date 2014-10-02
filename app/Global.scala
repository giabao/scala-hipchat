import com.sandinh.hipchat.{TenantClient, TenantStore}
import play.api.{Logger, Application, GlobalSettings}
import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import java.util.concurrent.TimeUnit.SECONDS

object Global extends GlobalSettings {
  override def onStart(app: Application): Unit = {
    val restoreTenantsFuture = Future.traverse(TenantStore.getAll)(tenant =>
      TenantClient(tenant).getToken
    )
    try {
      val timeout = app.configuration.underlying
        .getDuration("com.sandinh.scala-hipchat.tenants-restore-timeout", SECONDS).seconds
      Await.ready(restoreTenantsFuture, timeout)
    } catch {
      case e: Exception => Logger.error("Can't restore tenants", e)
    }
  }
}
