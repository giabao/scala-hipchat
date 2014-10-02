package com.sandinh

import com.sandinh.hipchat.vo.Tenant
import scala.concurrent.duration._

package object hipchat {
  /** await timeout for running test */
  val timeout = 5.seconds

  /** install (by point to https://<my-domain>/hipchat/capabilities in hipchat / group admin / Integrations tab)
    * then browse data/tenants h2 db to get this Tenant */
  val tenantTest = Tenant(
    id = "649c6f67-4644-488e-84d6-c77d024cae3d",
    secret = "jukrmAeIHcALMVYMy43rrzHYfTWLnF4jUMUsc4CR",
    linkApi = "https://chat.sandinh.com/v2",
    linkToken = "https://chat.sandinh.com/v2/oauth/token"
  )
}
