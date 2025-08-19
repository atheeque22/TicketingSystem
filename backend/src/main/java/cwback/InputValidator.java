package cwback;
/**
 * Utility class for input validation.
 */
public class InputValidator {

    /**
     * Validates if a number is a positive integer.
     * @param value The value to validate.
     * @param fieldName The name of the field being validated.
     * @return Error message if validation fails; otherwise, null.
     */
    public static String validatePositiveInteger(int value, String fieldName) {
        if (value <= 0) {
            return "Error: " + fieldName + " must be a positive number.";
        }
        return null;
    }

    /**
     * Validates the configuration object from the WebSocket message.
     * @param config Configuration object containing input parameters.
     * @return Error message if validation fails; otherwise, null.
     */    public static String validateConfiguration(Configuration config) {
        // Check if the values are valid positive integers
        String validationMessage;

        validationMessage = validatePositiveInteger(config.getTotalTickets(), "Total tickets");
        if (validationMessage != null) return validationMessage;

        validationMessage = validatePositiveInteger(config.getTicketReleaseRate(), "Ticket release rate");
        if (validationMessage != null) return validationMessage;

        validationMessage = validatePositiveInteger(config.getCustomerRetrievalRate(), "Customer retrieval rate");
        if (validationMessage != null) return validationMessage;

        validationMessage = validatePositiveInteger(config.getMaxTicketCapacity(), "Max ticket capacity");
        if (validationMessage != null) return validationMessage;

        // If all checks pass, return null (indicating no error)
        return null;
    }
}
