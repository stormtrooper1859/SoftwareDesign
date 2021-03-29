import java.io.InputStream

class ManagerAdmin(
    port: Int,
    private val queryManager: QueryManager
) : Server(port) {

    override fun process(query: Map<String, String>, requestBody: InputStream): String? {
        TODO("Not yet implemented")
    }

}
