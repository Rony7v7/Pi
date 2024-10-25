import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Util;
import com.zeroc.Ice.ObjectPrx;
import java.util.Scanner;


public class Client {

    public static Scanner sc=new Scanner(System.in);

    public static void main(String[] args){

        try(Communicator communicator = Util.initialize(args)){

            /** ESTO ES POR SI ACASO, MAYBE MASTER LO NECESITA
            System.out.println("Enter your name:");
            String name=sc.nextLine();
             */
            

            System.out.println("Enter the number of point you want to estimate pi with:");
            int N=sc.nextInt();
            //sc.close();

            //com.zeroc.Ice.ObjectPrx base = communicator.stringToProxy("Master:default -p 10000");
            ObjectAdapter adapter = communicator.createObjectAdapter("Suscriber");
            SuscriberI suscriber = new SuscriberI();
            
            ObjectPrx proxy = adapter.add(suscriber, Util.stringToIdentity("NotNecessaryName"));
	        adapter.activate();

            SuscriberPrx subscriberPrx = SuscriberPrx.checkedCast(proxy);
            MasterPrx master = MasterPrx.checkedCast(communicator.propertyToProxy("master.proxy"));

            if(master == null){
                throw new Error("Invalid proxy");
            }

            master.addClient(subscriberPrx); //en caso de que al final si se utilize el nombre para trackear a que cliente se envia la respuesta se agrega el param de nombre

            //publisher.onEcho("Hola desde: " + name); --> ESTO PARA QUE ERA?


            double piEstimate = master.calculatePi(N);

            System.out.println("The value of pi is: "+piEstimate); //tambien se puede poner este syso del lado del cliente
            //TODO ver si en efecto el onUpdate imprime en el lado del cliente o del server
            //en caso de ser del lado del cliente, no se necesita esa linea

            communicator.waitForShutdown();


        }

    }
    
}
