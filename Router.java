import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Router {

    class Interfaces{
        final int CONNECTED = 1;
        final int NOT_CONNECTED = 0;
        int interaceID;
        int connection;
        String macAddress;
        Integer[] ipAddress = new Integer[4];
        Link link;
        Integer[] subNetMask;
        Integer[] gateway = null;

        public void addConnection(Link link)
        {
            if(connection == CONNECTED)
            {
                System.out.println("Link Connection Failed... Error :- Interface already connected to Link ID "+link.getID()); 
            }
            else{
                link.rList.add(this);
                this.link = link;
                connection = CONNECTED;
                System.out.println("Link "+link.getID()+" successfully connected !!!");
            }
        }

        //Contructor
        public Interfaces()
        {
            this.interaceID = count++;
            macAddress = DeviceManager.getMAC_ADDRESS();
            this.connection = NOT_CONNECTED;
            
        }
        
        //Returns Subnet Mask
        Integer[] getSubnetMask()
        {
            return this.subNetMask;
        }

        //Returns interface ID
        public int getID()
        {
            return this.interaceID;
        }

        //Returns Interface's Mac Address
        public  String getMacAddress()
        {
            return this.macAddress;
        }

        //Returns Interface IPAddress
        public  Integer[] getIP_Address()
        {
            return this.ipAddress;
        }

        //Return IP Address in String format
        public String getIPString()
        {
            return ipAddress[0]+"."+ipAddress[1]+"."+ipAddress[2]+"."+ipAddress[3];
        }

        //Set's the IP Address
        public void setIPAddress(Integer[] ipAddress, Integer[] subNetMask)
        {
            
            this.subNetMask = subNetMask;
            this.ipAddress = ipAddress;
            ARP.arp.put(this.getIPString(), this.getMacAddress());
        }

        public void sendPacket(Packet packet,Link link)
        {
            if(packet.getDestinationMac().equals(this.getMacAddress()))
            {
                Integer[] dIP = packet.getReceiversIP();
                Integer forwardInterface = getForwardingInterface(dIP);
                Interfaces itf = getInterface(forwardInterface);
                if(checkIfSameNetwork(dIP, itf.getIP_Address(), itf.getSubnetMask()))
                {
                    packet.setDMacAddress(ARP.arp.get(getString(packet.getReceiversIP())));
                    itf.link.deliverPacket(packet, itf);
            
                }
                else
                {
                    if(itf.getGateway()==null)
                    {
                        System.out.println("HOST NOT REACHABLE  at ROuter "+getRouterID()+" at Interface "+this.getID());
                    }
                    else
                    {
                        String dMac = ARP.arp.get(getString(itf.getGateway()));
                        packet.setDMacAddress(dMac);
                        itf.link.deliverPacket(packet, itf);
                    }
                }
            }


        }
        
        public Integer[] getGateway()
        {
        return this.gateway;
        }

        public void setGateway(Integer[] gateway)
        {
            this.gateway = gateway;
        }
    }
    int ID;
    int count = 0;
    static int counter = 0;

    int MAXIMUM_INTERFACES = 10;
    List<Interfaces> interfaces = new LinkedList<Interfaces>();

    public Router()
    {
        this.ID = counter++;
        System.out.println("Router "+ this.getRouterID()+" is successfully created");
    }
    /**********************************************   Routing Table   ******************************************* */
    List<Integer[]> subNetEntry = new LinkedList<Integer[]>();
    List<Integer[]> networkIDEntry = new LinkedList<Integer[]>();
    List<Integer> interfaceIDEntry = new LinkedList<Integer>(); 


    //Adds entry in the routing table
    public void addEntry(Integer[] subnet, Integer[] networkID, Integer interfaceID)
    {
        subNetEntry.add(subnet);
        networkIDEntry.add(networkID);
        interfaceIDEntry.add(interfaceID);
    }

    //Returns Router ID
    int getRouterID()
    {
        return this.ID;
    }


    //creates Interface
    public void createInterface(){
        Interfaces itf = new Interfaces();
        System.out.println("Router ID : "+this.getRouterID() + " Interface with ID " + itf.getID() + " MacAddress :" + itf.getMacAddress() + " IpAddress : NA  has been successfully created !!!");
        interfaces.add(itf);
    
    }

    Interfaces getInterface(Integer ID)
    {
        int l = interfaces.size();
        for(int i = 0; i < l; i++)
        {
            if(interfaces.get(i).getID() == ID)
                return interfaces.get(i);
        }
      
            return null;
    }


    //get forwarding Interface
    public Integer getForwardingInterface(Integer[] IP)
    {
        Integer[] nID = new Integer[4];
        int length = subNetEntry.size();
        Integer itf_num = null;
        int subnetLength = 0;
        for(int i = 0; i < length; i++)
        {
            nID[0] = IP[0] & subNetEntry.get(i)[0];
            nID[1] = IP[1] & subNetEntry.get(i)[1];
            nID[2] = IP[2] & subNetEntry.get(i)[2];
            nID[3] = IP[3] & subNetEntry.get(i)[3];
            if(Arrays.equals(nID,networkIDEntry.get(i)) && subnetLength < subNetEntry.get(i)[4])
            {
                itf_num = interfaceIDEntry.get(i);
                subnetLength = subNetEntry.get(i)[4];
            }
        }
        return itf_num;
    }
    


    static boolean checkIfSameNetwork(Integer[] IP1, Integer[] IP2, Integer[] subnet)
    {
        Integer[] nID1 = new Integer[4];
        Integer[] nID2 = new Integer[4];

        nID1[0] = IP1[0] & subnet[0];
        nID1[1] = IP1[1] & subnet[1];
        nID1[2] = IP1[2] & subnet[2];
        nID1[3] = IP1[3] & subnet[3];

        nID2[0] = IP2[0] & subnet[0];
        nID2[1] = IP2[1] & subnet[1];
        nID2[2] = IP2[2] & subnet[2];
        nID2[3] = IP2[3] & subnet[3];

        if(Arrays.equals(nID1, nID2))
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    static String getString(Integer[] ip)
    {
        if(ip != null)
        {
            return ip[0]+"."+ip[1]+"."+ip[2]+"."+ip[3];
        }
        return null;
    }

    void printRoutingTable()
    {
        int length = subNetEntry.size();
        
        for(int i = 0; i < length; i++)
        {
            System.out.println(getString(networkIDEntry.get(i))+"   "+getString(subNetEntry.get(i))+"  "+interfaceIDEntry.get(i));
        }
    }

    
}
