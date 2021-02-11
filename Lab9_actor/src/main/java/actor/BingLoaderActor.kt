package actor

import net.PageLoader
import org.jsoup.nodes.Document

class BingLoaderActor(loader: PageLoader) : LoaderActor(loader) {
    override fun getQueryLink(query: String): String {
        return "https://www.bing.com/search?q=${query}"
    }

    override fun parsePage(document: Document): Response {
        val listItem = document.select("li.b_algo ")

        val titles = listItem.map {
            it.selectFirst(".b_title h2 a")
        }.map {
            Link(
                it.text(),
                it.attr("href")
            )
        }

        return Response(titles)
    }
}
