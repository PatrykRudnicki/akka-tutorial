package com.lightbend.akka.sample.akkaExperiments

import akka.actor.{Actor, ActorSystem, Props}

class StartStopActor1 extends Actor {
  override def preStart(): Unit = {
    println("first started")
    context.actorOf(Props[StartStopActor2], "second")
  }
  override def postStop(): Unit = println("first stopped")

  override def receive: Receive = {
    case "stop" ⇒ context.stop(self)
  }
}

class StartStopActor2 extends Actor {
  override def preStart(): Unit = println("second started")
  override def postStop(): Unit = println("second stopped")

  // Actor.emptyBehavior is a useful placeholder when we don't
  // want to handle any messages in the actor.
  override def receive: Receive = Actor.emptyBehavior
}

object ActorStartStopExperiments extends App {
  val system = ActorSystem("startStopSystem")

  println("Before system")

  val first = system.actorOf(Props[StartStopActor1], "first")

  println("I'm here")

  first ! "stop"

  println("After all")

  system.terminate()
}