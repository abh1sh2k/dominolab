package com.dominolab.common

case class IntTask(val id:Int) extends Task{
  override def execute(consumerID: Int): Unit = {
    println("Consumer id " + consumerID + ", executing job id " , id)
  }
}
