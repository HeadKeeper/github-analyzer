package by.bsuir.headkeepers.githubanalyzer.support

import org.slf4j.LoggerFactory
import java.util.Properties

object Settings {
    private val properties = Properties()
    private val logger = LoggerFactory.getLogger(javaClass)

    init {
        val inputStream = javaClass.classLoader.getResourceAsStream("application.properties")
        properties.load(inputStream)
        logAll()
    }

    fun get(key : String) : String {
        return properties.getProperty(key)
    }

    private fun logAll() {
        properties.forEach{(k, v) -> logger.info("$k : $v") }
    }
}