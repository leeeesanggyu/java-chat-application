package my.server;

import my.server.command.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class CommandManagerV2 implements CommandManager {

    private final Map<CommandType, Command> commands = new HashMap<>();
    private final UnknownCommand unknownCommand = new UnknownCommand();

    public CommandManagerV2(SessionManager sessionManager) {
        commands.put(CommandType.EXIT, new ExitCommand());
        commands.put(CommandType.JOIN, new JoinCommand());
        commands.put(CommandType.CHANGE, new ChangeCommand());
        commands.put(CommandType.USERS, new UsersCommand(sessionManager));
        commands.put(CommandType.MESSAGE, new MessageCommand());
        commands.put(CommandType.UNKNOWN, unknownCommand);
    }

    @Override
    public void execute(Message message, Session session) throws IOException {
        commands.getOrDefault(message.command(), unknownCommand)
                .execute(message, session);
    }
}
