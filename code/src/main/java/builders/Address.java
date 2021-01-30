package builders;

import java.util.Objects;

/// begin: excerpt
public class Address {
    private final String line1;
    private final String line2;
    private final String townOrCity;
    private final String region;
    private final String postalCode;
    private final String countryCode;

    public Address(
        String line1,
        String line2,
        String townOrCity,
        String region,
        String postalCode,
        String countryCode
    ) {
        this.line1 = line1;
        this.line2 = line2;
        this.townOrCity = townOrCity;
        this.region = region;
        this.postalCode = postalCode;
        this.countryCode = countryCode;
    }
    /// mute: excerpt [... getters, equals, hashCode]
    public String getLine1() {
        return line1;
    }

    public String getLine2() {
        return line2;
    }

    public String getTownOrCity() {
        return townOrCity;
    }

    public String getRegion() {
        return region;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return line1.equals(address.line1) && line2.equals(address.line2) && townOrCity.equals(address.townOrCity) && region.equals(address.region) && postalCode.equals(address.postalCode) && countryCode.equals(address.countryCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(line1, line2, townOrCity, region, postalCode, countryCode);
    }
    /// resume: excerpt

    /// begin: withPostalCode
    public Address withPostalCode(String newValue) {
        return new Address(
            getLine1(),
            getLine2(),
            getTownOrCity(),
            getRegion(),
            newValue,
            getCountryCode()
        );
    }
    /// end: withPostalCode
}
/// end: excerpt
