package encapsulation;

import java.util.function.Consumer;

/// begin: foo
class Rescuing {
    private final Consumer<Email> emailSender;

    Rescuing(Consumer<Email> emailSender) {
        this.emailSender = emailSender;
    }
    /// mute: foo
    /// resume: foo
}
/// end: foo
