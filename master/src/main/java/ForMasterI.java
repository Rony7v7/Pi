import Demo.ForClientPrx;
import Demo.ForWorkerPrx;
import com.zeroc.Ice.Current;

import java.util.ArrayList;
import java.util.HashMap;


public class ForMasterI implements Demo.ForMaster {
    private ArrayList<ForWorkerPrx> workers;
    private HashMap<String, ForClientPrx> clients;

    public ForMasterI() {
        workers = new ArrayList<>();
        clients = new HashMap<>();
    }


    @Override
    public void addClient(String name, ForClientPrx client, Current current) {
        clients.put(name, client);
        System.out.println("Client added");
    }

    @Override
    public void addWorker(ForWorkerPrx worker, Current current) {
        workers.add(worker);
        System.out.println("Worker added");

    }

    @Override
    public void calculate(String name,int N,Current current){
        if(workers.isEmpty()){
            System.out.println("No workers available");
            return;
        }

        if(clients.get(name) == null){
            System.out.println("Client not found");
            return;
        }

        System.out.println("Calculating Pi with " + N + " points");
        int result = 0;
        int n = pointsPerWorker(N);


        for (ForWorkerPrx worker : workers) {

            result += worker.throwPoints(n);
            System.out.println(result);


        }
        double pi = 4 * ((double) result /N);

        notifyResult(name, pi);

    }

    public void notifyResult(String name, double result){
        ForClientPrx client = clients.get(name);
        client.printResult(result);

    }

    public int pointsPerWorker(int N) {
        return (int) Math.ceil((double) N / workers.size());
    }


}
