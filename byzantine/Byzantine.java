import java.util.*;

interface Replica {
    String process(String req);
}

// Honest node
class Honest implements Replica {
    public String process(String req) {
        return "Processed: " + req;
    }
}

// Faulty (Byzantine) node
class Faulty implements Replica {
    public String process(String req) {
        return Math.random() > 0.5 ?
            "Error in " + req :
            "Fake: " + req;
    }
}

// Client for consensus
class Client {
    List<Replica> nodes;

    Client(List<Replica> nodes) {
        this.nodes = nodes;
    }

    void send(String req) {
        Map<String, Integer> count = new HashMap<>();

        System.out.println("Sending request...\n");

        for (Replica r : nodes) {
            String res = r.process(req);
            System.out.println(res);
            count.put(res, count.getOrDefault(res, 0) + 1);
        }

        // majority check
        String result = null;
        int max = 0;

        for (String k : count.keySet()) {
            if (count.get(k) > max) {
                max = count.get(k);
                result = k;
            }
        }

        System.out.println("\nFinal Decision: " + result);
    }
}

public class Byzantine {
    public static void main(String[] args) {
        List<Replica> list = new ArrayList<>();

        list.add(new Honest());
        list.add(new Honest());
        list.add(new Honest());
        list.add(new Faulty());   // faulty node

        new Client(list).send("OperationX");
    }
}
