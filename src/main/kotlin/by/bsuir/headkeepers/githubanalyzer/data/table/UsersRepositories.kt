package by.bsuir.headkeepers.githubanalyzer.data.table

import org.jetbrains.exposed.sql.Table

object UsersRepositories : Table() {
    val repository = reference("repository", Repositories).primaryKey(0)
    val user = reference("user", Users).primaryKey(1)
}