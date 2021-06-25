import java.util.*;
public class Hub {

    static int counter = 0;

    //Hub ID
    private int hubId = 0;

    //Number of interfaces
    private int interfaces = 0;

    //Active connections
    List<Link> links = new LinkedList<Link>();

    

    public Hub()
    {
        hubId = counter++;
    }
    

    /****************************************************   Methods ******************************************** */

    void forwardPacket(Packet packet, Link link)
    {
        // n = total number of active hub's interfaces
        int n = links.size();
        for(int i = 0; i < n; i++)
        {
            if(links.get(i) != link)
            {
                links.get(i).deliverPacket(packet, this);
            }
        }
    }

    //Returns hub ID
    public int getID()
    {
        return hubId;
    }

    // Add's connection
    public boolean addConnection(Link link)
    {
        if(link == null)
        {
            return false;
        }
        link.hubList.add(this);
        links.add(link);
        return true;
    }

}
