package journal;

import java.text.ParseException;

/**
 * Represents type of event happened in a day.
 *
 * @author choondrise
 */
public enum EventType {
    /**
     * smoking
     */
    Z,

    /**
     * alcohol
     */
    A,

    /**
     * nothing
     */
    N,

    /**
     * mixed a and z
     */
    M;

    public static EventType parse(String type) throws ParseException {
        return switch(type) {
            case "Z" -> EventType.Z;
            case "A" -> EventType.A;
            case "N" -> EventType.N;
            case "M" -> EventType.M;
            default -> throw new ParseException("Cannot parse into event type", 0);
        };
    }
}
