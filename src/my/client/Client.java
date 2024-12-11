package my.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static util.Logger.log;
import static util.SocketCloseUtil.closeAll;

class Client {

    private final String host;
    private final int port;

    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    private ReadHandler readHandler;
    private WriteHandler writeHandler;
    private boolean closed = false;

    Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    void start() throws IOException {
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

    synchronized void close() {
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
