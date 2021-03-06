package encapsulation.typeAlias

import encapsulation.Email
import encapsulation.sendEmail
import java.net.InetAddress

/// begin: typealias
typealias EmailSenderFunction = (Email) -> Unit

class EmailSender(
    /// mute: typealias
    private val serverAddress: InetAddress,
    private val username: String,
    private val password: String
    /// resume: typealias
) : EmailSenderFunction {
    override fun invoke(email: Email) {
        sendEmail(
            email,
            serverAddress,
            username,
            password
        )
    }
}
/// end: typealias