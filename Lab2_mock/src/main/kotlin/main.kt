import config.Props
import config.PropsReader
import query.PostFrequencyController
import query.VkFeedController

fun main() {
    val props: Props = PropsReader().readPropertyFile("key.properties") ?: return

    val vkFeedController = PostFrequencyController(VkFeedController(props.apiKey))

    val res = vkFeedController.buildDiagramForLastHours("#mashup", 24)
//    val res = qc.load("#mashup", 256)

    println(res.contentToString())
}
