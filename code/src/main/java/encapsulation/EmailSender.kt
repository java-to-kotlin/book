package encapsulation

import java.net.InetAddress


/// begin: plain
class EmailSender(
    private val serverAddress: InetAddress,
    private val username: String,
    private val password: String
) {
    fun send(email: Email) {
        sendEmail(
            email,
            serverAddress,
            username,
            password
        )
    }
}
/// end: plain

object EmailSenderI {
    /// begin: impl
    class EmailSender(
        /// mute: impl
        private val serverAddress: InetAddress,
        private val username: String,
        private val password: String
    /// resume: impl
    ) : ISendEmail {
        override fun send(email: Email) {
            sendEmail(
                email,
                serverAddress,
                username,
                password
            )
        }
    }
    /// end: impl
}

object EmailSenderInvoke {
    /// begin: invoke
    class EmailSender(
        /// mute: invoke
        private val serverAddress: InetAddress,
        private val username: String,
        private val password: String
    /// resume: invoke
    ) : ISendEmail,
        (Email) -> Unit // <1>
    {
        override operator fun invoke(email: Email) =
            send(email) // <2>

        override fun send(email: Email) {
            sendEmail(
                email,
                serverAddress,
                username,
                password
            )
        }
    }
    /// end: invoke
}

/// begin: typealias
typealias ISendEmailToo = (Email) -> Unit

/// end: typealias

object EmailSenderTypeAlias {
    /// begin: typealias
    class EmailSender(
        /// mute: typealias
        private val serverAddress: InetAddress,
        private val username: String,
        private val password: String
    /// resume: typealias
    ) : ISendEmailToo {
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
}