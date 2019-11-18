package com.dominolab.threading.consumer

import java.util.concurrent.{BlockingQueue, LinkedBlockingQueue}

import com.dominolab.common.{IntTask, Task}

import scala.concurrent.{ExecutionContext, Future}

class IntConsumer(override val id:Int, queue: BlockingQueue[IntTask])(implicit executionContext: ExecutionContext) extends Consumer(id:Int , queue:BlockingQueue[IntTask]) {
  def consume(): Unit = {
      val t = queue.take()
      t.execute(id)
  }
}
