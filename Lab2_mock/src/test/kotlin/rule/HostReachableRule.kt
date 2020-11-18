package rule

import org.junit.Assume
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import java.io.IOException
import java.util.concurrent.TimeUnit


class HostReachableRule : TestRule {
    @kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
    @Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER, AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
    annotation class HostReachable(val value: String)

    override fun apply(statement: Statement, description: Description): Statement {
        val hostReachable = description.getAnnotation(HostReachable::class.java)
        if (hostReachable == null) {
            return statement
        } else if (!checkHost(hostReachable.value)) {
            return SkipStatement(hostReachable.value)
        }
        return statement
    }

    private class SkipStatement(private val host: String) : Statement() {
        @Throws(Throwable::class)
        override fun evaluate() {
            Assume.assumeTrue("Skipped, because following host " +
                    "is not available at the moment: " + host, false)
        }
    }

    companion object {
        private const val TIMEOUT = 10000
        private fun checkHost(host: String): Boolean {
            return nativePing(host) || nativePing6(host)
        }

        private fun nativePing(host: String): Boolean {
            return nativePingImpl("4", host)
        }

        private fun nativePing6(host: String): Boolean {
            return nativePingImpl("6", host)
        }

        private fun nativePingImpl(version: String, host: String): Boolean {
            return try {
                val pingProcess = ProcessBuilder("ping", "-${version}", host).start()
                if (!pingProcess.waitFor(TIMEOUT.toLong(), TimeUnit.MILLISECONDS)) {
                    false
                } else pingProcess.exitValue() == 0
            } catch (e: IOException) {
                e.printStackTrace()
                false
            } catch (e: InterruptedException) {
                e.printStackTrace()
                false
            }
        }
    }
}