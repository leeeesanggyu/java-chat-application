package my.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import static util.Logger.log;
import static util.SocketCloseUtil.closeAll;

public class Session implements Runnable {

    public String name = "unknown";
    private boolean closed = false;

    private final Socket socket;
    private final DataInputStream input;
    private final DataOutputStream output;
    private final SessionManager sessionManager;

    public Session(Socket socket, SessionManager sessionManager) throws IOException {
        this.socket = socket;
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
        this.sessionManager = sessionManager;
        this.sessionManager.add(this);
    }

    @Override
    public void run() {
        try {
            while (true) {
                String received = input.readUTF();
                Command command = Command.of(received);
                if (command.command() == CommandType.EXIT) {
                    break;
                }

                switch (command.command()) {
                    case CommandType.JOIN -> name = command.message();
                    case CommandType.USERS -> {
                        List<String> sessionNames = sessionManager.getNames();
                        output.writeUTF(String.valueOf(sessionNames));
                    }
                    case CommandType.MESSAGE -> sessionManager.sendAll(name + ": " + command.message());
                    case CommandType.CHANGE -> name = command.message();
                    default -> log("message : " + received);
                }
            }
        } catch (IOException e) {
            log(e);
        } finally {
            sessionManager.remove(this);
            close();
        }
    }

    public String getName() {
        return name;
    }

    public synchronized void close() {
        if (closed) {
            return;
        }
        closeAll(socket, input, output);
        closed = true;
        log("연결 종료: " + socket);
    }

    public void send(String message) {
        try {
            output.writeUTF(message);
        } catch (IOException e) {
            log(e);
        }
    }
}
