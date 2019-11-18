package com.dominolab.actors
import com.dominolab.common.Task

object AkkaMessages {
  case class QueueWork(task: Task)
  case class ProcessWork(task : Task)
  object NextTask
  object RequestWork
  object QueueFull
  object Start
  object QueueEmpty
  object NoMoreRemainingTask
}