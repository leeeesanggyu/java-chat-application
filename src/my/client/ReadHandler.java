package my.client;

import java.io.DataInputStream;
import java.io.IOException;

import static util.Logger.log;

public class ReadHandler implements Runnable {

    private final Client client;
    private final DataInputStream input;
    private boolean closed;

    public ReadHandler(Client client, DataInputStream input) throws IOException {
        this.client = client;
        this.input = input;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String received = input.readUTF();
                System.out.println(received);
            }
        } catch (IOException e) {
            log(e);
        } finally {
            client.close();
        }
    }

    public synchronized void close() {
        if (closed) {
            return;
        }

        closed = true;
        log("ReadHandler 종료");
    }
}
