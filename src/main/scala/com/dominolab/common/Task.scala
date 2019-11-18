package com.dominolab.common

trait Task{
  def execute(consumerID: Int):Unit
}
