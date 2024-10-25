import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Util;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.ObjectAdapter;
import java.util.concurrent.ForkJoinPool;
import Demo.Worker;
import Demo.MasterPrx;

/**
 * WorkerI es una clase que implementa la interfaz Worker y realiza la estimacion
 * de puntos para calcular Pi utilizando el metodo de Monte Carlo.
 * Los calculos se paralelizan utilizando ForkJoinPool.
 */
public class WorkerI implements Worker {
    private ForkJoinPool pool = new ForkJoinPool();
    private MasterPrx master;

    /**
     * Constructor del Worker que establece la conexion con el Master.
     * 
     * @param communicator El comunicador de ICE utilizado para conectarse al Master.
     */
    public WorkerI(Communicator communicator) {
        try {
            System.out.println("[INFO] Intentando conectar al Master...");
            master = MasterPrx.checkedCast(communicator.stringToProxy("default -p 10000 -h localhost")); // CONEXION AL MASTER
            if (master == null) {
                throw new Error("[ERROR] Proxy del Master es invalido.");
            }
            System.out.println("[INFO] Conectado exitosamente al Master.");
        } catch (Exception e) {
            System.err.println("[ERROR] No se pudo conectar al Master: " + e.getMessage());
        }
    }

    /**
     * Lanza los puntos para estimar el valor de Pi.
     * 
     * @param n la cantidad de puntos a generar.
     * @param __current el contexto actual de la llamada ICE.
     */
    @Override
    public void throwPoints(int n, Current __current) {
        System.out.println("[INFO] Iniciando el calculo de " + n + " puntos.");
        // Utilizamos ForkJoinPool para paralelizar el calculo de los puntos
        int pointsInCircle = pool.invoke(new MonteCarloTask(0, n));
        System.out.println("[INFO] Calculo completado. Puntos dentro del circulo: " + pointsInCircle);
        
        // Reportar el resultado al master
        if (master != null) {
            try {
                master.reportPoints(pointsInCircle);  // Cambiado a un método adecuado para reportar puntos al Master
                System.out.println("[INFO] Resultados reportados al Master.");
            } catch (Exception e) {
                System.err.println("[ERROR] Error al reportar los puntos al Master: " + e.getMessage());
            }
        } else {
            System.err.println("[ERROR] No se pudo reportar los puntos al Master. El Master no esta conectado.");
        }
    }

    public static void main(String[] args) {
        // Inicializar el Communicator utilizando el archivo de configuración del Worker
        try (Communicator communicator = Util.initialize(args, "properties.cfg.")) {

            // Crear un adaptador para manejar el servicio del Worker
            ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("WorkerAdapter", "tcp -h 127.0.0.1 -p 10001");
            System.out.println("[INFO] Adaptador del Worker creado y escuchando en el puerto 10001.");

            // Instanciar el Worker y pasar el Communicator al constructor para conectarse al Master
            WorkerI worker = new WorkerI(communicator);
            System.out.println("[INFO] Worker creado y conectado al Master.");

            // Añadir la instancia del Worker al adaptador para que esté disponible en la red
            adapter.add(worker, Util.stringToIdentity("Worker1"));
            System.out.println("[INFO] Worker añadido al adaptador y registrado con el nombre 'Worker1'.");

            // Activar el adaptador para empezar a escuchar peticiones del Master
            adapter.activate();
            System.out.println("[INFO] Worker activado y listo para recibir peticiones...");

            // Mantener el Worker en ejecución hasta que se cierre la aplicación
            communicator.waitForShutdown();
            System.out.println("[INFO] Worker finalizando...");

        } catch (Exception e) {
            System.err.println("[ERROR] Error durante la inicialización del Worker: " + e.getMessage());
        }   
    }
}
