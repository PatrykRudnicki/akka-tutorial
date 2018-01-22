package com.lightbend.akka.sample.internetOfThings

import akka.actor.ActorSystem
import akka.testkit.TestProbe
import org.scalatest.{Matchers, WordSpec}

class DeviceManagerSpec extends WordSpec with Matchers {
  implicit val system = ActorSystem("device-manager-spec")

  "Device manager actor" should {
    "be able to register a device group actor" in {
      val probe = TestProbe()
      val managerActor = system.actorOf(DeviceManager.props())

      managerActor.tell(DeviceManager.RequestTrackDevice("group", "device1"), probe.ref)
      probe.expectMsg(DeviceManager.DeviceRegistered)
      val groupActor1 = probe.lastSender

      managerActor.tell(DeviceManager.RequestTrackDevice("group", "device2"), probe.ref)
      probe.expectMsg(DeviceManager.DeviceRegistered)
      val groupActor2 = probe.lastSender
      groupActor1 should !== (groupActor2)
    }

    "return same actor for same group" in {
      val probe = TestProbe()
      val managerActor = system.actorOf(DeviceManager.props())

      managerActor.tell(DeviceManager.RequestTrackDevice("group", "device1"), probe.ref)
      probe.expectMsg(DeviceManager.DeviceRegistered)
      val groupActor1 = probe.lastSender

      managerActor.tell(DeviceManager.RequestTrackDevice("group", "device1"), probe.ref)
      probe.expectMsg(DeviceManager.DeviceRegistered)
      val groupActor2 = probe.lastSender
      groupActor1 should === (groupActor2)
    }
  }
}
