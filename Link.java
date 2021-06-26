import java.util.*;

public class Link {
    
    public static final int CONNECTED = 1;
    public static final int NOT_CONNECTED = 0;


    static int count = 0;

    //Link ID
    private int ID;

    String bufferMessage = null;

    //Buffer packet
    private Packet bufferPacket = null;

    //List of host's connected to this Link
    List<Host> hostList = new ArrayList<Host>();

    //List of hub's connected to this Link
    List<Hub> hubList = new ArrayList<Hub>();

    //List of Switch's connected to this Link
    List<Switch> switchList = new ArrayList<Switch>();

    //List of switch's connected to this Link
   // List<Switch.Interface> switchList = new ArrayList<Switch.Interface>();


    //*******************************************Constructors***************************8 */
    public Link()
    {
        
        this.ID = count++;
    }

    //************************************************************************************ */


    public int getID()
    {
        return this.ID;
    }


    //add Host Connection
    public void addHostConnection(Host host)
    {
        hostList.add(host);
    }

    
    public void deliverPacket(Packet packet,Host host)
    {
        int length = hostList.size();
        for(int i = 0;  i < length; i++)
        {
            if(host != hostList.get(i))
            {
                hostList.get(i).receivePacket(packet);
            }
        }
        length = hubList.size();
        for(int i = 0; i < length; i++)
        {
            hubList.get(i).forwardPacket(packet, this);
        }
        length = switchList.size();
        for(int i = 0; i < length; i++)
        {
            switchList.get(i).sendPacket(packet,this);
        }
    }

    public void deliverPacket(Packet packet,Hub hub)
    {
        int length = hostList.size();
        for(int i = 0;  i < length; i++)
        {
            
            hostList.get(i).receivePacket(packet);
            
        }
        length = hubList.size();
        for(int i = 0; i < length; i++)
        {
            if(hubList.get(i) != hub)
            {
                hubList.get(i).forwardPacket(packet, this);
            }
        }
        length = switchList.size();
        for(int i = 0; i < length; i++)
        {
            switchList.get(i).sendPacket(packet,this);
        }
    }

    public void deliverPacket(Packet packet,Switch switch1)
    {
        int length = hostList.size();
        for(int i = 0;  i < length; i++)
        {
            
            hostList.get(i).receivePacket(packet);
            
        }
        length = hubList.size();
        for(int i = 0; i < length; i++)
        {
            hubList.get(i).forwardPacket(packet, this);
        }
        length = switchList.size();
        for(int i = 0; i < length; i++)
        {
            if(switchList.get(i) != switch1)
            {
                switchList.get(i).sendPacket(packet,this);
            }
        }
    }

}