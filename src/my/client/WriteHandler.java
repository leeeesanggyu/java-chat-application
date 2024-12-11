package my.client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import static util.Logger.log;

public class WriteHandler implements Runnable {

    private final Client client;
    private final DataOutputStream output;
    private boolean closed;

    public WriteHandler(Client client, DataOutputStream output) throws IOException {
        this.client = client;
        this.output = output;
    }

    @Override
    public void run() {
        try {
            Scanner scanner = new Scanner(System.in);
            onboarding(scanner, output);

            while (true) {
                String toSend = scanner.nextLine();
                if (!toSend.startsWith("/")) {
                    output.writeUTF("/message|" + toSend);
                } else {
                    output.writeUTF(toSend);
                }

                if (toSend.equals("exit")) {
                    break;
                }
            }
        } catch (IOException | NoSuchElementException e) {
            log(e);
        } finally {
            client.close();
        }
    }

    private void onboarding(Scanner scanner, DataOutputStream output) throws IOException {
        System.out.println("사용자의 이름을 입력하세요: ");
        String nameInput = scanner.nextLine();
        output.writeUTF("/join|" + nameInput);
    }

    public synchronized void close() {
        if (closed) {
            return;
        }

        try {
            System.in.close();
        } catch (IOException e) {
            log(e);
        }
        closed = true;
        log("WriteHandler 종료");
    }
}
