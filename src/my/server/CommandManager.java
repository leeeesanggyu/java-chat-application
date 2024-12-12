package my.server;

import java.io.IOException;

interface CommandManager {

    void execute(Message message, Session session) throws IOException;
}
