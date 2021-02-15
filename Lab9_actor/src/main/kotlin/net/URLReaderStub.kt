package net

import java.nio.charset.StandardCharsets
import java.nio.file.Paths
import java.nio.file.Files


class URLReaderStub(private val unavailableServices: List<String>?) : PageLoader {
    private val map = HashMap<String, String>()

    constructor() : this(null) {}

    init {
        for (name in stubServersName) {
            map[name] = readFile("stub_data/${name}.txt")
        }
    }

    override fun load(sourceUrl: String): String {
        unavailableServices?.let {
            for (name in it) {
                if (sourceUrl.contains(name)) {
                    Thread.sleep(SLEEP_TIMEOUT)
                }
            }
        }
        for (name in stubServersName) {
            if (sourceUrl.contains(name)) {
                return map[name]!!
            }
        }
        throw RuntimeException("Stub not found")
    }

    private fun readFile(path: String): String {
        val encoded = Files.readAllBytes(Paths.get(path))
        return String(encoded, StandardCharsets.UTF_8)
    }

    companion object {
        val stubServersName = listOf("bing", "yahoo", "yandex")
        const val SLEEP_TIMEOUT = 15000L
    }
}
