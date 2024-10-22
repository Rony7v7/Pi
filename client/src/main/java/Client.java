import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Util;
import com.zeroc.Ice.ObjectPrx;
import java.util.Scanner;


public class Client {

    public static Scanner sc=new Scanner(System.in);

    public static void main(String[] args){

        System.out.println("Enter the number of point you want to estimate pi with:");
        int n=sc.nextInt();

        try(Communicator communicator = Util.initialize(args)){

            com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy("Master:default -p 10000");

            Demo.MasterPrx master = Demo.MasterPrx.checkedCast(base);

            if(master == null){
                throw new Error("Invalid proxy");
            }
            
            System.out.println(master.calculatePi(int n));

        }

    }
    
}
