import java.util.*;
import java.util.Scanner;

public class Simulator {

    static List<Host> hostList = new ArrayList<Host>();
    static List<Link> links = new ArrayList<Link>();
    static List<Hub> hubList = new ArrayList<Hub>();

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

        System.out.println("Enter the macAddress");
        //Store's the system Mac Address
        String macAddress = sc.next();

        System.out.println("Enter the IPAddress: ( Note enter four spaced integers ranging btw 0 - 255");
        //Stores's the IP address
        int[] IP_Address = new int[4];
        for(int i = 0; i < 4; i++)
        {
            IP_Address[i] = sc.nextInt();
        }
        clearScreen();
        System.out.println();
        System.out.println();
        System.out.println("Host created !");
        
        return new Host(hostName,macAddress,IP_Address);
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
                host.SendPacket(new Packet(hostList.get(choice).getMacAddress(), host.getMacAddress(), message));
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




    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }
}
