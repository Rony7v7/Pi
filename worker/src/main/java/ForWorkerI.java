import com.zeroc.Ice.Current;

public class ForWorkerI implements Demo.ForWorker {



    @Override
    public int throwPoints(int n,Current current) {
        int count = 0;
        for (int i = 0; i < n; i++) {
            double x = Math.random() * 2 - 1;
            double y = Math.random() * 2 - 1;
            if (x * x + y * y <= 1) {
                count++;
            }
        }
        return count;


    }



}
