

public class Packet {

    //Destination Mac Address
    private String destinationMac;

    //Source Mac Address
    private String sourceMac;

    //Frame's Length
    private String frameLength;

    //FrameData
    private String frameData;

    //Sender's IP Address
    private Integer[] senderIP;

    //Receiver's IP Address
    private Integer[] receiverIP;


    Packet(String destinationMac, String sourceMac, String frameData, Integer[] senderIP, Integer[] receiverIP)
    {
        this.destinationMac = destinationMac;
        this.sourceMac = sourceMac;
        this.frameData = frameData;
        this.senderIP = senderIP;
        this.receiverIP = receiverIP;
    }



    /******************************************************  Methods  ******************************************** */

    //Returns frameData
    String getData()
    {
        return this.frameData;
    }

    //Returns source Mac
    String getSourceMac()
    {
        return this.sourceMac;
    }

    //Returns destination Mac
    String getDestinationMac()
    {
        return this.destinationMac;
    }

    Integer[] getSendersIP()
    {
        return this.senderIP;
    }

    Integer[] getReceiversIP()
    {
        return this.receiverIP;
    }

    //Set's destination mac Address
    void setDMacAddress(String macAddress)
    {
        this.destinationMac = macAddress;
    }
}
