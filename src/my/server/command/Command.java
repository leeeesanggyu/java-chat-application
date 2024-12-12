package my.server.command;

import my.server.Message;
import my.server.Session;

import java.io.IOException;

public interface Command {

    void execute(Message message, Session session) throws IOException;
}
