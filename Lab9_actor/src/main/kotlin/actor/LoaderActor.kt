package actor

import akka.actor.UntypedAbstractActor
import net.PageLoader
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

abstract class LoaderActor(private val loader: PageLoader) : UntypedAbstractActor() {
    override fun onReceive(message: Any?) {
        if (message is Query) {
            val res = getLinks(message)
            this.context.parent.tell(res, self)
        }
        context.stop(self)
    }

    private fun getLinks(query: Query): Response {
        val url = getQueryLink(URLEncoder.encode(query.query, StandardCharsets.UTF_8.toString()))
        val data = loader.load(url)
        val document = Jsoup.parse(data)

        val result = parsePage(document)

        return Response(result.list.take(MAX_ITEMS_FROM_LOADER))
    }

    abstract fun getQueryLink(query: String): String
    abstract fun parsePage(document: Document): Response

    companion object {
        private const val MAX_ITEMS_FROM_LOADER = 5
    }
}
