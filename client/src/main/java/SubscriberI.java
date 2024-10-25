import com.zeroc.Ice.Current;

public class SuscriberI implements Demo.Suscriber {
    
    @Override
    public void onUpdate(double piEstimate, Current current){

	    System.out.println("The value of pi is: "+piEstimate);

    }
}