import java.io.*;
import java.net.*;

class rpc_server {
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(3000);
        System.out.println("Server ready");

        Socket s = ss.accept();
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);

        while (true) {
            String op = in.readLine();
            int a = Integer.parseInt(in.readLine());
            int b = Integer.parseInt(in.readLine());
            int c = 0;

            if (op.equals("add")) c = a + b;
            else if (op.equals("sub")) c = a - b;
            else if (op.equals("mul")) c = a * b;
            else if (op.equals("div")) c = a / b;

            System.out.println("Operation : " + op);
            System.out.println("Result = " + c);

            out.println(
                op.equals("add") ? "Addition = " + c :
                op.equals("sub") ? "Subtraction = " + c :
                op.equals("mul") ? "Multiplication = " + c :
                "Division = " + c
            );
        }
    }
}
