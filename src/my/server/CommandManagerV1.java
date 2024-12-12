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
    public void execute(Message message, Session session) throws IOException {
        switch (message.command()) {
            case EXIT -> throw new IOException();
            case CommandType.JOIN -> {
                session.changeName(message.message());
                session.sendAll(session.getName() + "님이 입장했습니다.");
            }
            case CommandType.CHANGE -> session.changeName(message.message());
            case CommandType.USERS -> {
                List<String> sessionNames = sessionManager.getNames();
                session.send(String.valueOf(sessionNames));
            }
            case CommandType.MESSAGE -> session.sendAll("[" + session.getName() + "]: " + message.message());
            default -> log("message : " + message.message());
        }
    }
}
