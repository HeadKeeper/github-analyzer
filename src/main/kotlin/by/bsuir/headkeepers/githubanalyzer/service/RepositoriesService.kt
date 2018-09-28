package by.bsuir.headkeepers.githubanalyzer.service

import by.bsuir.headkeepers.githubanalyzer.data.bean.Repository
import by.bsuir.headkeepers.githubanalyzer.data.bean.User
import by.bsuir.headkeepers.githubanalyzer.data.table.Repositories
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import java.util.Optional

object RepositoriesService : BeanService() {

    fun create(name: String) : Repository {
        return this.create(name, DateTime.now())
    }

    fun create(name: String, creationDate: DateTime) : Repository {
        var repository: Repository? = null

        transaction {
            val founded = Repository.find {
                (Repositories.name eq name) and (Repositories.creationDate eq creationDate)
            }
            if (!founded.empty()) {
                this@RepositoriesService.logger.error("Table 'Repositories' already has the same repository.")
                repository = founded.first()
            }
            repository = Repository.new {
                this.name = name
                this.creationDate = creationDate
            }

            commit()
            close()
        }

        return repository!!
    }

    fun create(name: String, creationDate: DateTime, users: List<User>) : Repository {
        var repository: Repository? = null

        transaction {
            val founded = Repository.find {
                (Repositories.name eq name) and (Repositories.creationDate eq creationDate)
            }
            if (!founded.empty()) {
                this@RepositoriesService.logger.error("Table 'Repositories' already has the same repository.")
                repository = founded.first()
            }
            repository = Repository.new {
                this.name = name
                this.creationDate = creationDate
                this.users = SizedCollection(users)
            }
        }

        return repository!!
    }

    fun findAll() : Iterable<Repository> {
        return Repository.all().notForUpdate().toList()
    }

    fun findAllByName(name: String) : Iterable<Repository> {
        return Repository.find {
            Repositories.name match "%$name%"
        }.notForUpdate().toList()
    }

    fun findById(id: String) : Optional<Repository> {
        return Optional.ofNullable(Repository.find {
            Repositories.id eq id
        }.notForUpdate().first())
    }
}