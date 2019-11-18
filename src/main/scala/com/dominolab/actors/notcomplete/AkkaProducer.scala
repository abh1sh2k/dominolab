package com.dominolab.actors.notcomplete

import akka.actor.{Actor, ActorRef, PoisonPill, Stash}
import com.dominolab.actors.AkkaMessages.{NextTask, NoMoreRemainingTask, QueueFull, QueueWork}
import com.dominolab.common.Tasks

import scala.concurrent.duration.FiniteDuration
import scala.concurrent.duration._

class AkkaProducer(tasks : Tasks, duration: FiniteDuration = (100 milliseconds)) extends Actor with Stash  {

  import context._

  override def preStart() { become(running) }

  def receive = PartialFunction.empty

  def running: Actor.Receive = {
    case NextTask => {
      if (tasks.hasNext)
        sender() ! QueueWork(tasks.next())
      else
        sender() ! NoMoreRemainingTask
      self ! PoisonPill
    }
    case QueueFull =>
      become(paused)
      scheduleReactivate
    case "wakeUp" => // already awake
  }

  def paused: Actor.Receive = {
    case "wakeUp" =>
      become(running)
  }

  def scheduleReactivate: Unit = {
    system.scheduler.scheduleOnce(duration, self, "wakeUp")
  }

}

