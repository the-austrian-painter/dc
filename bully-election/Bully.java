import java.util.*;

class Bully {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        System.out.print("Enter failed coordinator: ");
        int failed = sc.nextInt();

        System.out.print("Enter initiator: ");
        int init = sc.nextInt();

        System.out.println("\nProcess " + init + " detects failure of " + failed);
        System.out.println("Election messages sent to:");

        int coord = init;

        for (int i = init + 1; i <= n; i++) {
            System.out.print(i + " ");
        }
        System.out.println();

        for (int i = init + 1; i <= n; i++) {
            if (i == failed)
                System.out.println("Process " + i + " failed");
            else {
                System.out.println("Process " + i + " OK");
                coord = i;
            }
        }

        System.out.println("\nNew Coordinator: Process " + coord);
    }
}
