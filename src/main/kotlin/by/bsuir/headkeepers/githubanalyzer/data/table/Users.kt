package by.bsuir.headkeepers.githubanalyzer.data.table

import by.bsuir.headkeepers.githubanalyzer.data.support.StringIdTable

object Users : StringIdTable("users") {
    val name = varchar("name", 50).index()
    val age = integer("age")
}