import java.util.*;

class Berkley {

    static int toSec(int h, int m, int s) {
        return h*3600 + m*60 + s;
    }

    static String toTime(int t) {
        int h = (t / 3600) % 24;
        int m = (t % 3600) / 60;
        int s = t % 60;
        return h + ":" + m + ":" + s;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of nodes: ");
        int n = sc.nextInt();

        System.out.println("Enter master time:");
        int mh = sc.nextInt(), mm = sc.nextInt(), ms = sc.nextInt();
        int master = toSec(mh, mm, ms);

        int[] nodes = new int[n];
        int[] diff = new int[n];

        for (int i = 0; i < n; i++) {
            System.out.println("Enter time for node " + (i+1) + ":");
            int h = sc.nextInt(), m = sc.nextInt(), s = sc.nextInt();
            nodes[i] = toSec(h, m, s);
            diff[i] = nodes[i] - master;
        }

        System.out.println("\nTime differences (in sec):");
        int sum = 0;
        for (int i = 0; i < n; i++) {
            System.out.println("Node " + (i+1) + ": " + diff[i]);
            sum += diff[i];
        }

        int avg = sum / n;
        System.out.println("Average offset = " + avg);

        System.out.println("\nAdjusted times:");
        for (int i = 0; i < n; i++) {
            int newTime = nodes[i] - diff[i] - avg;
            System.out.println("Node " + (i+1) + ": " + toTime(newTime));
        }

        System.out.println("All clocks synchronized successfully.");
    }
}
