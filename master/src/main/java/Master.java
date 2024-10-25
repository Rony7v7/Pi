import com.zeroc.Ice.*;



public class Master {

    public static void main(String[] args) {
        try(Communicator communicator = Util.initialize(args, "properties.cfg")){

            ObjectAdapter adapter = communicator.createObjectAdapter("services");
            ForMasterI master = new ForMasterI();
            adapter.add(master, Util.stringToIdentity("Master"));
            adapter.activate();
            System.out.println("Master is ready to receive requests...");




            communicator.waitForShutdown();


        }



    }

}