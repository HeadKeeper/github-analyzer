package by.bsuir.headkeepers.githubanalyzer.data.table

import by.bsuir.headkeepers.githubanalyzer.data.support.StringIdTable

object Stars: StringIdTable("stars") {
    val creationDate = date("creation_date")
    val user = reference("user", Users)
    val repository = reference("repository", Repositories)
}