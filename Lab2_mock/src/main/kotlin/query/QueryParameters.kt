package query

import java.time.Instant

data class QueryParameters(val query: String, val startTime: Instant)
