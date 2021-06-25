

public class Packet {

    //Destination Mac Address
    private String destinationMac;

    //Source Mac Address
    private String sourceMac;

    //Frame's Length
    private String frameLength;

    //FrameData
    private String frameData;


    Packet(String destinationMac, String sourceMac, String frameData)
    {
        this.destinationMac = destinationMac;
        this.sourceMac = sourceMac;
        this.frameData = frameData;
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


}
