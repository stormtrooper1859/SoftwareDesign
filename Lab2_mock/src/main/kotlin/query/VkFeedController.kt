package query

import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.time.Instant
import kotlin.collections.ArrayList

import net.URLReader

class VkFeedController(private val apiKey: String) : RequestController {
    private val responseParser = VkFeedResponseParser()

    override fun request(queryParameters: QueryParameters): QueryResponse {
        val urlReader = URLReader()
        val postsTime = ArrayList<Instant>()
        var startFrom: String? = ""

        while (startFrom != null) {
            val queryURL = getQueryURL(queryParameters, startFrom)
            val responseData = urlReader.load(queryURL)
            val parsedResponse = responseParser.parseResponse(responseData)
            startFrom = parsedResponse?.second
            parsedResponse?.first?.let { postsTime.addAll(it) }
        }

        return QueryResponse(postsTime)
    }

    private fun getQueryURL(queryParameters: QueryParameters, startFrom: String): String =
            "https://api.vk.com/method/${METHOD_NAME}?" +
                    "q=${URLEncoder.encode(queryParameters.query, StandardCharsets.UTF_8.toString())}&" +
                    "start_time=${queryParameters.startTime.epochSecond}&" +
                    "access_token=${apiKey}&" +
                    "v=${API_VERSION}&" +
                    "count=${MAX_ITEMS_PER_QUERY}&" +
                    "start_from=${startFrom}"

    companion object {
        private const val API_VERSION = "5.126"
        private const val METHOD_NAME = "newsfeed.search"
        private const val MAX_ITEMS_PER_QUERY = 200
    }
}
