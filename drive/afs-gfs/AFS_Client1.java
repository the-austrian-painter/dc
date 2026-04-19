import java.io.*;
import java.net.*;

public class AFS_Client1 {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 5555;

    public static void main(String[] args) {

        try (Socket socket = new Socket(SERVER_HOST, SERVER_PORT);
             BufferedReader userInput = new BufferedReader(
                     new InputStreamReader(System.in));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(
                     new InputStreamReader(socket.getInputStream()))) {

            String userCommand;

            while (true) {
                // Display menu
                System.out.println("\n--- AFS Client Menu ---");
                System.out.println("1. Write to file");
                System.out.println("2. Read from file");
                System.out.println("3. Exit");
                System.out.print("Enter your choice: ");

                userCommand = userInput.readLine();

                if (userCommand.equals("1")) {
                    System.out.print("Enter filename: ");
                    String filename = userInput.readLine();

                    System.out.print("Enter content to write: ");
                    String content = userInput.readLine();

                    String command = "write " + filename + " " + content;
                    out.println(command);

                    System.out.println("Server response: " + in.readLine());

                } else if (userCommand.equals("2")) {
                    System.out.print("Enter filename: ");
                    String filename = userInput.readLine();

                    String command = "read " + filename;
                    out.println(command);

                    System.out.println("Server response: " + in.readLine());

                } else if (userCommand.equals("3")) {
                    out.println("exit");
                    System.out.println("Server response: " + in.readLine());
                    break;

                } else {
                    System.out.println("Invalid choice, try again.");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
