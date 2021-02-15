package net

interface PageLoader {
    fun load(sourceUrl: String): String
}
