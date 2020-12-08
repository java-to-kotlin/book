package properties;

public class Calling {
    /// begin: excerpt
    public static void print(PersonWithPublicFields person) {
        System.out.println(person.givenName); // <1>
    }

    public static void print(PersonWithAccessor person) {
        System.out.println(person.getGivenName()); // <2>
    }

    public static void print(PersonWithProperties person) {
        System.out.println(person.getGivenName()); // <3>
    }
    /// end: excerpt
}
