import java.io.*;

class BullyAlg {
    int p[];
    int n;
    int cood;

    void election(int initiator) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("\nThe Coordinator Has Crashed");
        int crash = 0;

        for (int i = 0; i < n; i++) {
            if (p[i] == 1)
                crash++;
        }

        if (crash == n) {
            System.out.println("*** All Processes Are Crashed ***");
            return;
        }

        System.out.println("\nEnter The Initiator:");
        int init = Integer.parseInt(br.readLine());

        for (int i = init - 1; i < n; i++) {
            if (i == init - 1)
                System.out.println("\nProcess " + (i + 1) + " Called For Election");
            for (int j = init; j < n; j++) {
                if (p[j] == 0)
                    System.out.println("Process " + (j + 1) + " Is Dead");
                else
                    System.out.println("Process " + (j + 1) + " Is In");
            }
        }

        for (int i = n - 1; i >= 0; i--) {
            if (p[i] == 1) {
                cood = i + 1;
                System.out.println("\n*** New Coordinator Is " + cood + " ***");
                break;
            }
        }
    }

    public void bully() throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter The Number Of Processes:");
        n = Integer.parseInt(br.readLine());

        p = new int[n];

        for (int i = 0; i < n; i++)
            p[i] = 1;

        cood = n;

        int ch;

        do {

            System.out.println("\n1. Crash A Process");
            System.out.println("2. Recover A Process");
            System.out.println("3. Display Coordinator");
            System.out.println("4. Exit");

            System.out.print("Enter Your Choice:");
            ch = Integer.parseInt(br.readLine());

            switch (ch) {

                case 1:
                    System.out.print("Enter A Process To Crash:");
                    int cp = Integer.parseInt(br.readLine());

                    if (cp <= 0 || cp > n)
                        System.out.println("Invalid Process");
                    else if (p[cp - 1] == 1) {

                        p[cp - 1] = 0;

                        System.out.println("\nProcess " + cp + " Has Been Crashed");

                        if (cp == cood)
                            election(cp);
                    } else
                        System.out.println("\nProcess Already Crashed");
                    break;

                case 2:
                    System.out.print("Enter The Process To Recover:");
                    int rp = Integer.parseInt(br.readLine());

                    if (rp <= 0 || rp > n)
                        System.out.println("Invalid Process");
                    else if (p[rp - 1] == 0) {

                        p[rp - 1] = 1;

                        System.out.println("\nProcess " + rp + " Has Recovered");

                        if (rp > cood) {
                            cood = rp;
                            System.out.println("Process " + rp + " Is The New Coordinator");
                        }

                    } else
                        System.out.println("\nProcess Is Not Crashed");
                    break;

                case 3:
                    System.out.println("\nCurrent Coordinator Is " + cood);
                    break;

                case 4:
                    System.exit(0);

                default:
                    System.out.println("Invalid Choice");
            }

        } while (ch < 4);
    }

    public static void main(String args[]) throws IOException {
        BullyAlg b = new BullyAlg();
        b.bully();
    }
}
