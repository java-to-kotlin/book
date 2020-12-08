package properties;

import java.time.LocalDate;

/// begin: excerpt
public class PersonWithPublicFields {
    public final String givenName;
    public final String familyName;
    public final LocalDate dateOfBirth;

    public PersonWithPublicFields(
        String givenName,
        String familyName,
        LocalDate dateOfBirth
    ) {
        this.givenName = givenName;
        this.familyName = familyName;
        this.dateOfBirth = dateOfBirth;
    }
    /// mute: excerpt []

    public String getFullName() {
        return givenName + " " + familyName;
    }
    /// resume: excerpt
}
/// end: excerpt
