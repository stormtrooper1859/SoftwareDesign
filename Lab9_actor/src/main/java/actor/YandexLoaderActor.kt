package actor

import net.PageLoader
import org.jsoup.nodes.Document

class YandexLoaderActor(loader: PageLoader) : LoaderActor(loader) {
    override fun getQueryLink(query: String): String {
        return "https://yandex.ru/search/?lr=2&text=${query}"
    }

    override fun parsePage(document: Document): Response {
        val serpItems = document.select(".serp-item")

        val titles = serpItems.filter {
            it.selectFirst(".organic__path .direct-label") == null
                    && it.selectFirst(".organic__url-text") != null
        }.map {
            Link(
                it.selectFirst(".organic__url-text").text(),
                it.selectFirst(".organic__url").attr("href")
            )
        }

        return Response(titles)
    }
}
