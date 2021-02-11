import actor.MasterActor
import actor.Query
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import net.URLReader
import net.URLReaderStub

fun main() {
    val system = ActorSystem.create("MySystem");
//    val actor = system.actorOf(Props.create(MasterActor::class.java, URLReader()), "master")
    val actor = system.actorOf(Props.create(MasterActor::class.java, URLReaderStub()), "master")
    actor.tell(Query("java"), ActorRef.noSender())
}