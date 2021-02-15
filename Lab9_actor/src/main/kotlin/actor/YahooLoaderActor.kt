package actor

import net.PageLoader
import org.jsoup.nodes.Document

class YahooLoaderActor(loader: PageLoader) : LoaderActor(loader) {
    override fun getQueryLink(query: String): String {
        return "https://search.yahoo.com/search?p=${query}"
    }

    override fun parsePage(document: Document): Response {
        val listItem = document.select("ol.searchCenterMiddle li h3.title a")

        val titles = listItem.map {
            Link(
                it.text(),
                it.attr("href")
            )
        }

        return Response(titles)
    }
}
