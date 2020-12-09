package properties;

public class Calling {
    /// begin: excerpt
    public static String accessField(PersonWithPublicFields person) {
        return person.givenName;
    }

    public static String callAccessor(PersonWithAccessor person) {
        return person.getGivenName();
    }

    public static String callKotlinAccessor(PersonWithProperties person) {
        return person.getGivenName();
    }
    /// end: excerpt
}
