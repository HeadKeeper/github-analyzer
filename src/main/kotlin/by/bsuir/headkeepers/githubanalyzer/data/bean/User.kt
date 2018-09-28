package by.bsuir.headkeepers.githubanalyzer.data.bean

import by.bsuir.headkeepers.githubanalyzer.data.table.Users
import by.bsuir.headkeepers.githubanalyzer.data.table.UsersRepositories
import org.jetbrains.exposed.dao.*
import java.util.*

class User(id: EntityID<String>) : Entity<String>(id) {
    companion object : EntityClass<String, User>(Users)

    var name by Users.name
    var age by Users.age
    var repositories by Repository via UsersRepositories
}