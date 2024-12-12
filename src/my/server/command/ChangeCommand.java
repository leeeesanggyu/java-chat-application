package my.server.command;

import my.server.Message;
import my.server.Session;

public class ChangeCommand implements Command {

    @Override
    public void execute(Message message, Session session) {
        session.changeName(message.message());
    }
}
