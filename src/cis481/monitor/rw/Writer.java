/**
 * @author Dr. Haiping Xu
 * Created at the CIS Department, UMass Dartmouth
 */

package cis481.monitor.rw;
import java.util.Random;
    
public class Writer extends Thread {
    private int i ;
    private RW_Controller rw = RW_Controller.getInstance();
    private Random randomGenerator = new Random();

    public Writer(int i) {
        this.i = i;
    }

    private void write(int i, String s) {
        System.out.println(" **Writer[" + i + "] is " + s) ;
        for (int j = 0; j < 10; j++) {
            System.out.print("W" + i + ".");
            try { sleep(500); } catch (InterruptedException e) { } ;
        }
        System.out.println(" **Writer[" + i + "] " + s + " done") ;
    }

    public void run() {
        do {
            rw.request_write();                      // Writing pre protocol

            write(i, "writing") ;                    // Writing

            rw.release_write();                      // Writing post protocol
              
            int waitingTime = randomGenerator.nextInt(1000); // milliseconds
            try { sleep(waitingTime) ; } catch (InterruptedException e) { } ;

        } while (true) ;
    }
}
