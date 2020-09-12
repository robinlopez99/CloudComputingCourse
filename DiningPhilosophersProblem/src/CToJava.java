import java.util.concurrent.Semaphore;

public class CToJava {

    private static int num_of_phil = 5;

    public static void main(String[] args) {
        Semaphore[] forks = new Semaphore[num_of_phil];
        Philosopher[] philosophers = new Philosopher[num_of_phil];
        
        //Create semaphores
        for (int i = 0; i < num_of_phil; i++) {
            forks[i] = new Semaphore(1);
        }
        
        //Create philosophers(threads) and start them
        for (int i = 0; i < num_of_phil; i++) {
            philosophers[i] = new Philosopher(num_of_phil, i, forks[i], forks[(i + 1) % num_of_phil]);
            new Thread(philosophers[i]).start();
        }

    }

}

