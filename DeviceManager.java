import java.util.Calendar;

public class DeviceManager {

    static Calendar calendar;
    static String currentDate, currentTime;
    static int trim(int num)
    {
        num = num%100;
        return num;
    }
    public static String getMAC_ADDRESS() {
        calendar = Calendar.getInstance();
        currentDate = Integer.toHexString(trim(calendar.get(Calendar.YEAR)))+":"+
                Integer.toHexString(calendar.get(Calendar.MONTH))+":"+
                Integer.toHexString(calendar.get(Calendar.DAY_OF_MONTH));
        currentTime = Integer.toHexString(calendar.get(Calendar.HOUR))+":"+
                Integer.toHexString(calendar.get(Calendar.MINUTE))+":"+
                Integer.toHexString(calendar.get(Calendar.SECOND));


        return currentDate+":"+currentTime;
    }
}
