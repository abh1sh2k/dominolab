package com.dominolab.threading

import java.util.concurrent.{Executors, LinkedBlockingQueue}

import com.dominolab.common.{Constants, IntTask}
import com.dominolab.threading.consumer.{Consumer, ConsumerThread, IntConsumer}
import com.dominolab.threading.producer.IntProducer

import scala.concurrent.ExecutionContext

object Main {
  def main(args: Array[String]): Unit = {
    val queue = new LinkedBlockingQueue[IntTask](Constants.BUFFER_SIZE)

    val pool = Executors.newFixedThreadPool(Constants.CORE)
    implicit val executionContext = ExecutionContext.fromExecutor(pool)

    //val list = List[ConsumerThread[IntTask]]()
    // Submit one consumer per core.
    for (i <- 1 to Constants.CORE) {
      val thread = new ConsumerThread[IntTask](new IntConsumer(i , queue))
      //list.::(thread)
      pool.submit(thread)
    }

    val producer = new IntProducer(Constants.TOTAL_TASKS ,queue)
    producer.produce()

    //shutting down all threads after task
    pool.shutdownNow()
  }
}
