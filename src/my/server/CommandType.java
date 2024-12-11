package my.server;

public enum CommandType {
    JOIN("/join"),
    MESSAGE("/message"),
    CHANGE("/change"),
    USERS("/users"),
    EXIT("/exit"),
    UNKNOWN(""),
    ;

    private final String input;

    CommandType(String input) {
        this.input = input;
    }

    public String getInput() {
        return input;
    }

    public static CommandType fromInput(String input) {
        for (CommandType type : CommandType.values()) {
            if (type.getInput().equals(input)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}