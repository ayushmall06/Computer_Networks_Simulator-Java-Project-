import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Host {

    //Stores the host's name
    private String hostName;

    //Store's the system Mac Address
    private String macAddress;

    private Integer[] subnetMask;

    private Integer[] gateway;

    //Received Packet
    private Packet receivedPacket = null;

    //Conections
    List<Link> links = new ArrayList<Link>();

    //IP Adress
    Integer[] IP_Address;

    /***********************************************   Constructor  ********************************************** */
    public Host(String hostName,  Integer[] IP_Address, Integer[] subnet, Integer[] gateway)
    {
        this.hostName = hostName;
        this.macAddress = DeviceManager.getMAC_ADDRESS();
        this.IP_Address = IP_Address;
        this.subnetMask = subnet;
        ARP.arp.put(this.getIPString(), this.macAddress);
        this.gateway = gateway;
        System.out.println("Host "+hostName+" created!!!  Mac Address : "+this.macAddress+"  IP Address : "+getIPString()+"  SubnetMask: /"+this.subnetMask[4]);
    }

    /**********************************************   Getters   ************************************************** */

    public Integer[] getSubnetMask()
    {
        return this.subnetMask;
    }

    public Integer[] getGateWay()
    {
        return this.gateway;
    }

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
    public  Integer[] getIP_Address()
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
        System.out.println("Recieved message");
        if(Arrays.equals(packet.getReceiversIP(), this.getIP_Address()))
        {
            System.out.println("Host "+this.getHostName()+" received the packet from "+Router.getString(packet.getSendersIP())+"\n Message : "+packet.getData());
        }
    }

}