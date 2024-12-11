package my.server;

import java.io.IOException;

public class ServerMain {

    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        Server server = new Server(PORT);
        server.start();
    }
}
