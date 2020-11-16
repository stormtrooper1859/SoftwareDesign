package query

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.time.Instant

class VkFeedResponseParserTest {
    private lateinit var parser: VkFeedResponseParser;

    @Before
    fun setUp() {
        parser = VkFeedResponseParser()
    }

    @Test
    fun parserReturnNullOnFail() {
        val responseJSON = "{\"response\":{\"d121}}"
        val result = parser.parseResponse(responseJSON)
        assertNull(result)
    }

    @Test
    fun parsingDataWithoutInfo() {
        val responseJSON = "{\"response\":{\"d\":1}}"
        val result = parser.parseResponse(responseJSON)
        assertEquals(Pair(ArrayList<Instant>(), null), result)
    }

    @Test
    fun parserCorrectness() {
        val responseJSON = "{\"response\":{\"items\":[{\"date\":12},{\"date\":123},{\"date\":1234}],\"next_from\":\"6/-167492120_738\"}}"
        val result = parser.parseResponse(responseJSON)
        val expectedArray = arrayListOf<Long>(12, 123, 1234).map { Instant.ofEpochSecond(it) }
        assertEquals(Pair(expectedArray, "6/-167492120_738"), result)
    }
}
