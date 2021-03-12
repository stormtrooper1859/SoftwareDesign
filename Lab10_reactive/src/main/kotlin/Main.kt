import com.mongodb.rx.client.MongoClients
import db.DBManager
import db.ServiceDAO
import io.reactivex.netty.protocol.http.server.HttpServer

fun main() {
    val controller = Controller(getMongoDAO())

    HttpServer
        .newServer(8080)
        .start { request, response ->
            response.writeString(controller.process(request.decodedPath, request.queryParameters))
        }
        .awaitShutdown()
}


fun getMongoDAO(): ServiceDAO {
    val client = MongoClients.create("mongodb://localhost:27017")
    val db = client.getDatabase("reactive")
    return DBManager(db)
}
