package properties;

public class CallingComputed {
    /// begin: excerpt
    public static void print2(PersonWithPublicFields person) {
        System.out.println(
            person.givenName + " " +
            person.getFullName()
        );
    }
    /// end: excerpt
}
