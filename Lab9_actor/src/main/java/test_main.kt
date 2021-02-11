import SupervisorExample.ChildActor
import SupervisorExample.EscalateException
import SupervisorExample.RestartException
import akka.actor.*
import akka.actor.SupervisorStrategy.Directive
import akka.japi.pf.DeciderBuilder
import akka.japi.pf.FI.Apply
import scala.Option

/**
 * @author akirakozov
 */
object SupervisorExample {
    @JvmStatic
    fun main(args: Array<String>) {
        val system = ActorSystem.create("MySystem")
        // Create actor
        val parent = system.actorOf(
            Props.create(Supervisor::class.java), "parent"
        )
        parent.tell("start", ActorRef.noSender())
        parent.tell("start", ActorRef.noSender())
        parent.tell("start", ActorRef.noSender())
        for (i in 0..2) {
            system.actorSelection("user/parent/child$i").tell("Hello!", ActorRef.noSender())
        }

        // restart and send new message for child1
        system.actorSelection("user/parent/child1").tell("restart", ActorRef.noSender())
        system.actorSelection("user/parent/child1").tell("Hello2", ActorRef.noSender())

        // stop and send new message for child1 (message wouldn't be received)
        system.actorSelection("user/parent/child1").tell("escalate", ActorRef.noSender())
        system.actorSelection("user/parent/child1").tell("Hello3", ActorRef.noSender())
        system.actorSelection("user/parent/child2").tell("Hello3", ActorRef.noSender())
    }

    class RestartException : RuntimeException()
    class StopException : RuntimeException()
    class EscalateException : RuntimeException()
    class ChildActor : UntypedAbstractActor() {
        override fun postStop() {
            println(self().path().toString() + " was stopped")
        }

        override fun postRestart(cause: Throwable) {
            println(self().path().toString() + " was restarted after: " + cause)
        }

        override fun preRestart(cause: Throwable, message: Option<Any?>?) {
            println(self().path().toString() + " is dying because of " + cause)
        }

        @Throws(Throwable::class)
        override fun onReceive(message: Any) {
            if (message is String) {
                if (message == "restart") {
                    throw RestartException()
                } else if (message == "stop") {
                    throw StopException()
                } else if (message == "escalate") {
                    throw EscalateException()
                } else {
                    println(self().path().toString() + " got message: " + message)
                }
            }
        }
    }

    class Supervisor : UntypedAbstractActor() {
        private var number = 0
        override fun supervisorStrategy(): SupervisorStrategy {
            return OneForOneStrategy(false, DeciderBuilder
                .match(RestartException::class.java) { e: RestartException? -> OneForOneStrategy.restart() }
                .match(
                    StopException::class.java
                ) { e: StopException? -> OneForOneStrategy.stop() }
                .match(EscalateException::class.java) { e: EscalateException? -> OneForOneStrategy.escalate() }
                .build())
        }

        @Throws(Throwable::class)
        override fun onReceive(message: Any) {
            if (message == "start") {
                val name = "child" + number++
                println("Create child: $name")
                getContext().actorOf(Props.create(ChildActor::class.java), name)
            }
        }
    }
}