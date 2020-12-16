package properties;

import java.time.LocalDate;

/// begin: excerpt
public class PersonWithAccessors {
    private final String givenName;
    private final String familyName;
    private final LocalDate dateOfBirth;

    public PersonWithAccessors(
        String givenName,
        String familyName,
        LocalDate dateOfBirth
    ) {
        this.givenName = givenName;
        this.familyName = familyName;
        this.dateOfBirth = dateOfBirth;
    }

    public String getGivenName() {
        return givenName;
    }

    /// mute: excerpt
    public String getFamilyName() {
        return familyName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getFullName() {
        return givenName + " " + familyName;
    }
    /// resume: excerpt
}
/// end: excerpt
