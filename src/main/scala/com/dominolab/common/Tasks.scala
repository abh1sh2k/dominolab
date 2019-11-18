package com.dominolab.common

class Tasks(maxTaks: Int) extends Iterator[Task] {
  var remainingPages = maxTaks

  def hasNext = remainingPages > 0

  override def next(): Task = {
    remainingPages-=1
    new IntTask(maxTaks-remainingPages-1)
  }
}

object Tasks {
  def apply(maxTasks: Int) = new Tasks(maxTasks)
}