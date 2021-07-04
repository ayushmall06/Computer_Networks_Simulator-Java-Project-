import java.util.HashMap;
import java.util.LinkedList;

public class Switch {

    static int counter = 0;
    
    int MAXIMUM_INTERFACES = 10;

    LinkedList<Link> interfaces = new LinkedList<Link>();

    //Number of interfaces
    int n = 0;

    //Switch ID
    int id;

    //Switch Table  HashMap<macAddress, interface>
    HashMap<String,Integer> table = new HashMap<>();

    HashMap<Integer, Integer> interfaceLink = new HashMap<>();


    /***********************************************   Constructors    ***************************************** */
    public Switch()
    {
        this.id = counter++;
        System.out.println("Switch "+id+" has been successfully created.");
    }



    /******************************************  Methods  ************************************************ */

    // get Switch ID
    public int getId()
    {
        return this.id;
    }


    public void sendPacket(Packet packet,Link link)
    {
        String sourceMac = packet.getSourceMac();
        int iface = interfaces.indexOf(link);
        String destMac = packet.getDestinationMac();
        if(!table.containsKey(sourceMac))
        {
            table.put(sourceMac, iface);
        }
        if(!table.containsKey(destMac))
        {
            for(int i = 0; i < interfaces.size(); i++)
            {
                if(interfaces.get(i) != link)
                {
                    interfaces.get(i).deliverPacket(packet, this);
                }
            }
        }
        else
        {
            int index = table.get(destMac);
            interfaces.get(index).deliverPacket(packet, this);
        }
    }


    public boolean addConnection(Link link)
    {
        if(link == null)
        {
            System.out.println("Link Connection failed! No Such Link found!!!!!!!!!");
            return false;
        }
        else
        {
            if(interfaces.contains(link))
            {
                System.out.println("Switch " + this.getId() + " is already connected to Link "+link.getID());
                return false;
            }
            else{
                interfaces.add(link);
                link.switchList.add(this);
                System.out.println("Switch " + this.getId() + " is successfully connected to Link "+link.getID());
                
            }    
            
            return true;
        }
    }




}
