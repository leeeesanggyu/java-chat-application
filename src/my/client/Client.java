package my.client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

import static util.Logger.log;

public class Client {

    private static final int PORT = 12345;

    public static void main(String[] args) {
        log("클라이언트 시작");

        try(Socket socket = new Socket("localhost", PORT);
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream())) {

            log("소켓 연결: " + socket);
            Scanner scanner = new Scanner(System.in);
            onboarding(scanner, output);
            
            while (true) {
                System.out.println("전송 문자: ");
                String toSend = scanner.nextLine();

                output.writeUTF(toSend);
                log("client -> server: " + toSend);

                if (toSend.equals("exit")) {
                    break;
                }

                String received = input.readUTF();
                log("client <- server: " + received);
            }
        } catch (IOException e) {
            log(e);
        }
    }

    private static void onboarding(Scanner scanner, DataOutputStream output) throws IOException {
        System.out.println("사용자의 이름을 입력하세요. 명령어: /join|{name}");
        String nameCommand = scanner.nextLine();
        output.writeUTF(nameCommand);
    }
}
