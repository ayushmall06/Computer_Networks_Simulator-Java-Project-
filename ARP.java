import java.util.HashMap;

public class ARP {
    
    static HashMap<String, String> arp = new HashMap<>();

    public static void printtable() {
        int i = 0;
        for(String key : arp.keySet())
        {
            System.out.println(i++ +") "+key+"  "+arp.get(key));
        }
    }
}
