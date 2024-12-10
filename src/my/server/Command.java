package my.server;

public record Command(CommandType command,
                      String message) {

    private static final String DELIMITER = "\\|";

    public static Command of(String input) {
        String[] commandMessage = input.split(DELIMITER);
        CommandType command = CommandType.fromInput(commandMessage[0]);

        String message = "";
        if (commandMessage.length > 1) {
            message = commandMessage[1];
        }
        return new Command(command, message);
    }
}
