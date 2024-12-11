package my.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static util.Logger.log;
import static util.SocketCloseUtil.closeAll;

public class Client {

    private final String host;
    private final int port;

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    private ReadHandler readHandler;
    private WriteHandler writeHandler;
    private boolean closed = false;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws IOException {
        log("클라이언트 시작");
        socket = new Socket(host, port);
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());

        readHandler = new ReadHandler(this, input);
        writeHandler = new WriteHandler(this, output);

        Thread readThread = new Thread(readHandler, "readHandler");
        readThread.start();

        Thread writeThread = new Thread(writeHandler, "writeHandler");
        writeThread.start();
    }

    public synchronized void close() {
        if (closed) {
            return;
        }

        writeHandler.close();
        readHandler.close();
        closeAll(socket, input, output);
        closed = true;
        log("연결 종료: " + socket);

    }
}
