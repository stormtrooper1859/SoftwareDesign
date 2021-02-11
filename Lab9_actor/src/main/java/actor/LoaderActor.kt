package actor

import akka.actor.UntypedAbstractActor
import net.PageLoader
import net.URLReader
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

abstract class LoaderActor(private val loader: PageLoader) : UntypedAbstractActor() {
    override fun onReceive(message: Any?) {
        println(message) // TODO
        if (message is Query) {
            val res = getLinks(message)
            this.context.parent.tell(res, self)
        }
    }

    private fun getLinks(query: Query): Response {
        val url = getQueryLink(URLEncoder.encode(query.query, StandardCharsets.UTF_8.toString()))
        val data = loader.load(url)
        val document = Jsoup.parse(data)

        return parsePage(document);
    }

    abstract fun getQueryLink(query: String) : String
    abstract fun parsePage(document: Document) : Response
}