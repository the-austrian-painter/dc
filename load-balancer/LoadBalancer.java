import java.util.*;

class LoadBalancer {

    static void show(int s, int p) {
        int base = p / s, extra = p % s;

        for (int i = 0; i < s; i++)
            System.out.println("Server " + (char)('A'+i) + " has " +
                (base + (i < extra ? 1 : 0)) + " Processes");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter servers and processes: ");
        int s = sc.nextInt(), p = sc.nextInt();

        while (true) {
            show(s, p);

            System.out.println("\n1.Add Servers  2.Remove Servers  3.Add Processes  4.Remove Processes  5.Exit");
            System.out.print("Enter choice: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    System.out.print("How many more servers? ");
                    s += sc.nextInt();
                    break;

                case 2:
                    System.out.print("How many servers to remove? ");
                    s = Math.max(1, s - sc.nextInt());
                    break;

                case 3:
                    System.out.print("How many more processes? ");
                    p += sc.nextInt();
                    break;

                case 4:
                    System.out.print("How many processes to remove? ");
                    p = Math.max(0, p - sc.nextInt());
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}
