import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

// Represents a generic server that can respond to requests
interface Replica {
    String processRequest(String request);
}

// A well-behaved server
class HonestReplica implements Replica {
    private final String id;

    public HonestReplica(String id) {
        this.id = id;
    }

    @Override
    public String processRequest(String request) {
        // Honest servers always return the correct response
        return "Processed: " + request;
    }
}

// A Byzantine server that might lie
class ByzantineReplica implements Replica {
    private final String id;
    private boolean isMalicious = true; // Decides to be malicious or not

    public ByzantineReplica(String id) {
        this.id = id;
    }

    @Override
    public String processRequest(String request) {
        if (isMalicious) {
            // Malicious behavior: sometimes lies
            if (Math.random() > 0.5) {
                return "Error: System failed during " + request;
            } else {
                return "FakeProcessed: " + request;
            }
        } else {
            return "Processed: " + request; // Sometimes behaves honestly
        }
    }
}

// The client that handles responses and reaches a consensus
class BFTClient {
    private final List<Replica> replicas;

    // For BFT, you need 3f + 1 total replicas to tolerate f failures
    // In this simple example, we assume we need a simple majority (quorum)
    public BFTClient(List<Replica> replicas) {
        this.replicas = replicas;
    }

    public String sendRequest(String request) {
        Map<String, AtomicInteger> counts = new HashMap<>();

        String consensusResult = null;
        int quorumSize = (replicas.size() / 2) + 1; // Simple majority quorum

        System.out.println("Client sending request to all replicas...");

        // Simulate sending requests and receiving replies
        // In a real system, this involves network communication, message signing, and sequencing
        for (Replica replica : replicas) {
            String response = replica.processRequest(request);
            System.out.println("Received from " + replica.getClass().getSimpleName() + ": " + response);

            counts.computeIfAbsent(response, k -> new AtomicInteger(0)).incrementAndGet();
        }

        // Check for consensus
        for (Map.Entry<String, AtomicInteger> entry : counts.entrySet()) {
            if (entry.getValue().get() >= quorumSize) {
                consensusResult = entry.getKey();
                break;
            }
        }

        if (consensusResult != null) {
            System.out.println("\nClient achieved consensus. Result: \"" + consensusResult + "\"");
            return consensusResult;
        } else {
            System.out.println("\nClient failed to achieve consensus. System compromised.");
            return null;
        }
    }
}

public class Byzantine {
    public static void main(String[] args) {
        // System setup: 3 Honest, 1 Byzantine (tolerates 1 fault: f=1, need 3f+1=4 nodes)
        List<Replica> network = new ArrayList<>();
        network.add(new HonestReplica("R1"));
        network.add(new HonestReplica("R2"));
        network.add(new HonestReplica("R3"));
        network.add(new ByzantineReplica("R4")); // The "traitor"

        BFTClient client = new BFTClient(network);
        client.sendRequest("OperationX");
    }
}
