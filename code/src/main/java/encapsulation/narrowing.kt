package encapsulation

object NarrowingInterface {
    val emailSystem: EmailSystem = TODO()

    /// begin: interface
    interface MyDependencies {
        fun delete(email: Email)
        fun list(folder: Folder): List<Email>
        fun move(email: Email, to: Folder)
    }

    fun organise(emails: MyDependencies) {
        emails.list(rootFolder).forEach {
            if (it.to.isEmpty()) {
                emails.delete(it)
            } else {
                emails.move(it, archiveFolder)
            }
        }
    }

    fun organise(emailSystem: EmailSystem) {
        organise(object : MyDependencies {
            override fun delete(email: Email) {
                emailSystem.delete(email)
            }
            override fun list(folder: Folder): List<Email> {
                return emailSystem.list(folder)
            }
            override fun move(email: Email, to: Folder) {
                emailSystem.move(email, to)
            }
        })
    }
    /// end: interface
}

object NarrowingObject {
    val emailSystem: EmailSystem = TODO()

    /// begin: object
    class MyDependencies(
        val delete: (Email) -> Unit,
        val list: (folder: Folder) -> List<Email>,
        val move: (email: Email, to: Folder) -> Unit
    )

    fun organise(emails: MyDependencies) {
        emails.list(rootFolder).forEach {
            if (it.to.isEmpty()) {
                emails.delete(it)
            } else {
                emails.move(it, archiveFolder)
            }
        }
    }

    fun organise(emailSystem: EmailSystem) {
        organise(
            MyDependencies(
                delete = emailSystem::delete,
                list = emailSystem::list,
                move = emailSystem::move
            )
        )
    }
    /// end: object

}