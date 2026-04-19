import java.io.*;
import java.net.*;
import java.nio.file.*;

class AFS_Server {
    static String DIR = "afs_files/";

    public static void main(String[] a) throws Exception {
        new File(DIR).mkdirs();
        ServerSocket ss = new ServerSocket(5555);
        System.out.println("Server started...");

        while (true) {
            Socket s = ss.accept();

            new Thread(() -> {
                try {
                    BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    PrintWriter out = new PrintWriter(s.getOutputStream(), true);

                    String req;
                    while ((req = in.readLine()) != null) {
                        if (req.startsWith("write")) {
                            String[] p = req.split(" ",3);
                            Files.write(Paths.get(DIR+p[1]), p[2].getBytes());
                            out.println("Written");
                        }
                        else if (req.startsWith("read")) {
                            String[] p = req.split(" ");
                            Path f = Paths.get(DIR+p[1]);
                            out.println(Files.exists(f) ? new String(Files.readAllBytes(f)) : "Not found");
                        }
                        else break;
                    }
                } catch (Exception e) {}
            }).start();
        }
    }
}
