import actor.MasterActor
import actor.Query
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import net.URLReaderStub
import java.time.Duration

fun main() {
    val system = ActorSystem.create("MySystem");
//    val actor = system.actorOf(Props.create(actor.MasterActor::class.java, net.URLReader()), "master")
    val actor = system.actorOf(
        Props.create(MasterActor::class.java, URLReaderStub(listOf("yandex")), Duration.ofMillis(3000)),
        "master"
    )
    actor.tell(Query("java"), ActorRef.noSender())
}