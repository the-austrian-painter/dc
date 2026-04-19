import java.io.*;
import java.net.*;
import java.nio.file.*;

public class AFS_Server {

    // Directory to store files
    private static final String SERVER_DIR = "afs_files";

    public static void main(String[] args) {
        // Ensure the directory exists
        File dir = new File(SERVER_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        try (ServerSocket serverSocket = new ServerSocket(5555)) {
            System.out.println("Server started on port 5555...");

            // Handle multiple clients
            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                // Handle client in a new thread
                new ClientHandler(clientSocket).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Client handler thread
    private static class ClientHandler extends Thread {
        private final Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter writer = new PrintWriter(
                         clientSocket.getOutputStream(), true)) {

                String clientRequest;

                while ((clientRequest = reader.readLine()) != null) {
                    System.out.println("Request: " + clientRequest);

                    if (clientRequest.startsWith("write")) {
                        // Write command: write <filename> <content>
                        String[] parts = clientRequest.split(" ", 3);
                        if (parts.length == 3) {
                            String filename = parts[1];
                            String content = parts[2];
                            writeFile(filename, content);
                            writer.println("File " + filename + " written successfully.");
                        } else {
                            writer.println("Invalid write command.");
                        }

                    } else if (clientRequest.startsWith("read")) {
                        // Read command: read <filename>
                        String[] parts = clientRequest.split(" ", 2);
                        if (parts.length == 2) {
                            String filename = parts[1];
                            String content = readFile(filename);

                            if (content != null) {
                                writer.println(content);
                            } else {
                                writer.println("File " + filename + " not found.");
                            }
                        } else {
                            writer.println("Invalid read command.");
                        }

                    } else if (clientRequest.equals("exit")) {
                        writer.println("Closing connection.");
                        break;

                    } else {
                        writer.println("Unknown command.");
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void writeFile(String filename, String content) {
            try {
                Files.write(Paths.get(SERVER_DIR, filename),
                        content.getBytes(),
                        StandardOpenOption.CREATE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private String readFile(String filename) {
            try {
                Path filePath = Paths.get(SERVER_DIR, filename);
                if (Files.exists(filePath)) {
                    return new String(Files.readAllBytes(filePath));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
