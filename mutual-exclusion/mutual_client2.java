import java.io.*;
import java.net.*;
import java.util.*;

class mutual_client2 {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("localhost", 7000);
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        Socket prev = new Socket("localhost", 7001);
        BufferedReader in = new BufferedReader(
            new InputStreamReader(prev.getInputStream()));
        PrintWriter outPrev = new PrintWriter(prev.getOutputStream(), true);

        Scanner sc = new Scanner(System.in);
        String token;

        while (true) {
            System.out.println("Waiting for token...");
            token = in.readLine();

            if (token.equals("Token")) {
                System.out.print("Send data? (yes/no): ");
                if (sc.nextLine().equalsIgnoreCase("yes")) {
                    System.out.print("Enter data: ");
                    out.println(sc.nextLine());
                }
                outPrev.println("Token");
            }
        }
    }
}
