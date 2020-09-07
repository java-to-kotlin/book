package encapsulation;

import java.net.InetAddress;
import java.util.function.Consumer;

import static encapsulation.EncapsulationKt.inetAddress;
import static encapsulation.EncapsulationKt.sendEmail;
import static encapsulation.FPUsageLambda.createEmailSender;

public class FPUsage {
    /// begin: foo
    // Where we know the configuration

    Consumer<Email> sender = createEmailSender(
        inetAddress("example.com"),
        "username",
        "password"
    );

    // Where we send the message

    public static void sendDistress(Consumer<Email> sender) {
        sender.accept( // <1>
            new Email("support@internationalrescue.org", "Travelator Customer Incident", "...")
        );
    }
    /// end: foo
}

class FPUsageLongHand {
    /// begin: FPUsageLongHand
    static Consumer<Email> createEmailSender(
        InetAddress serverAddress,
        String username,
        String password
    ) {
        return new Consumer<Email>() {
            @Override
            public void accept(Email email) {
                sendEmail(email, serverAddress, username, password);
            }
        };
    }
    /// end: FPUsageLongHand
}

class FPUsageLambda {
    /// begin: FPUsageLambda
    static Consumer<Email> createEmailSender(
        InetAddress serverAddress,
        String username,
        String password
    ) {
        return email -> sendEmail(email, serverAddress, username, password);
    }
    /// end: FPUsageLambda
}



