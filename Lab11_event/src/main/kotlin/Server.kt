import com.sun.net.httpserver.HttpServer
import java.io.InputStream
import java.net.InetSocketAddress
import java.nio.charset.Charset

abstract class Server(port: Int) {

    private val server = HttpServer.create(InetSocketAddress(port), 0)

    init {
        server.createContext("/") {
            val queryParameters = parseQueryParameters(it.requestURI.query)
            val responseString = process(queryParameters, it.requestBody)

            val status = if (responseString != null) 200 else 400
            val response = responseString?.toByteArray(Charset.defaultCharset()) ?: ByteArray(0)

            it.sendResponseHeaders(status, response.size.toLong())
            it.responseBody.write(response)
            it.responseBody.close()
        }
//        server.start()
    }

    fun start() = server.start()

    private fun parseQueryParameters(queryParameters: String): Map<String, String> {
        val result = HashMap<String, String>()
        for (pair in queryParameters.split("&")) {
            val q = pair.split("=")
            result[q[0]] = q[1]
        }
        return result
    }

    abstract fun process(query: Map<String, String>, requestBody: InputStream): String?
}
