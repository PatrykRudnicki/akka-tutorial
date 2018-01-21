package com.lightbend.akka.sample

import akka.actor.{ Actor, ActorLogging, Props }

object IotSupervisor {
  val Name = "iot-supervisor"

  def props(): Props = Props(new IotSupervisor)
}

class IotSupervisor extends Actor with ActorLogging {
  override def preStart(): Unit = log.info("IoT Application started")
  override def postStop(): Unit = log.info("IoT Application stopped")

  // No need to handle any messages
  override def receive: Receive = Actor.emptyBehavior

}