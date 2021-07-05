

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

    //Sender's Port
    private Integer senderPort;

    //Destination Port
    private Integer destinationPort;


    Packet(String destinationMac, String sourceMac, String frameData, Integer[] senderIP, Integer[] receiverIP, Integer senderPort, Integer destinationPort)
    {
        this.destinationMac = destinationMac;
        this.sourceMac = sourceMac;
        this.frameData = frameData;
        this.senderIP = senderIP;
        this.receiverIP = receiverIP;
        this.senderPort = senderPort;
        this.destinationPort = destinationPort;
    }



    /******************************************************  Methods  ******************************************** */

    //Return Senders Port
    public Integer getSenderPort()
    {
        return this.senderPort;
    }

    //Return Destination Port
    public Integer getDestinationPort()
    {
        return this.destinationPort;
    }

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
