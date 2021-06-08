package extensionFunctions;

import com.fasterxml.jackson.databind.JsonNode;
import static extensionFunctions.MarketingStuffKt.*;

public class SomeJava {

    public static void callingExt(Customer customer) {
        /// begin: callingExt
        var s = MarketingStuffKt.nameForMarketing(customer);
        /// end: callingExt
    }

    /// begin: toCustomer
    static Customer toCustomer(JsonNode node) {
        /// mute: toCustomer
        return null;
        /// resume: toCustomer
    }
    /// end: toCustomer

    /// begin: customerFrom
    static Customer customerFrom(JsonNode node) {
        /// mute: customerFrom
        return null;
        /// resume: customerFrom
    }
    /// end: customerFrom

    public static void callingUtil(JsonNode node) {
        /// begin: calling
        var customer = customerFrom(node);
        var marketingName = nameForMarketing(customer);
        /// end: calling

        /// begin: chaining
        var marketingLength = nameForMarketing(customerFrom(node)).length();
        /// end: chaining
    }
}
