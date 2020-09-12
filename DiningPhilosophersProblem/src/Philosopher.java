import java.util.Random;
import java.util.concurrent.Semaphore;


//Need to implement Runnable to be allowed to be executed by thread
class Philosopher implements Runnable {
	
    private Random randNumGenerator = new Random();

    private int count;
    private int position; 
    private Semaphore leftFork;
    private Semaphore rightFork;
    private boolean didEat;

    public Philosopher(int count, int position, Semaphore leftFork, Semaphore rightFork) {
        this.count = count;
        this.position = position; 
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.didEat = false;
    }
    
    public void run() {
        try {
            while (!didEat) {
                think();
                takeForks();
                eat();
                returnForks();            
            }
        } catch (InterruptedException e) {
            System.out.println("Philosopher " + position + "error");
        }
    }

    private void think() throws InterruptedException {
        System.out.println("Philosopher " + position + " started thinking");
        Thread.sleep(randNumGenerator.nextInt(1));
    }
    
    private void takeForks() throws InterruptedException{
    	if(shouldTakeLeftFork()) {
    		pickUpLeftFork();
    		pickUpRightFork();
    	} else {
    		pickUpRightFork();
    		pickUpLeftFork();
    	}
    }
    
    private boolean shouldTakeLeftFork() {
    	return position == count;
    }

    private void pickUpLeftFork() throws InterruptedException{
         if(leftFork.availablePermits() == 0){
            System.out.println("Philosopher " + position +" is waiting for left fork");
        }

        leftFork.acquire();
        System.out.println("Philosopher " + position + " is using left fork");
        
    }

    private void pickUpRightFork()  throws InterruptedException{
        if(rightFork.availablePermits() == 0){
            System.out.println("Philosopher " + position +" is waiting for right fork");
        }
        
        rightFork.acquire();
        System.out.println("Philosopher " + position + " is using right fork");
        
        
    }

    private void eat() throws InterruptedException {
        System.out.println("Philosopher " + position + " is eating");
        Thread.sleep(randNumGenerator.nextInt(1));
    }

    private void returnForks() {
    	leftFork.release();
    	rightFork.release();
    	didEat = true;
    	System.out.println("Philosopher" + position + "finished eating");
    }

}
