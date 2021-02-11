package net

import java.io.IOException
import java.nio.charset.StandardCharsets
import java.nio.file.Paths

import java.nio.file.Files
import java.nio.charset.Charset


class URLReaderStub : PageLoader {
    override fun load(sourceUrl: String): String {
        if (sourceUrl.contains("yandex")) {
            return readFile("Lab9_actor/yandex.txt", StandardCharsets.UTF_8)
        } else if (sourceUrl.contains("bing")) {
            return readFile("Lab9_actor/bing.txt", StandardCharsets.UTF_8)
        } else if (sourceUrl.contains("yahoo")) {
            return readFile("Lab9_actor/yahoo.txt", StandardCharsets.UTF_8)
        }
        return ""
    }

    @Throws(IOException::class)
    fun readFile(path: String?, encoding: Charset?): String {
        val path2 = Paths.get(path)
        println(path2.toAbsolutePath())
        val encoded = Files.readAllBytes(path2)
        return String(encoded, encoding!!)
    }
}
