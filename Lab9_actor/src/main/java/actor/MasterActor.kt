package actor

import akka.actor.Props
import akka.actor.UntypedAbstractActor
import net.PageLoader

class MasterActor(private val loader: PageLoader) : UntypedAbstractActor() {
    override fun onReceive(message: Any?) {
        if (message is Query) {
            this.context.actorOf(Props.create(BingLoaderActor::class.java, loader), "bing")
            this.context.actorOf(Props.create(YandexLoaderActor::class.java, loader), "yandex")
            this.context.actorOf(Props.create(YahooLoaderActor::class.java, loader), "yahoo")

            this.context.children.forEach {
                it.tell(message, self)
            }
        } else if (message is Response) {
            val sender = sender.path().name()
            for (item in message.list) {
                println("${sender}: $item")
            }
        }
        println(message)
    }
}
