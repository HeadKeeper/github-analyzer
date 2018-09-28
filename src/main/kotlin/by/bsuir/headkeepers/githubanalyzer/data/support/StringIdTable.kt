package by.bsuir.headkeepers.githubanalyzer.data.support

import org.jetbrains.exposed.dao.EntityID
import org.jetbrains.exposed.dao.IdTable
import org.jetbrains.exposed.sql.Column
import java.util.UUID

open class StringIdTable(name: String = "", columnName: String = "id") : IdTable<String>(name) {
    override val id: Column<EntityID<String>> = varchar(columnName, 36).primaryKey()
        .clientDefault { UUID.randomUUID().toString() }
        .entityId()
}