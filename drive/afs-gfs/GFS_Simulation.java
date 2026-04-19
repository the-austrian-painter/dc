import java.util.*;

class Chunk {
    String data;
    UUID id;

    Chunk(String data) {
        this.id = UUID.randomUUID();
        this.data = data;
    }
}

class ChunkServer {
    String name;
    Map<UUID, Chunk> chunkStorage = new HashMap<>();

    ChunkServer(String name) {
        this.name = name;
    }

    void storeChunk(Chunk chunk) {
        chunkStorage.put(chunk.id, chunk);
        System.out.println("Stored chunk " + chunk.id + " on " + name);
    }

    String readChunk(UUID chunkId) {
        if (chunkStorage.containsKey(chunkId)) {
            return chunkStorage.get(chunkId).data;
        }
        return "Chunk not found!";
    }
}

class MasterNode {
    Map<String, List<UUID>> fileToChunks = new HashMap<>();
    Map<UUID, List<ChunkServer>> chunkReplicas = new HashMap<>();

    void registerFile(String fileName, List<Chunk> chunks, List<ChunkServer> servers) {
        List<UUID> chunkIds = new ArrayList<>();

        for (Chunk chunk : chunks) {
            chunkIds.add(chunk.id);

            // Replicate chunk on 3 servers (or as many available)
            List<ChunkServer> replicas = new ArrayList<>();
            Collections.shuffle(servers);

            for (int i = 0; i < Math.min(3, servers.size()); i++) {
                servers.get(i).storeChunk(chunk);
                replicas.add(servers.get(i));
            }

            chunkReplicas.put(chunk.id, replicas);
        }

        fileToChunks.put(fileName, chunkIds);
        System.out.println("File " + fileName + " registered with Master.");
    }

    List<UUID> getChunkIds(String fileName) {
        return fileToChunks.get(fileName);
    }

    List<ChunkServer> getChunkServers(UUID chunkId) {
        return chunkReplicas.get(chunkId);
    }
}

class Client {
    MasterNode master;

    Client(MasterNode master) {
        this.master = master;
    }

    void writeFile(String fileName, String data, List<ChunkServer> servers) {
        List<Chunk> chunks = new ArrayList<>();

        // Split into chunks of 10 characters (for simulation)
        int chunkSize = 10;
        for (int i = 0; i < data.length(); i += chunkSize) {
            String part = data.substring(i, Math.min(data.length(), i + chunkSize));
            chunks.add(new Chunk(part));
        }

        master.registerFile(fileName, chunks, servers);
    }

    void readFile(String fileName) {
        List<UUID> chunkIds = master.getChunkIds(fileName);

        System.out.println("\nReading file: " + fileName);

        for (UUID id : chunkIds) {
            List<ChunkServer> servers = master.getChunkServers(id);

            if (!servers.isEmpty()) {
                System.out.print(servers.get(0).readChunk(id));
            }
        }

        System.out.println("\n");
    }
}

public class GFS_Simulation {
    public static void main(String[] args) {

        // Setup
        MasterNode master = new MasterNode();
        ChunkServer s1 = new ChunkServer("ChunkServer-1");
        ChunkServer s2 = new ChunkServer("ChunkServer-2");
        ChunkServer s3 = new ChunkServer("ChunkServer-3");

        List<ChunkServer> servers = Arrays.asList(s1, s2, s3);

        // Client operations
        Client client = new Client(master);

        client.writeFile("myfile.txt", "This is a simple simulation of GFS in Java.", servers);
        client.readFile("myfile.txt");
    }
}
