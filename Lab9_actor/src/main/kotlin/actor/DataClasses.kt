package actor

class Timeout
data class Query(val query: String)
data class Link(val title: String, val url: String)
data class Response(val list: List<Link>)
