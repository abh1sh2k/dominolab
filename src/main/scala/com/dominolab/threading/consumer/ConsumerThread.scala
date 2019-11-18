package com.dominolab.threading.consumer

class ConsumerThread [Task](consumer: Consumer[Task]) extends Runnable{
  override def run(): Unit = {
    while (true){
      consumer.consume()
    }
  }
}
