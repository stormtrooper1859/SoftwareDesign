import com.google.gson.JsonObject

open class Event(
    val timestamp: Long,
    val id: Long
)

class EventExtendLeft(
    val until: Long,
    timestamp: Long, id: Long
) : Event(timestamp, id)

class EventLeft(timestamp: Long, id: Long) : Event(timestamp, id)
class EventEntered(timestamp: Long, id: Long) : Event(timestamp, id)

fun event2map(event: Event): MutableMap<String, String> {
    val builder = HashMap<String, String>()
    builder["timestamp"] = event.timestamp.toString()
    builder["id"] = event.id.toString()
    when (event) {
        is EventExtendLeft -> {
            builder["type"] = "payed"
            builder["till"] = event.until.toString()
        }
        is EventEntered   -> builder["type"] = "entered"
        is EventLeft      -> builder["type"] = "left"
    }
    return builder
}

fun JsonObject.getLong(id: String) = (this[id].asString).toLong()

fun json2event(obj: JsonObject): Event? {
    val timestamp = obj.getLong("timestamp")
    val id = obj.getLong("id")
    return when (obj["type"].asString) {
        "payed"   -> EventExtendLeft(obj.getLong("till"), timestamp, id)
        "entered" -> EventEntered(timestamp, id)
        "left"    -> EventLeft(timestamp, id)
        else -> {
            println("null")
            null
        }
    }
}
