package com.dominolab.actors
import akka.actor.{Actor, PoisonPill, Props, Terminated}
import akka.routing.{Broadcast, FromConfig}

class Supervisor(val totalTasks : Int , val bufferSize : Int) extends Actor {
  val producer = context.actorOf(Props(new ProducerActor(totalTasks)), "producer")
  val consumers = context.actorOf(Props(new ConsumerActor(producer)).
    withRouter(FromConfig()), "consumers")

  context.watch(consumers)
  context.watch(producer)

  def receive = {
    case Terminated(`producer`) => consumers ! Broadcast(PoisonPill)
    case Terminated(`consumers`) => context.system.terminate
  }
}

object Supervisor {
  def getActor(totalTask:Int , producerSize : Int) = new Supervisor(totalTask , producerSize)

  def getConfig(numConsumers: Int , dispatcherType : String , mailboxCapicity: Int) =
    s"""
      akka.actor.deployment {
        /ConsumerProducer/consumers {
          router = round-robin-pool
          nr-of-instances = $numConsumers
        }
        consumer-dispatcher {
        type = $dispatcherType
        mailbox-capacity = $mailboxCapicity
       }
      }"""
}