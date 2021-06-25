import java.util.HashMap;

public class Switch {

    int MAXIMUM_INTERFACES = 10;

    //Switch Name
    String switchName;

    //Number of interfaces
    int n = 0;

    //Switch Table  HashMap<macAddress, interface>
    HashMap<String,Integer> table = new HashMap<>();

    public Switch(String switchName)
    {
        this.switchName = switchName;
    }



    /******************************************  Methods  ************************************************ */

    //add an inteface
    public boolean addInterface()
    {
        if(n < MAXIMUM_INTERFACES)
        {
            n++;
            return true;
        }
        else{
            return false;
        }
        
    }

    public void sendPacket(Packet packet)
    {
       
    }




}
