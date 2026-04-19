import java.util.*;

class Chunk {
    String data; UUID id = UUID.randomUUID();
    Chunk(String d){ data=d; }
}

class ChunkServer {
    Map<UUID,String> store = new HashMap<>();
    void save(Chunk c){ store.put(c.id,c.data); }
    String read(UUID id){ return store.getOrDefault(id,"Not found"); }
}

class Master {
    Map<String,List<UUID>> files = new HashMap<>();
    Map<UUID,List<ChunkServer>> loc = new HashMap<>();

    void write(String name,String data,List<ChunkServer> s){
        List<UUID> ids = new ArrayList<>();
        for(int i=0;i<data.length();i+=5){
            Chunk c=new Chunk(data.substring(i,Math.min(i+5,data.length())));
            ids.add(c.id);
            for(ChunkServer cs:s){ cs.save(c); }
            loc.put(c.id,s);
        }
        files.put(name,ids);
    }

    void read(String name){
        System.out.println("Reading "+name);
        for(UUID id:files.get(name))
            System.out.print(loc.get(id).get(0).read(id));
        System.out.println();
    }
}

public class GFS {
    public static void main(String[] a){
        Master m=new Master();
        List<ChunkServer> s=Arrays.asList(new ChunkServer(),new ChunkServer(),new ChunkServer());

        m.write("file","HelloDistributedSystem",s);
        m.read("file");
    }
}
