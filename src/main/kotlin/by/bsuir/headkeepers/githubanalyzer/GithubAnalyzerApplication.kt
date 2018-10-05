@file:JvmName("GithubAnalyzerApplication")
package by.bsuir.headkeepers.githubanalyzer

import by.bsuir.headkeepers.githubanalyzer.api.DataFetcher
import by.bsuir.headkeepers.githubanalyzer.data.bean.User
import by.bsuir.headkeepers.githubanalyzer.service.RepositoriesService
import by.bsuir.headkeepers.githubanalyzer.support.DatabaseConnection
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.LoggerFactory

private const val UPDATE_DATABASE = "UPDATE_DATABASE"
private const val RUN_APPLICATION = "RUN_APPLICATION"

private val logger = LoggerFactory.getLogger("Application")

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        return
    }
    when (args[0]) {
        UPDATE_DATABASE -> updateDatabase()
        RUN_APPLICATION -> runApplication()
        else -> return
    }
}

private fun updateDatabase() {
    val fetcher = DataFetcher()
    fetcher.exploreRepositories {
        result -> result.first?.forEach {
            val repository = it.node()!!
            if (repository is ExploreRepositoriesQuery.AsRepository) {
                println("${repository.name()} ${repository.codeOfConduct()?.name()}")
            }
        logger.info("Database updated")
    }
    }
}

private fun runApplication() {
    DatabaseConnection.open()

    transaction {
        println("All users:")
        for (user in User.all()) {
            println("${user.name}: ${user.age}")
        }

        println("All repos:")
        for (repository in RepositoriesService.findAll()) {
            println("${repository.name}: ${repository.stars.count()}: ${repository.users.count()}")
        }
    }
}