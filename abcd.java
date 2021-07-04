import java.util.Scanner;

public class abcd{

    static int getRandom()
    {
        int random_int = (int)Math.floor(Math.random()*(100-1+1)+1);
        return random_int;
    }
    static void tSleep()
    {
        try        
{
    Thread.sleep(1000);
} 
catch(InterruptedException ex) 
{
    Thread.currentThread().interrupt();
}
    }

    public static void main(String[] args) {
        
        int nf,N;
        int no_tr=0;
        Scanner sc = new Scanner(System.in);
 
        System.out.println("Enter the number of frames : ");
        nf = sc.nextInt();
        System.out.println("Enter the Window Size : ");
        N = sc.nextInt();
        int i=1;
        while(i<=nf)
        {
            int x=0;
            for(int j=i;j<i+N && j<=nf;j++)
            {
                System.out.println("Sent frame "+j);
                tSleep();
        
                no_tr++;
            }
            for(int j=i;j<i+N && j<=nf;j++)
            {
                int flag = getRandom()%2;
                if(!(flag==1))
                {
                    System.out.println("Acknowledgement for frame "+j);
                    tSleep();
                 x++;
                }
                else
                {   
                    System.out.println("Frame "+j+" Not Received");
                    System.out.println("Retransmitting window");
                    tSleep();
                    break;
                }
            }

            i+=x;
        }
        System.out.println("Total Number of tranmissions "+no_tr);
 
 
    }
}





