package by.bsuir.headkeepers.githubanalyzer.service

import by.bsuir.headkeepers.githubanalyzer.support.DatabaseConnection
import org.slf4j.LoggerFactory

open class BeanService {
    protected val logger = LoggerFactory.getLogger("Service")!!
    init {
        DatabaseConnection.open()
    }
}