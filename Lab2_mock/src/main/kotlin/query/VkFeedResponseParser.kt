package query

import com.google.gson.JsonParseException
import com.google.gson.JsonParser
import java.time.Instant

class VkFeedResponseParser {
    fun parseResponse(data: String?): Pair<ArrayList<Instant>, String?>? {
        try {
            val response = JsonParser.parseString(data).asJsonObject.getAsJsonObject("response")
            val nextFrom = response?.get("next_from")
            val postsArray = response?.get("items")?.asJsonArray

            val res = ArrayList<Instant>()

            if (postsArray != null) {
                for (el in postsArray) {
                    val elem = el.asJsonObject
                    val date = elem.get("date").asLong
                    val parsedDate = Instant.ofEpochSecond(date)

                    res.add(parsedDate)
                }
            }
            return Pair(res, nextFrom?.asString)
        } catch (e: JsonParseException) {
            System.err.println(e.message)
        }
        return null
    }
}
