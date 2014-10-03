package com.sandinh.hipchat

import com.sandinh.hipchat.vo.response.{RoomStatistics, User}
import org.specs2.specification.AllExpectations
import org.specs2.mutable.Specification
import play.api.Application
import play.api.libs.json.JsObject
import play.api.test.WithApplication

class GetRoomSpec extends Specification with AllExpectations {
  def tenantClient(implicit app: Application) = TenantClient.fromCache(tenantTest.id).get

  "TenantClient" should {
    "getRoom without expand" in new WithApplication {
      val roomFut = tenantClient.getRoom("1")
      roomFut.map(_.links.self) must ===(tenantTest.linkApi + "/room/1").await(timeout = timeout)

      roomFut.map(_.statisticsBrief.links.self) must ===(tenantTest.linkApi + "/room/1/statistics").await(timeout = timeout)

      roomFut.map(_.statistics) must beLike[JsObject]{
        //`messages_sent` is not in GetRoomResponse.briefStatistics
        case js => (js \ "messages_sent").validate[Int].isSuccess must beFalse
      }.await(timeout = timeout)
    }

    "getRoom with expand" in new WithApplication {
      val roomFut = tenantClient.getRoom("1", "statistics,owner")

      roomFut.map(_.statisticsExpand) must beLike[RoomStatistics]{
        case statistics =>
          statistics.messages_sent must be_>=(0) and
          statistics.links.self ===(tenantTest.linkApi + "/room/1/statistics")
      }.await(timeout = timeout)

      roomFut.map(_.ownerExpand) must beLike[User]{
        case u => u.is_guest must beFalse
      }.await(timeout = timeout)
    }
  }
}
