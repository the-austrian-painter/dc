import java.io.*;
import java.net.*;
import java.util.*;

class AFS_Client {
    public static void main(String[] a) throws Exception {
        Socket s = new Socket("localhost",5555);
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("1.Write 2.Read 3.Exit");
            int ch = sc.nextInt(); sc.nextLine();

            if (ch==1) {
                System.out.print("File: "); String f=sc.nextLine();
                System.out.print("Data: "); String d=sc.nextLine();
                out.println("write "+f+" "+d);
                System.out.println(in.readLine());
            }
            else if (ch==2) {
                System.out.print("File: "); String f=sc.nextLine();
                out.println("read "+f);
                System.out.println(in.readLine());
            }
            else break;
        }
    }
}
