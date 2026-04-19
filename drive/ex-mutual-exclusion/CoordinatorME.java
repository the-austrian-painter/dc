import java.util.*;

class Process {
    int id;
    int priority;

    Process(int id, int priority) {
        this.id = id;
        this.priority = priority;
    }
}

class Coordinator {
    PriorityQueue<Process> queue;

    Coordinator() {
        queue = new PriorityQueue<>((a, b) -> b.priority - a.priority);
    }

    void requestAccess(Process p) {
        System.out.println("Process " + p.id + " sends REQUEST");
        queue.add(p);
    }

    void grantAccess() throws InterruptedException {
        while (!queue.isEmpty()) {
            Process p = queue.poll();

            System.out.println("\nCoordinator GRANTS access to Process " + p.id);
            System.out.println("Process " + p.id + " ENTERING Critical Section");

            Thread.sleep(1000);

            System.out.println("Process " + p.id + " RELEASES Critical Section");
        }
    }
}

public class CoordinatorME {
    public static void main(String args[]) throws InterruptedException {
        Scanner sc = new Scanner(System.in);
        Coordinator coord = new Coordinator();

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter priority for Process " + (i + 1) + ": ");
            int p = sc.nextInt();

            Process proc = new Process(i + 1, p);
            coord.requestAccess(proc);
        }

        coord.grantAccess();

        System.out.println("\nAll processes completed execution.");
    }
}