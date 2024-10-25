import Demo.ForMasterPrx;
import Demo.ForWorkerPrx;
import com.zeroc.Ice.*;

public class Worker {
    public static void main(String[] args) {
        try(Communicator communicator = Util.initialize(args, "properties.cfg")){

            ObjectAdapter adapter = communicator.createObjectAdapter("Worker");

            ForWorkerI worker = new ForWorkerI();
            adapter.add(worker, Util.stringToIdentity("Worker"));

            adapter.activate();

            ForWorkerPrx workerPrx = ForWorkerPrx.checkedCast(adapter.createProxy(Util.stringToIdentity("Worker")));
            ForMasterPrx master = ForMasterPrx.checkedCast(communicator.propertyToProxy("master.proxy"));

            if(master == null){
                throw new Error("Master is Null");
            }

            master.addWorker(workerPrx);

            communicator.waitForShutdown();

        }

    }
}