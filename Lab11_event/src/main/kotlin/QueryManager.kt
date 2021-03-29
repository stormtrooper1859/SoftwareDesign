interface QueryManager {
    fun query(commandName: String, parameters: Map<String, String>?, data: String?): String?
}
