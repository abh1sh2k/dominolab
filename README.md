#Implemented using normal thread and Akka Actors
1)com.dominolab.threading.Main is the main class to run threading producers and consumer

2)com.dominolab.actors.ActorMain is the main class to run Actor Main class
   i)using actors I have used bounded consumer instead of producer. I was facing issue in creating bounded producer( mainly terms of changing context like stopping producer when queue is full or stopping consuker when queue is empty)
   ii)The codes is in com.dominolab.actors.notcomplete