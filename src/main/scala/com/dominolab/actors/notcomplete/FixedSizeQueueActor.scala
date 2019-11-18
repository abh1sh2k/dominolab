package com.dominolab.actors.notcomplete
import akka.actor.{Actor, ActorRef}
import com.dominolab.actors.AkkaMessages._
import com.dominolab.common.Task

import scala.collection.immutable.Queue

class FixedSizeQueueActor(producer:ActorRef, size: Int) extends Actor {
  var currentSize = 0
  var itemQueue: Queue[Task] = Queue.empty[Task]

  override def receive: Receive = {
    case Start =>
      producer ! NextTask
    case QueueWork(task) =>
      if (currentSize < size) {
        itemQueue = itemQueue.enqueue(task)
        currentSize += 1
        sender() ! NextTask
      }
      else
        sender() ! QueueFull
    case RequestWork =>
      val consumer = sender()
      if (itemQueue.nonEmpty) {
        val (item, remainingItemQueue) = itemQueue.dequeue
        itemQueue = remainingItemQueue
        currentSize-=1
        consumer ! ProcessWork(item)
      } else {
        consumer ! QueueEmpty
      }
    case NoMoreRemainingTask => context.parent ! NoMoreRemainingTask

  }
}
