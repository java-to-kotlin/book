package encapsulation

import encapsulation.AA.EmailSystem
import encapsulation.EmailAddress.Companion.parse

object AA {
    /// begin: foo
    class EmailSystem(
        val send: (Email) -> Unit,
        val delete: (Email) -> Unit,
        val list: (folder: Folder) -> List<Email>,
        val move: (email: Email, to: Folder) ->  Unit
    )
    /// end: foo

    /// begin: bar
    fun sendThanks(sender: EmailSystem) {
        sender.send(
            Email(
                to = parse("support@internationalrescue.org"),
                from = parse("support@travelator.com"),
                subject = "Thanks for your help",
                body = "..."
            )
        )
    }
    /// end: bar
}

object AAA {
    /// begin: baz
    fun sendThanks(sender: EmailSystem) {
        sender.send.invoke(
            Email(
                to = parse("support@internationalrescue.org"),
                from = parse("support@travelator.com"),
                subject = "Thanks for your help",
                body = "..."
            )
        )
    }
    /// end: baz
}

