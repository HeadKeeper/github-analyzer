package by.bsuir.headkeepers.githubanalyzer.data.bean

import by.bsuir.headkeepers.githubanalyzer.data.table.Stars
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import java.util.UUID

class Star(id: EntityID<String>) : Entity<String>(id) {
    companion object : EntityClass<String, Star>(Stars)

    var creationDate by Stars.creationDate
    var user by User referencedOn Stars.user
    var repository by Repository referencedOn Stars.repository
}