package com.dominolab.actors.notcomplete
import akka.actor.{Actor, PoisonPill, Props, Terminated, _}
import akka.routing.{Broadcast, FromConfig}
import com.dominolab.actors.AkkaMessages.{NoMoreRemainingTask, Start}
import com.dominolab.common.{Constants, Tasks}

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.language.postfixOps

class Supervisor(val totalTasks : Int , val bufferSize : Int) extends Actor {
  val tasks = Tasks(totalTasks)

  val producer =  context.actorOf(Props(new AkkaProducer(tasks ,(Constants.PRODUCER_SLEEP_TIME millis))), "producer")
  val queue = context.actorOf(Props(new FixedSizeQueueActor(producer, Constants.TOTAL_TASKS)), "queue")
  val consumers = context.actorOf(Props(new AkkaConsumer(queue)).
    withRouter(FromConfig()), "consumers")

  override def preStart(): Unit = {
    queue ! Start
    context.watch(consumers)
    context.watch(queue)
    context.watch(producer)
  }
  def receive = {
    case Terminated(`consumers`) => consumers ! Broadcast(PoisonPill)
    case Terminated(`producer`) => context.system.terminate()
    case Terminated(`queue`) => println("queue terminating")
    case NoMoreRemainingTask => {
      Await.result(context.system.terminate() , 50.seconds)
    }
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

