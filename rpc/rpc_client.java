import java.io.*;
import java.net.*;

class rpc_client {
    public static void main(String[] args) throws Exception {
        Socket s = new Socket("127.0.0.1", 3000);

        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Client ready, type and press Enter key");

        while (true) {
            System.out.print("\nEnter operation to perform(add,sub,mul,div)....\n");
            out.println(kb.readLine().toLowerCase());

            System.out.print("Enter first parameter : ");
            out.println(kb.readLine());

            System.out.print("Enter second parameter : ");
            out.println(kb.readLine());

            System.out.println(in.readLine());
        }
    }
}
