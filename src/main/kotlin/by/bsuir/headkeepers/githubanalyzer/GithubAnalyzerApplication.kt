package by.bsuir.headkeepers.githubanalyzer

import by.bsuir.headkeepers.githubanalyzer.data.bean.Repository
import by.bsuir.headkeepers.githubanalyzer.data.bean.Star
import by.bsuir.headkeepers.githubanalyzer.data.bean.User
import by.bsuir.headkeepers.githubanalyzer.data.table.Repositories
import by.bsuir.headkeepers.githubanalyzer.data.table.Stars
import by.bsuir.headkeepers.githubanalyzer.data.table.Users
import by.bsuir.headkeepers.githubanalyzer.data.table.UsersRepositories
import by.bsuir.headkeepers.githubanalyzer.service.RepositoriesService
import by.bsuir.headkeepers.githubanalyzer.support.DatabaseConnection
import org.jetbrains.exposed.sql.SchemaUtils.create
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime

fun main(args: Array<String>) {
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
}