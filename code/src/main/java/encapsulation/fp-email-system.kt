package encapsulation

import encapsulation.AA.EmailSystem
import java.net.InetAddress

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
                to = "support@internationalrescue.org",
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
                to = "support@internationalrescue.org",
                subject = "Thanks for your help",
                body = "..."
            )
        )
    }
    /// end: baz
}

