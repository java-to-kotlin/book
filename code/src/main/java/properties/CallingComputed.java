package properties;

public class CallingComputed {
    /// begin: excerpt
    public static String fieldAndAccessor(PersonWithPublicFields person) {
        return
            person.givenName + " " +
            person.getFullName();
    }
    /// end: excerpt
}
