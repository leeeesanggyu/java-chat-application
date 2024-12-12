package my.server;

import java.io.IOException;

interface CommandManager {

    void execute(Command command, Session session) throws IOException;
}
