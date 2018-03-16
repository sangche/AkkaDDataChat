package controllers

import play.api.mvc._
import play.api.libs.streams.ActorFlow
import javax.inject.Inject
import akka.actor.ActorSystem
import akka.stream.Materializer

import services._

class Application @Inject()(cc:ControllerComponents)(implicit system: ActorSystem, mat: Materializer) 
  extends AbstractController(cc) {

  def index = Action {
    Ok(views.html.index("Hello."))
  } 

  def socket = WebSocket.accept[String, String] { request =>
    ActorFlow.actorRef { out =>
      ChatActor.props(out)
    }
  }
}