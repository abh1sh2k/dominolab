package com.dominolab.threading.producer

import java.util.concurrent.BlockingQueue

import com.dominolab.common.Task

import scala.concurrent.{ExecutionContext, Future}

abstract class Producer[T<:Task](queue: BlockingQueue[T])(implicit executionContext: ExecutionContext){
}