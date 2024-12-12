package my.server.command;

import my.server.Message;
import my.server.Session;

import java.io.IOException;

public class ExitCommand implements Command {

    @Override
    public void execute(Message message, Session session) throws IOException {
        throw new IOException();
    }
}
