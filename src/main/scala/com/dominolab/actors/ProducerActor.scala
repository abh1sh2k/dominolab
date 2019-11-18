package com.dominolab.actors

import akka.actor.Actor
import com.dominolab.actors.AkkaMessages.NextTask
import com.dominolab.common.{IntTask, Tasks}

class ProducerActor(totalTasks : Int) extends Actor{
  val pages = Tasks(totalTasks)

  def receive = {
    case NextTask =>
      if (pages.hasNext)
        sender ! pages.next()
      else
        context.stop(self)
  }
}

