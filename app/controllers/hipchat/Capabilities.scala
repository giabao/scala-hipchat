package controllers.hipchat

import controllers.Assets
import play.api.mvc.Controller

object Capabilities extends Controller {
  def index = Assets.at("/", "capabilities.json")
}
