import java.io.*;
import java.net.*;
import java.util.*;

class group_slave {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("localhost", 9001);

        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter your name: ");
        out.println(sc.nextLine());

        String msg;
        while ((msg = in.readLine()) != null)
            System.out.println(msg);
    }
}
