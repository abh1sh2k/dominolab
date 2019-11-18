package com.dominolab.actors

import akka.actor.{Actor, ActorRef}
import com.dominolab.actors.AkkaMessages.NextTask
import com.dominolab.common.Task

class ConsumerActor(producer: ActorRef) extends Actor{
  override def preStart() {
    producer ! NextTask
  }

  def receive = {
     case task: Task => {
       val name = self.path.name
       task.execute(name.charAt(1)-'a')
       producer ! NextTask
     }
  }
}
