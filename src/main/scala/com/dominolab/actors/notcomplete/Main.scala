package com.dominolab.actors.notcomplete

import java.util.concurrent.TimeUnit

import akka.actor.{ActorSystem, Props}
import com.dominolab.common.Constants
import com.typesafe.config.ConfigFactory

import scala.concurrent.Await
import scala.concurrent.duration.Duration

object ActorMain {
  def main(args: Array[String]): Unit = {
    val config = ConfigFactory.parseString(Supervisor.getConfig(Constants.CORE , "BalancingDispatcher", Constants.BUFFER_SIZE / Constants.CORE)) // 8 actors consumer pool

    val system = ActorSystem("ConsumerProducerSystem", ConfigFactory.load(config))
    system.actorOf(Props(Supervisor.getActor(Constants.TOTAL_TASKS , Constants.BUFFER_SIZE)), "ConsumerProducer")

    Await.result(system.whenTerminated, Duration(10, TimeUnit.MINUTES))
  }
}
