package my.server.command;

import my.server.Message;
import my.server.Session;

public class JoinCommand implements Command {

    @Override
    public void execute(Message message, Session session) {
        session.changeName(message.message());
        session.sendAll(session.getName() + "님이 입장했습니다.");
    }
}
