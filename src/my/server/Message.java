package my.server;

public record Message(String input,
                      CommandType command,
                      String message) {

    private static final String DELIMITER = "\\|";

    public static Message of(String input) {
        String[] commandMessage = input.split(DELIMITER);
        CommandType command = CommandType.fromInput(commandMessage[0]);

        String message = "";
        if (commandMessage.length > 1) {
            message = commandMessage[1];
        }
        return new Message(input, command, message);
    }
}
