import java.io.*;
import java.net.*;

class Server
{
    public static void main(String[] args) throws Exception
    {
        ServerSocket serSock = new ServerSocket(3000);
        System.out.println("Server ready");

        Socket sock = serSock.accept();

        BufferedReader keyRead =
                new BufferedReader(new InputStreamReader(System.in));

        OutputStream ostream = sock.getOutputStream();
        PrintWriter pwrite = new PrintWriter(ostream, true);

        InputStream istream = sock.getInputStream();
        BufferedReader receiveRead =
                new BufferedReader(new InputStreamReader(istream));

        String fun;
        int a, b, c;

        while (true)
        {
            fun = receiveRead.readLine();
            if (fun != null)
            {
                System.out.println("Operation: " + fun);

                a = Integer.parseInt(receiveRead.readLine());
                b = Integer.parseInt(receiveRead.readLine());

                System.out.println("Received: " + a + " , " + b);

                if (fun.compareTo("add") == 0)
                {
                    c = a + b;
                    System.out.println("Addition = " + c);
                    pwrite.println("Addition = " + c);
                }

                if (fun.compareTo("sub") == 0)
                {
                    c = a - b;
                    System.out.println("Subtraction = " + c);
                    pwrite.println("Subtraction = " + c);
                }

                if (fun.compareTo("mul") == 0)
                {
                    c = a * b;
                    System.out.println("Multiplication = " + c);
                    pwrite.println("Multiplication = " + c);
                }

                if (fun.compareTo("div") == 0)
                {
                    if (b != 0)
                    {
                        c = a / b;
                        System.out.println("Division = " + c);
                        pwrite.println("Division = " + c);
                    }
                    else
                    {
                        pwrite.println("Error: Division by zero");
                    }
                }
            }
        }
    }
}
