package my.client;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

import static util.Logger.log;

public class ReadHandler implements Runnable {

    private final Socket socket;
    private final DataInputStream input;

    public ReadHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.input = new DataInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        while (true) {
            try {
                String received = input.readUTF();
                log(received);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
