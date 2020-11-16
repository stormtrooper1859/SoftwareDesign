package config

import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test

class PropsReaderTest {

    @Test
    fun readPropertyFromInputStreamTest() {
        val data = "VK_API_KEY=1245\nTEMP=1"
        val stream = data.byteInputStream(Charsets.UTF_8);
        val res = PropsReader().readPropertyFromInputStream(stream)
        val sett = Props("1245")
        assertEquals(res, sett)
    }


}