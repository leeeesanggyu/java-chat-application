package my.server.command;

import my.server.Message;
import my.server.Session;

public class MessageCommand implements Command {

    @Override
    public void execute(Message message, Session session) {
        session.sendAll("[" + session.getName() + "]: " + message.message());
    }
}
