package query

import org.junit.Assert.*
import org.junit.Test
import org.mockito.Mockito
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit

class PostFrequencyControllerTest {

    @Test
    fun buildDiagramForHoursRangeTest() {
        val mockBookService = Mockito.mock(RequestController::class.java)
        val dateNow = Instant.now()
        val params = QueryParameters("#mashup", dateNow.minus(Duration.of(3L, ChronoUnit.HOURS)))
        val response = QueryResponse(arrayListOf(dateNow.minus(Duration.of(65L, ChronoUnit.MINUTES))))
        Mockito.`when`(mockBookService.request(params)).thenReturn(response)

        val c = PostFrequencyController(mockBookService)

        val res = c.buildDiagramForHoursRange("#mashup", 3, dateNow)
        println(res.contentToString())
        assertArrayEquals(res, intArrayOf(0, 1, 0))
    }

}
