package by.bsuir.headkeepers.githubanalyzer.support

import org.jetbrains.exposed.sql.Database

object DatabaseConnection {
    private var opened = false

    fun open() {
        if (opened) {
            return
        }
        Database.connect("jdbc:h2:file:./database/git-data", driver = "org.h2.Driver")
        opened = true
    }

    fun openInMem() {
        if (opened) {
            return
        }
        Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
        opened = true
    }

    fun isOpened() : Boolean {
        return opened
    }
}