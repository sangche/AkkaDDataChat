package services

import akka.actor.{Actor, ActorRef, ActorLogging, Props}
import akka.cluster.Cluster
import akka.cluster.ddata.DistributedData
import akka.cluster.ddata.Replicator._
import akka.cluster.ddata.{LWWRegister, LWWRegisterKey}

object ChatActor {
  def props(out: ActorRef) = Props(new ChatActor(out))
}

class ChatActor(out: ActorRef) extends Actor with ActorLogging {

  val replicator = DistributedData(context.system).replicator
  implicit val node = Cluster(context.system)
 
  val DataKey = LWWRegisterKey[String]("key") 
  replicator ! Subscribe(DataKey, self)

  def receive = {
    case c @ Changed(DataKey) =>
      val data = c.get(DataKey)
      out ! data.value

    case text: String =>
      replicator ! Update(DataKey, LWWRegister[String](null), WriteLocal) {
        reg => reg.withValue(text)
      }
  }
}