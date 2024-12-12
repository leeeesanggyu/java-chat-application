package my.server;

import java.io.IOException;
import java.util.List;

import static util.Logger.log;

class CommandManagerV1 implements CommandManager {

    private final SessionManager sessionManager;

    CommandManagerV1(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void execute(Command command, Session session) throws IOException {
        switch (command.command()) {
            case EXIT -> throw new IOException();
            case CommandType.JOIN -> session.changeName(command.message());
            case CommandType.CHANGE -> session.changeName(command.message());
            case CommandType.USERS -> {
                List<String> sessionNames = sessionManager.getNames();
                session.send(String.valueOf(sessionNames));
            }
            case CommandType.MESSAGE -> session.sendAll("[" + session.getName() + "]: " + command.message());
            default -> log("message : " + command.message());
        }
    }
}
