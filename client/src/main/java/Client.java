import Demo.ForClientPrx;
import Demo.ForMasterPrx;
import com.zeroc.Ice.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Client {
    public static void main(String[] args) {
        try(Communicator communicator = Util.initialize(args, "properties.cfg")){

            System.out.println("Give me a name: ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String name = reader.readLine();

            ObjectAdapter adapter = communicator.createObjectAdapter("client");
            ForClientI client = new ForClientI();

            ObjectPrx proxy = adapter.add(client, Util.stringToIdentity("NotNecessaryName"));
            adapter.activate();

            ForClientPrx clientPrx = ForClientPrx.checkedCast(proxy);

            ForMasterPrx master = ForMasterPrx.checkedCast(communicator.propertyToProxy("master.proxy"));

            if(master == null){
                throw new Error("Master is Null");
            }

            master.addClient(name, clientPrx);

            String msg = "";
            System.out.println("\nSend a message with the format: NameClient::N points");
            while( (msg = reader.readLine()) != null){
                String[] command = msg.split("::");
                master.calculate(command[0], Integer.parseInt(command[1]));

            }

            communicator.waitForShutdown();
            reader.close();

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}