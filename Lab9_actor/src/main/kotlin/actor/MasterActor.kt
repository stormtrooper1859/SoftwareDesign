package actor

import akka.actor.AbstractActorWithTimers
import akka.actor.ActorRef
import akka.actor.Props
import net.PageLoader
import java.time.Duration

class MasterActor(private val loader: PageLoader, private val timeout: Duration?) : AbstractActorWithTimers() {
    private var responsesLeft = 3
    private val answer: ArrayList<Link> = ArrayList()
    private var requester: ActorRef? = null

    override fun createReceive(): Receive {
        return receiveBuilder()
            .match(Query::class.java, this::onRequest)
            .match(Response::class.java, this::onResponse)
            .match(Timeout::class.java, this::onTimeout)
            .build()
    }

    private fun onRequest(query: Query) {
        requester = sender

        context.actorOf(Props.create(BingLoaderActor::class.java, loader), "bing")
        context.actorOf(Props.create(YandexLoaderActor::class.java, loader), "yandex")
        context.actorOf(Props.create(YahooLoaderActor::class.java, loader), "yahoo")

        context.children.forEach {
            it.tell(query, self)
        }

        timeout.let {
            timers.startSingleTimer("Timeout", Timeout(), it)
        }
    }

    private fun onResponse(response: Response) {
        val sender = sender.path().name()
        for (item in response.list) {
            answer.add(Link("${sender}: ${item.title}", item.url))
        }
        responsesLeft -= 1
        if (responsesLeft == 0) {
            sendAnswer()
        }
    }

    private fun onTimeout(_timeout: Timeout) {
        context.children.forEach {
            println(it.path())
            context.stop(it)
        }
        sendAnswer()
    }

    private fun sendAnswer() {
        answer.forEach {
            println(it)
        }
        requester?.tell(Response(answer), self)
        context.stop(self)
    }
}
