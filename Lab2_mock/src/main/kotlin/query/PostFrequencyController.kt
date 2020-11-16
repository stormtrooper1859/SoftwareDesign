package query

import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit

class PostFrequencyController(private val requestController: RequestController) {

    fun buildDiagramForLastHours(query: String, hours: Int): IntArray? {
        val dateNow = Instant.now()
        return buildDiagramForHoursRange(query, hours, dateNow)
    }

    fun buildDiagramForHoursRange(query: String, hours: Int, dateNow: Instant): IntArray? {
        val startFrom = dateNow.minus(Duration.of(hours.toLong(), ChronoUnit.HOURS))

        val res = requestController.request(QueryParameters(query, startFrom));

        return datesToFrequencyArray(res.postsTime.sortedDescending(), hours, dateNow)
    }

    private fun datesToFrequencyArray(postsTime: List<Instant>, hours: Int, lastTime: Instant): IntArray {
        var i = 0
        var curTime: Instant
        var ind = 0

        val result = IntArray(hours)

        while (ind < hours) {
            curTime = lastTime.minus(Duration.of(ind + 1L, ChronoUnit.HOURS))
            while (i < postsTime.size && !postsTime[i].isBefore(curTime)) {
                i++
                result[ind]++
            }
            ind++
        }
        return result
    }

}
