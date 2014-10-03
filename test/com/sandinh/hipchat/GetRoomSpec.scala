package com.sandinh.hipchat

import com.sandinh.hipchat.vo.response.Room
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
      roomFut.map(_.statistics) must beLike[JsObject]{
        //`messages_sent` is not in GetRoomResponse.briefStatistics
        case js if (js \ "messages_sent").validate[Int].isError => ok
      }.await(timeout = timeout)
    }

    "getRoom with expand" in new WithApplication {
      val roomFut = tenantClient.getRoom("1", "statistics,owner")
      roomFut must beLike[Room]{
        case room if
          (room.statistics \ "messages_sent").validate[Int].isSuccess &&
          (room.owner \ "presence" \ "is_online").validate[Boolean].isSuccess =>
          ok
      }.await(timeout = timeout)
    }
  }
}
