package by.bsuir.headkeepers.githubanalyzer.data.bean

import by.bsuir.headkeepers.githubanalyzer.data.table.Repositories
import by.bsuir.headkeepers.githubanalyzer.data.table.Stars
import by.bsuir.headkeepers.githubanalyzer.data.table.UsersRepositories
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import java.util.UUID

class Repository(id: EntityID<String>) : Entity<String>(id) {
    companion object : EntityClass<String, Repository>(Repositories)

    var name by Repositories.name
    var creationDate by Repositories.creationDate
    var users by User via UsersRepositories

    val stars by Star referrersOn Stars.repository
}