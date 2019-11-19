#Implemented using normal thread and Akka Actors
1)com.dominolab.threading.Main is the main class to run threading producers and consumer

2)com.dominolab.actors.ActorMain is the main class to run Actor Main class
   
   i)using actors I have used  with bounded  mailbox-capacity instead of producer.I am using pull based approach for producer.
    I am currently facing issue in push based approach and creating bounded queue for producer
    ( mainly terms of changing context like stopping producer when queue is full or stopping consuker when queue is empty)
   
   ii)The codes for above(bounded queue for producer) in com.dominolab.actors.notcomplete package