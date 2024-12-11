package my.client;

import java.io.IOException;

public class ClientMain {

    private static final String HOST = "localhost";
    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException {
        Client client = new Client(HOST, PORT);
        client.start();
    }
}
