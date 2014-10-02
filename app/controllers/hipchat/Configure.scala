package controllers.hipchat

import com.nimbusds.jwt.SignedJWT
import play.api.mvc.{Action, Controller}

object Configure extends Controller {
  def index = Action { req =>
    req.getQueryString("signed_request") match {
      case None => BadRequest
      case Some(signedRequest) =>
        val jwt = SignedJWT.parse(signedRequest).getJWTClaimsSet.toJSONObject.toString
        //signedRequest is a JWT http://tools.ietf.org/html/draft-ietf-oauth-json-web-token-11
        Ok(views.html.hipchat.configure(jwt))
    }
  }
}
