import java.util.concurrent.RecursiveTask;

/**
 * MonteCarloTask es una clase interna que extiende RecursiveTask para realizar
 * el calculo de los puntos de Monte Carlo de manera paralelizada.
 */
class MonteCarloTask extends RecursiveTask<Integer> {
    private int start, end;
    private static final int THRESHOLD = 10000;  // Umbral para dividir el trabajo

    /**
     * Constructor de la tarea de Monte Carlo.
     * 
     * @param start el indice de inicio de la tarea.
     * @param end   el indice de fin de la tarea.
     */
    MonteCarloTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Realiza el calculo de los puntos dentro del rango especificado.
     * Si el rango es mayor que el umbral, la tarea se divide en dos subtareas.
     * 
     * @return el numero de puntos que cayeron dentro del circulo.
     */
    @Override
    protected Integer compute() {
        if ((end - start) <= THRESHOLD) {
            int count = 0;
            for (int i = start; i < end; i++) {
                double x = Math.random() * 2 - 1;
                double y = Math.random() * 2 - 1;
                if (x * x + y * y <= 1) {
                    count++;
                }
            }
            return count;
        } else {
            int mid = (start + end) / 2;
            MonteCarloTask leftTask = new MonteCarloTask(start, mid);
            MonteCarloTask rightTask = new MonteCarloTask(mid, end);
            invokeAll(leftTask, rightTask);
            return leftTask.join() + rightTask.join();
        }
    }
}