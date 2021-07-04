import java.util.*;
import java.util.Scanner;

public class Simulator {

    static List<Host> hostList = new ArrayList<Host>();
    static List<Link> links = new ArrayList<Link>();
    static List<Hub> hubList = new ArrayList<Hub>();
    static List<Switch> switchList = new ArrayList<Switch>();
    static List<Router> routerList = new ArrayList<Router>();

    static Scanner sc = new Scanner(System.in);
    public static void main(String[] args) {
        
        System.out.println("HI.");
        while(true)
        {
            System.out.println("1. Create Hosts");
            System.out.println("2. List Nodes");
            System.out.println("3. Create Link");
            System.out.println("4. Manage Hosts");
            System.out.println("5. Create Hub");
            System.out.println("6. Manage Hub");
            System.out.println("7. Create Switch");
            System.out.println("8. Manage Switch");
            System.out.println("9. Create Router");
            System.out.println("10. Manage Router");
            System.out.println("11. Print ARP table");

            int key = sc.nextInt();

            switch (key) {
                case 1:
                    hostList.add(createHost());
                    break;
                case 2:
                    printHostList();
                    break;
                case 3:
                    createLink();
                    break;
                case 4:
                    manageHost();
                    break;
                case 5:
                    createHub();
                    break;
                case 6:
                    manageHub();
                    break;
                case 7:
                    createSwitch();
                    break;
                case 8:
                    manageSwitch();
                    break;
                case 9: 
                    createRouter();
                    break;
                case 10:
                    manageRouter();
                case 11:
                    printARP();
                    break;
                default:
                    break;
            }
            

        }
        
    }

    // 1 Creates Hosts
    private static Host createHost() {

        clearScreen();
        System.out.println("Enter the HostName");
        //Stores the host's name
        String hostName = sc.next();

    

        System.out.println("Enter the IPAddress: ( Note enter four spaced integers ranging btw 0 - 255");
        //Stores's the IP address
        Integer[] IP_Address = new Integer[4];
        for(int i = 0; i < 4; i++)
        {
            IP_Address[i] = sc.nextInt();
        }
        System.out.println("Enter the SubnetMask: ( Note enter four spaced integers and one Length of host bits");
        Integer[] subnet = new Integer[5];
        for(int i = 0; i < 5; i++)
        {
            subnet[i] = sc.nextInt();
        }
        System.out.println("Enter the Gateway: ( Note enter four spaced integers ranging btw 0 - 255");
        //Stores's the IP address
        Integer[] gateway = new Integer[4];
        for(int i = 0; i < 4; i++)
        {
            gateway[i] = sc.nextInt();
        }
        clearScreen();
        System.out.println();
        System.out.println();
        
        
        return new Host(hostName,IP_Address,subnet,gateway);
    }
    
    // 2 List's Hosts
    private static void printHostList() {
        clearScreen();

        int length = hostList.size();
        for(int i = 0; i < length; i++)
        {
            System.out.println(
                hostList.get(i).getHostName() + "  "+
                hostList.get(i).getMacAddress() + "  "+
                hostList.get(i).getIP_Address()[0]+"."+
                hostList.get(i).getIP_Address()[1]+"."+
                hostList.get(i).getIP_Address()[2]+"."+
                hostList.get(i).getIP_Address()[3]
            );
        }
    }

    // 3 Create's Link
    private static void createLink() {
        clearScreen();
        Link link = new Link();
        System.out.println("Link ID " + link.getID()+ " Created!");
        links.add(link);
    }
    
    // 4 Manage Hosts
    private static void manageHost() {
        int length = hostList.size();
        clearScreen();
        while(true)
        {
            System.out.println("Choose a host to operate on :-");
            System.out.println("0)  Return to main menu ");
            for(int i = 0; i < length; i++)
            {
                System.out.println(i+1+") "+hostList.get(i).getHostName() + "  "+
                hostList.get(i).getMacAddress() + "  "+
                hostList.get(i).getIP_Address()[0]+"."+
                hostList.get(i).getIP_Address()[1]+"."+
                hostList.get(i).getIP_Address()[2]+"."+
                hostList.get(i).getIP_Address()[3]);
            }
            System.out.println("Choose a host to operate on :-");
            int key = sc.nextInt();
            if(key == 0)
            {
                
                break;
            }
            debugHost(hostList.get(key-1));
            clearScreen();

        }
    }

    // 4.1 Debugging Hosts
    private static void debugHost(Host host) {
        while(true)
        {
            System.out.println("1) Add Connection :-");
            System.out.println("2  Send Message :-");
            System.out.println("3) Return");
            int key = sc.nextInt();
            if(key == 3)
            {
                break;
            }
            else if(key == 1)
            {
                clearScreen();
                System.out.println("Enter the link_ID you want to connect :-");
                int linkID = sc.nextInt();
                if(host.addConnection(getLink(linkID)))
                {
                    clearScreen();
                    System.out.println("Host "+ host.getHostName()+" is successfully added to Link "+linkID);
                }
                else{
                    clearScreen();
                    System.out.println("Link Connection failed!!!");
                }
            }
            else if(key == 2)
            {
                clearScreen();
                System.out.println("Choose the host to send the packets ");
                for(int k = 0; k < hostList.size(); k++)
                {
                    System.out.println(k+" "+hostList.get(k).getHostName() + " Mac : "+hostList.get(k).getMacAddress()+" IP : "+hostList.get(k).getIP_Address());
                }
                int choice = sc.nextInt();
                System.out.println("Enter the message to transmit : ");
                String message = sc.next();
                if(Router.checkIfSameNetwork(host.getIP_Address(), hostList.get(choice).getIP_Address(), host.getSubnetMask()))
                {
                    String destMac = ARP.arp.get(hostList.get(choice).getIPString());

                    host.SendPacket(new Packet(destMac, host.getMacAddress(), message,host.getIP_Address(),hostList.get(choice).getIP_Address()));
                }
                else
                {
                    String destMac = ARP.arp.get(Router.getString(host.getGateWay()));
                    host.SendPacket(new Packet(destMac, host.getMacAddress(), message, host.getIP_Address(), hostList.get(choice).getIP_Address()));
                }

                
            }
        }
    }


    // 5 Create's Hub
    private static void createHub() {
        clearScreen();
        Hub hub = new Hub();
        System.out.println("Hub ID " + hub.getID()+ " Created!");
        hubList.add(hub);
    }
    
    // 6. Manage Hub
    private static void manageHub()
    {
        int totalHubs = hubList.size();
        clearScreen();
        while(true)
        {
            System.out.println("Choose a hub to operate on :-");
            System.out.println("0)  Return to main menu ");
            for(int i = 0; i < totalHubs; i++)
            {
                System.out.println(i+1+") Hub ID"+hubList.get(i).getID());
            }
            int key = sc.nextInt();
            if(key == 0)
            {
                
                break;
            }
            debugHub(hubList.get(key-1));
            clearScreen();

        }
    }

    // 6.1 Debug Hubs
    private static void debugHub(Hub hub) {
        while(true)
        {
            System.out.println("1) Add Connection :-"); 
            System.out.println("2) Return");
            int key = sc.nextInt();
            if(key == 2)
            {
                break;
            }
            else if(key == 1)
            {
                clearScreen();
                System.out.println("Enter the link_ID you want to connect :-");
                int linkID = sc.nextInt();
                if(hub.addConnection(getLink(linkID)))
                {
                    clearScreen();
                    System.out.println("Hub "+ hub.getID()+" is successfully added to Link "+linkID);
                }
                else{
                    clearScreen();
                    System.out.println("Link Connection failed!!!");
                }
            }
            
        }
    }

    // 7 Create Switch
    private static void createSwitch()
    {
        clearScreen();
        switchList.add(new Switch());
    }

    // 8 Manage Switch
    private static void manageSwitch()
    {
        int totalSwitch = switchList.size();
        clearScreen();
        while(true)
        {
            System.out.println("Choose a Switch to operate on :-");
            System.out.println("0)  Return to main menu ");
            for(int i = 0; i < totalSwitch; i++)
            {
                System.out.println(i+1+") Switch ID"+switchList.get(i).getId());
            }
            int key = sc.nextInt();
            if(key == 0)
            {
                
                break;
            }
            debugSwitch(switchList.get(key-1));
            clearScreen();

        }
    }

    
    // 8.1 Debug Switch
    private static void debugSwitch(Switch switch1) {
        while(true)
        {
            System.out.println("1) Add Connection :-"); 
            System.out.println("2) Return");
            int key = sc.nextInt();
            if(key == 2)
            {
                break;
            }
            else if(key == 1)
            {
                clearScreen();
                System.out.println("Enter the link_ID you want to connect :-");
                int linkID = sc.nextInt();
                switch1.addConnection(getLink(linkID));
            }
            
        }
    }

    // 9 Create Router
    public static void createRouter()
    {
        clearScreen();
        routerList.add(new Router());
    }

    // 10 Manage Router
    private static void manageRouter()
    {
        int totalRouter = routerList.size();
        clearScreen();
        while(true)
        {
            System.out.println("Choose a Router to operate on :-");
            System.out.println("0)  Return to main menu ");
            for(int i = 0; i < totalRouter; i++)
            {
                System.out.println(i+1+") Router ID"+routerList.get(i).getRouterID());
            }
            int key = sc.nextInt();
            if(key == 0)
            {
                
                break;
            }
            debugRouter(routerList.get(key-1));
            clearScreen();

        }
    }
    
    // 10.1 Debug ROuter
    private static void debugRouter(Router router) {
        while(true)
        {
            System.out.println("1) Create Interface :-");
            System.out.println("2) Manage Interface");
            System.out.println("3) Add entry to Routing table");
            System.out.println("4) Print ROuting table");
            System.out.println("5) Return");
            int key = sc.nextInt();
            if(key == 1)
            {
                router.createInterface();
            }
            
            else if(key == 2)
            {
                manageInterface(router);

            }
            else if(key == 3)
            {
                System.out.println("Enter the network ID...   (Note there should be 4 spaced integers");
                Integer[] netID = new Integer[4];
                for(int i = 0; i < 4; i++)
                {
                    netID[i] = sc.nextInt();
                }
                System.out.println("Enter the SubnetMask: ( Note enter four spaced integers and one Length of host bits");
                Integer[] subnet = new Integer[5];
                for(int i = 0; i < 5; i++)
                {
                    subnet[i] = sc.nextInt();
                }
                System.out.println("Enter the Interface ID");
                Integer interfaceId = sc.nextInt();
                router.addEntry(subnet, netID, interfaceId);
            }
            else if(key == 4)
            {
                router.printRoutingTable();
            }
            else if(key == 5)
            {
                break;
            }
        }

        
    }
    
    private static void manageInterface(Router router) {
        while(true)
        {
            System.out.println("Choose an interface you want to manage :");
                int l = router.interfaces.size();
                for(int i = 0; i < l; i++)
                {
                    System.out.println(i+") Interface "+router.interfaces.get(i).getID()+" Mac Address : "+router.interfaces.get(i).getMacAddress() +" IP Address :"+ router.interfaces.get(i).getIPString()+" Gateway : "+Router.getString(router.interfaces.get(i).getGateway()));
                }
                System.out.println(l+") Return");
                System.out.print("Your choice : ");

                int choice = sc.nextInt();
                if(choice == l)
                    break;
                clearScreen();
                while(true)
                {
                    System.out.println("1) Add Connection ");
                System.out.println("2) Set Gateway");
                System.out.println("3) Add IP Adresss");
                System.out.println("4) Return");
                int choice1 = sc.nextInt();
                if(choice1 == 1)
                {
                    System.out.println("Enter the link_ID you want to connect :-");
                    int linkID = sc.nextInt();
                    router.interfaces.get(choice).addConnection(getLink(linkID));
                }
                else if(choice1 == 2)
                {
                    System.out.println("Enter the Gateway: ( Note enter four spaced integers ranging btw 0 - 255");
                    //Stores's the IP address
                    Integer[] gateway = new Integer[4];
                    for(int i = 0; i < 4; i++)
                    {
                        gateway[i] = sc.nextInt();
                    }
                    router.interfaces.get(choice).setGateway(gateway);
                }
                else if(choice1 == 3)
                {
                    System.out.println("Enter the IPAddress: ( Note enter four spaced integers ranging btw 0 - 255");
                    //Stores's the IP address
                    Integer[] IPaddress = new Integer[4];
                    for(int i = 0; i < 4; i++)
                    {
                        IPaddress[i] = sc.nextInt();
                    }
                    System.out.println("Enter the SubnetMask: ( Note enter four spaced integers and one Length of host bits");
                    Integer[] subNetMask = new Integer[5];
                    for(int i = 0; i < 5; i++)
                    {
                        subNetMask[i] = sc.nextInt();
                    }
                    router.interfaces.get(choice).setIPAddress(IPaddress, subNetMask);;
                    }
                    else if(choice1 == 4)
                    {
                        break;
                    }
                }
        }
    }

    /******************************************************* General Methods ************************************* */



    //Returns Link object of corresponding LinkId
    private static Link getLink(int linkID) {
        int length = links.size();
        for(int i = 0; i < length; i++)
        {
            if(links.get(i).getID() == linkID)
                return links.get(i);
        }
        return null;
    }

    static void printARP()
    {
        ARP.printtable();
    }


    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }
}
