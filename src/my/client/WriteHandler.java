package my.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class WriteHandler implements Runnable {

    private final Socket socket;
    private final DataOutputStream output;

    public WriteHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.output = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        try {
            while (true) {
                System.out.println("명령어를 입력하세요 :");
                Scanner scanner = new Scanner(System.in);
                String toSend = scanner.nextLine();
                output.writeUTF(toSend);

                if (toSend.equals("exit")) {
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
