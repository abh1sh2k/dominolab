package com.dominolab.threading.producer

import java.util.concurrent.{BlockingQueue, LinkedBlockingQueue, TimeUnit}

import com.dominolab.common.{Constants, IntTask, Task}

import scala.concurrent.{ExecutionContext, Future}

class IntProducer(val totalTasks:Int , queue: BlockingQueue[IntTask])(implicit executionContext: ExecutionContext) extends Producer(queue:BlockingQueue[IntTask]) {
  def produce(): Unit = {
    while (dataSource.hasNext){
      val task = dataSource.next()
      println("producing task "+ task.id)
      queue.put(task )
      Thread.sleep(Constants.PRODUCER_SLEEP_TIME)
    }
  }

  private lazy val dataSource: Iterator[IntTask] = {
    1 to totalTasks
    }.map(new IntTask(_)).toIterator
}