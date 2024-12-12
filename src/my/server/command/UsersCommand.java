package my.server.command;

import my.server.Message;
import my.server.Session;
import my.server.SessionManager;

import java.util.List;

public class UsersCommand implements Command {

    private final SessionManager sessionManager;

    public UsersCommand(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void execute(Message message, Session session) {
        List<String> sessionNames = sessionManager.getNames();
        session.send(String.valueOf(sessionNames));
    }
}
