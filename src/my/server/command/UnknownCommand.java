package my.server.command;

import my.server.Message;
import my.server.Session;

public class UnknownCommand implements Command {

    @Override
    public void execute(Message message, Session session) {
        session.send("처리할 수 없는 명령어입니다: " + message.input());
    }
}
