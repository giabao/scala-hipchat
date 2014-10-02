package com.sandinh.hipchat

import com.sandinh.hipchat.vo.enums.Scope.Scope
import org.specs2.mutable.Specification
import org.specs2.specification.AllExpectations
import play.api.test.WithApplication
import com.sandinh.hipchat.vo.response.SelfLinks.readsLinks

class RestClientSpec extends Specification with AllExpectations {
  "RestClient" should {
    "fetch capabilities" in new WithApplication {
      val capabilitiesUrl = tenantTest.linkApi + "/capabilities"
      RestClient.getCapabilities(capabilitiesUrl)
        .map(readsLinks.reads(_).get.self) must ===(capabilitiesUrl).await(timeout = timeout)
    }

    "fetch an oauth token" in new WithApplication {
      RestClient.generateToken(tenantTest.linkToken, tenantTest.id, tenantTest.secret, Set.empty[Scope])
        .map(_.access_token.length) must ===(40).await(timeout = timeout)
    }
  }
}
