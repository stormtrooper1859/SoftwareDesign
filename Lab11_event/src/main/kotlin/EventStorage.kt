import com.google.gson.Gson
import com.google.gson.JsonArray
import java.io.InputStream
import java.io.InputStreamReader

class EventStorage(
    port: Int
) : Server(port) {

    private val storage = ArrayList<Event>()
    private val gson = Gson()

    override fun process(query: Map<String, String>, requestBody: InputStream): String? {
        return when (query["command"]) {
            "GET_USER" -> {
                val response = JsonArray()

                query["id"]?.let {
                    val id = it.toLong()

                    for (event in storage) {
                        if (id == event.id) {
                            response.add(gson.toJson(event2map(event)))
                        }
                    }
                }

                response.toString()
            }
            "GET_ALL_USERS" -> {
                val response = JsonArray()

                for (event in storage) {
                    response.add(gson.toJson(event2map(event)))
                }

                response.toString()
            }
            "ADD" -> {
                Gson().fromJson(InputStreamReader(requestBody), Event::class.java)
                "Ok"
            }
            else -> {
                "Unsupported operation"
            }
        }
    }
}
