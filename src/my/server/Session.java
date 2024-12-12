package my.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import static util.Logger.log;
import static util.SocketCloseUtil.closeAll;

class Session implements Runnable {

    String name = "unknown";
    private boolean closed = false;

    private final Socket socket;
    private final DataInputStream input;
    private final DataOutputStream output;
    private final SessionManager sessionManager;
    private final CommandManager commandManager;

    Session(Socket socket, SessionManager sessionManager, CommandManager commandManager) throws IOException {
        this.socket = socket;
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
        this.sessionManager = sessionManager;
        this.sessionManager.add(this);
        this.commandManager = commandManager;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String received = input.readUTF();
                Message message = Message.of(received);
                commandManager.execute(message, this);
            }
        } catch (IOException e) {
            log(e);
        } finally {
            sessionManager.remove(this);
            sendAll(name + "님이 퇴장했습니다.");
            close();
        }
    }

    void send(String message) {
        try {
            output.writeUTF(message);
        } catch (IOException e) {
            log(e);
        }
    }

    protected void sendAll(String message) {
        sessionManager.sendAll(message);
    }

    String getName() {
        return name;
    }

    protected void changeName(String newName) {
        this.name = newName;
    }

    synchronized void close() {
        if (closed) {
            return;
        }
        closeAll(socket, input, output);
        closed = true;
        log("연결 종료: " + socket);
    }
}
