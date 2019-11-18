package com.dominolab.actors.notcomplete

import akka.actor.{Actor, ActorRef}
import com.dominolab.actors.AkkaMessages.{_}

import scala.concurrent.duration._


class AkkaConsumer(queue: ActorRef,duration: FiniteDuration = (100 milliseconds)) extends Actor {
  import context._

  override def preStart() {
    become(running)
    queue ! RequestWork
  }

  def receive = PartialFunction.empty

  def running: Actor.Receive = {
    case ProcessWork(task) =>
      task.execute(self.path.name.charAt(1)-'a')
      queue ! RequestWork
    case QueueEmpty => {
      become(paused)
      scheduleReactivate
    }
    case "wakeUp" => // already awake
  }

  def paused: Actor.Receive = {
    case "wakeUp" =>
      become(running)
      queue ! RequestWork
  }

  def scheduleReactivate: Unit = {
    system.scheduler.scheduleOnce(duration, self, "wakeUp")
  }
}
