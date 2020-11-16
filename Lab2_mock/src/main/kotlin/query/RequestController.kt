package query

interface RequestController {
    fun request(queryParameters: QueryParameters): QueryResponse
}
