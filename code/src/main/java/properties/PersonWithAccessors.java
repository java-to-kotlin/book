package properties;

import java.time.LocalDate;

/// begin: excerpt
public class PersonWithAccessors {
    private final String givenName;
    private final String familyName;
    private final LocalDate dateOfBirth;


    public PersonWithAccessors(
        /// mute: excerpt
        String givenName,
        String familyName,
        LocalDate dateOfBirth
    ) {
        this.givenName = givenName;
        this.familyName = familyName;
        this.dateOfBirth = dateOfBirth;
    /// resume: excerpt
    }

    public String getGivenName() {
        return givenName;
    }

    public String getFamilyName() {
        return familyName;
    }
    
    /// mute: excerpt

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getFullName() {
        return givenName + " " + familyName;
    }
    /// resume: excerpt
}
/// end: excerpt
