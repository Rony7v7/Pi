import com.zeroc.Ice.Current;


public class ForClientI implements Demo.ForClient {


    @Override
    public void printResult(double result, Current current) {
        System.out.println("The result of pi approximation is" + result);

    }


}
