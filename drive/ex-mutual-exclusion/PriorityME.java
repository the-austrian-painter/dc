import java.util.*;

class Process {
    int id;
    int priority;
    int waitTime;

    Process(int id, int priority) {
        this.id = id;
        this.priority = priority;
        this.waitTime = 0;
    }
}

public class PriorityME {
    public static void main(String args[]) throws InterruptedException {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        List<Process> queue = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            System.out.print("Enter priority for Process " + (i + 1) + ": ");
            int p = sc.nextInt();
            queue.add(new Process(i + 1, p));
        }

        System.out.println("\nCoordinator scheduling with PRIORITY + AGING\n");

        while (!queue.isEmpty()) {

            // Apply aging (increase priority of waiting processes)
            for (Process p : queue) {
                p.waitTime++;
                p.priority += 1; // Aging prevents starvation
            }

            // Sort based on updated priority
            Collections.sort(queue, (a, b) -> b.priority - a.priority);

            Process current = queue.remove(0);

            System.out.println("Coordinator GRANTS access to Process " + current.id);
            System.out.println("Process " + current.id + " ENTERING Critical Section");

            Thread.sleep(1000);

            System.out.println("Process " + current.id + " LEAVING Critical Section\n");
        }

        System.out.println("All processes executed successfully (NO STARVATION)");
    }
}