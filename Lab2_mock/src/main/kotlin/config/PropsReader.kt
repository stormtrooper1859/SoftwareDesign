package config

import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream
import java.util.*

class PropsReader {

    fun readPropertyFile(path: String): Props? {
        try {
            val fileInputStream = File(path).inputStream()

            return readPropertyFromInputStream(fileInputStream)
        } catch (e: FileNotFoundException) {
            System.err.println("File not found")
        }

        return null;
    }

    fun readPropertyFromInputStream(inputStream: InputStream): Props? {
        inputStream.use {
            val properties = Properties()
            properties.load(it)
            val vkKey = properties[VK_API_KEY] as String

            return Props(vkKey);
        }
    }

    companion object {
        private const val VK_API_KEY = "VK_API_KEY"
    }
}
