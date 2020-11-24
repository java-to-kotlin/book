package encapsulation

import encapsulation.AA.EmailSystem
import java.net.InetAddress

object AA {
    /// begin: foo
    class EmailSystem(
        val send: (Email) -> Unit,
        val delete: (Email) -> Unit,
        val list: () -> List<Email>
    )
    /// end: foo

    /// begin: bar
    fun sendDistress(sender: EmailSystem) {
        sender.send(
            Email("support@internationalrescue.org", "Travelator Customer Incident", "...")
        )
    }
    /// end: bar
}

object AAA {
    /// begin: baz
    fun sendDistress(sender: EmailSystem) {
        sender.send.invoke(
            Email("support@internationalrescue.org", "Travelator Customer Incident", "...")
        )
    }
    /// end: baz
}

