@file:JvmName("GithubAnalyzerApplication")
package by.bsuir.headkeepers.githubanalyzer

import by.bsuir.headkeepers.githubanalyzer.api.DataFetcher

fun main(args: Array<String>) {
    /*
    DatabaseConnection.open()

    transaction {
        create(Repositories, Users, UsersRepositories, Stars)

        val andrey = User.new {
            name = "Andrey"
            age = 21
        }

        val maxim: User = User.new {
            name = "Maxim"
            age = 21
        }

        val headKeeperRepo = RepositoriesService.create("HeadKeeper", DateTime.now(), listOf(andrey, maxim))

        val starByAndrey = Star.new {
            creationDate = DateTime.now()
            user = andrey
            repository = headKeeperRepo
        }

        val starByMaxim = Star.new {
            creationDate = DateTime.now()
            user = maxim
            repository = headKeeperRepo
        }

        println("All users:")
        for (user in User.all()) {
            println("${user.name}: ${user.age}")
        }

        println("All repos:")
        for (repository in RepositoriesService.findAllByName("Hea")) {
            println("${repository.name}: ${repository.stars.count()}: ${repository.users.count()}")
        }
    }
    */


    val fetcher = DataFetcher()

    fetcher.getLatestTrendingRepositoriesInLastWeek {
        result: Pair<List<GetLatestTrendingRepositoriesInLastWeekQuery.Edge>?, Error?> ->
        result.first!!.forEach { t: GetLatestTrendingRepositoriesInLastWeekQuery.Edge? ->
            val repo = t!!.node()!!
            if (repo is GetLatestTrendingRepositoriesInLastWeekQuery.AsRepository) {
                println(repo.name())
            }
        }
    }
}