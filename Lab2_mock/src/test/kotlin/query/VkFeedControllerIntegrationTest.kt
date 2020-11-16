package query

import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit
import org.junit.Assert.*
import org.junit.ClassRule
import org.junit.Test

import config.PropsReader
import rule.HostReachableRule

@HostReachableRule.HostReachable(VkFeedControllerIntegrationTest.HOST)
class VkFeedControllerIntegrationTest {

    @Test
    fun checkRequest() {
        val props = PropsReader().readPropertyFile("key.properties")
        assertNotNull(props)
        val vkFeedController = VkFeedController(props!!.apiKey)
        val fromDate = Instant.now().minus(Duration.of(1L, ChronoUnit.DAYS))
        val (postsTime) = vkFeedController.request(QueryParameters("#mashup", fromDate))
        assertTrue(postsTime.isNotEmpty() && !postsTime[0].isBefore(fromDate))
    }

    companion object {
        const val HOST = "api.vk.com"

        @ClassRule
        @JvmField
        val rule = HostReachableRule()
    }
}
