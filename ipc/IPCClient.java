import java.net.*;
import java.io.*;

class IPCClient {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 1200);

            DataInputStream in = new DataInputStream(s.getInputStream());
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            System.out.print("Enter first number: ");
            int a = Integer.parseInt(br.readLine());

            System.out.print("Enter second number: ");
            int b = Integer.parseInt(br.readLine());

            out.writeInt(a);
            out.writeInt(b);

            int result = in.readInt();
            System.out.println("Server reply: Message received: " + result);

            s.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
