package encapsulation

import extensionFunctions.SOME_CODE

object IndividualMethod {
    /// begin: one_method
    class Organiser(
        private val listing: (Folder) -> List<Email>
    ) {
        fun subjectsIn(folder: Folder): List<String> {
            return listing(folder).map { it.subject }
        }
    }

    val emailSystem: EmailSystem = SOME_CODE()
    val organiser = Organiser(emailSystem::list)
    /// end: one_method
}

object IndividualMethods {
    val emailSystem: EmailSystem = SOME_CODE()
    /// begin: two_methods
    class Organiser(
        private val listing: (Folder) -> List<Email>,
        private val deleting: (Email) -> Unit
    ) {
        fun deleteInternal(folder: Folder) {
            listing(rootFolder).forEach {
                if (it.to.isInternal()) {
                    deleting.invoke(it)
                }
            }
        }
    }

    val organiser = Organiser(
        emailSystem::list,
        emailSystem::delete
    )
    /// end: two_methods
}

object DirectDependency {
    val emailSystem: EmailSystem = SOME_CODE()
    /// begin: direct_dependency
    class Organiser(
        private val emails: EmailSystem
    ) {
        fun organise() {
            emails.list(rootFolder).forEach {
                if (it.to.isInternal()) {
                    emails.delete(it)
                } else {
                    emails.move(it, archiveFolder)
                }
            }
        }
    }

    val organiser = Organiser(emailSystem)
    /// end: direct_dependency
}

object NarrowingInterface {
    val emailSystem: EmailSystem = SOME_CODE()
    /// begin: interface
    class Organiser(
        private val emails: Dependencies
    ) {
        interface Dependencies {
            fun delete(email: Email)
            fun list(folder: Folder): List<Email>
            fun move(email: Email, to: Folder)
        }

        fun organise() {
            emails.list(rootFolder).forEach {
                if (it.to.isInternal()) {
                    emails.delete(it)
                } else {
                    emails.move(it, archiveFolder)
                }
            }
        }
    }

    val organiser = Organiser(object : Organiser.Dependencies {
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
    /// end: interface
}

object NarrowingObject {
    val emailSystem: EmailSystem = SOME_CODE()
    /// begin: object
    class Organiser(
        private val emails: Dependencies
    ) {
        class Dependencies(
            val delete: (Email) -> Unit,
            val list: (folder: Folder) -> List<Email>,
            val move: (email: Email, to: Folder) -> Unit
        )

        fun organise() {
            emails.list(rootFolder).forEach {
                if (it.to.isInternal()) {
                    emails.delete(it)
                } else {
                    emails.move(it, archiveFolder)
                }
            }
        }
    }

    val organiser = Organiser(
        Organiser.Dependencies(
            delete = emailSystem::delete,
            list = emailSystem::list,
            move = emailSystem::move
        )
    )
    /// end: object

}

private fun EmailAddress.isInternal(): Boolean = SOME_CODE()

