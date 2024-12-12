package my.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static util.Logger.log;

class Server {

    private final int port;

    private SessionManager sessionManager;
    private ServerSocket serverSocket;
    private CommandManager commandManager;

    Server(int port) {
        this.port = port;
    }

    void start() throws IOException {
        log("서버 시작");
        sessionManager = new SessionManager();
        serverSocket = new ServerSocket(port);
        commandManager = new CommandManagerV1(sessionManager);
        log("서버 소켓 시작 - 리스닝 포트: " + port);

        addShutdownHook();

        try {
            while (true) {
                Socket socket = serverSocket.accept();
                log("소켓 연결: " + socket);
                Session session = new Session(socket, sessionManager, commandManager);
                new Thread(session).start();
            }
        } catch (IOException e) {
            log("서버 소켓 종료: " + e);
        }

    }

    private void addShutdownHook() {
        ShutdownHook shutdownHook = new ShutdownHook(serverSocket, sessionManager);
        Runtime.getRuntime().addShutdownHook(new Thread(shutdownHook, "shutdown"));
    }

    private class ShutdownHook implements Runnable {

        private final ServerSocket serverSocket;
        private final SessionManager sessionManager;

        private ShutdownHook(ServerSocket serverSocket, SessionManager sessionManager) {
            this.serverSocket = serverSocket;
            this.sessionManager = sessionManager;
        }

        @Override
        public void run() {
            log("shutdownHook 실행");
            try {
                sessionManager.closeAll();
                serverSocket.close();
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
}
