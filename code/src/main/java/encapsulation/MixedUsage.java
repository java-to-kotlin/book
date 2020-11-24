package encapsulation;

import java.net.InetAddress;
import java.util.function.Consumer;

import static encapsulation.EncapsulationKt.inetAddress;
import static encapsulation.EmailSenderI.EmailSender;
import static encapsulation.EncapsulationKt.sendEmail;

public class MixedUsage {
    static {
        /// begin: foo
        ISendEmail instance = new EmailSender(
            inetAddress("smtp.travelator.com"),
            "username",
            "password"
        );
        Consumer<Email> sender = email -> instance.send(email);
        /// end: foo
    }

    static {
        /// begin: bar
        ISendEmail instance = new EmailSender(
            inetAddress("smtp.travelator.com"),
            "username",
            "password"
        );
        Consumer<Email> sender = instance::send;
        /// end: bar
    }
}

class FOO {
    /// begin: consumer
    public class EmailSender
        implements ISendEmail,
            Consumer<Email> // <1>
    {
        /// mute: consumer
        private final InetAddress serverAddress;
        private final String username;
        private final String password;

        public EmailSender(
             InetAddress serverAddress,
             String username,
             String password
        ) {
            this.serverAddress = serverAddress;
            this.username = username;
            this.password = password;
        }

        /// resume: consumer
        @Override
        public void accept(Email email) { // <2>
            send(email);
        }

        @Override
        public void send(Email email) {
            sendEmail(email, serverAddress, username, password);
        }
    }
    /// end: consumer
}
