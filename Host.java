import java.util.ArrayList;
import java.util.List;

public class Host {

    //Stores the host's name
    private String hostName;

    //Store's the system Mac Address
    private String macAddress;

    //Received Packet
    private Packet receivedPacket = null;

    //Conections
    List<Link> links = new ArrayList<Link>();

    //IP Adress
    int[] IP_Address;

    public Host(String hostName, String macAdrress, int []IP_Address)
    {
        this.hostName = hostName;
        this.macAddress = macAdrress;
        this.IP_Address = IP_Address;
        
        System.out.println("Host "+hostName+" created!!!  Mac Address : "+macAdrress+"  IP Address : "+getIPString());
    }

    /**********************************************   Getters   ************************************************** */
    //Returns hostName
    public  String getHostName()
    {
        return this.hostName;
    }

    //Returns Host's Mac Address
    public  String getMacAddress()
    {
        return this.macAddress;
    }

    //Returns Host IPAddress
    public  int[] getIP_Address()
    {
        return this.IP_Address;
    }

    public String getIPString()
    {
        return IP_Address[0]+"."+IP_Address[1]+"."+IP_Address[2]+"."+IP_Address[3];
    }

    /********************************************* Methods  ****************************************************** */
    public boolean addConnection(Link link)
    {
        if(link == null)
        {
            return false;
        }
        link.hostList.add(this);
        links.add(link);
        return true;
    }

    public void SendPacket(Packet packet)
    {
        for(int i =0; i < links.size(); i++)
        {
            links.get(i).deliverPacket(packet,this);
        }

    }
    public void receivePacket(Packet packet) {
        System.out.println(this.hostName+ " "+ packet.getData());
    }

}