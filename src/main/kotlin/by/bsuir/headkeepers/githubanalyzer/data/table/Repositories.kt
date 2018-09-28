package by.bsuir.headkeepers.githubanalyzer.data.table

import by.bsuir.headkeepers.githubanalyzer.data.support.StringIdTable

object Repositories : StringIdTable("repositories") {
    var name = varchar("name", 128)
    var creationDate = date("creation_date")
}