import java.util.*;

class deadlock {

    static class Message {
        int i, j, k;
        Message(int i, int j, int k) {
            this.i = i; this.j = j; this.k = k;
        }
        public String toString() {
            return "(" + i + "," + j + "," + k + ")";
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int[][] graph = new int[n][n];

        System.out.println("Enter wait-for graph:");
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                graph[i][j] = sc.nextInt();

        System.out.print("Enter initiator process: ");
        int init = sc.nextInt();

        List<Message> probes = new ArrayList<>();

        // generate probes
        for (int i = 0; i < n; i++)
            if (graph[init][i] == 1)
                probes.add(new Message(init, init, i));

        System.out.println("\nProbes:");
        System.out.println(probes);

        boolean deadlock = false;

        // check cycle
        for (Message m : probes) {
            if (m.k == init)
                deadlock = true;
        }

        if (deadlock)
            System.out.println("Deadlock Detected");
        else
            System.out.println("No Deadlock");
    }
}
