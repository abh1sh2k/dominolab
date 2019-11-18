package com.dominolab.threading.consumer

import java.util.concurrent.BlockingQueue

import com.dominolab.common.Task

import scala.concurrent.{ExecutionContext, Future}

abstract class Consumer[Task](val id:Int ,queue: BlockingQueue[Task])(implicit executionContext: ExecutionContext){
  def consume(): Unit
}
