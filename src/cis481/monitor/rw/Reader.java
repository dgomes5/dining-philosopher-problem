/**
 * @author Dr. Haiping Xu
 * Created at the CIS Department, UMass Dartmouth
 */

package cis481.monitor.rw;

import java.util.Random;

public class Reader extends Thread {
    private int i ;
    private RW_Controller rw = RW_Controller.getInstance();
    private Random randomGenerator = new Random();
    
    public Reader(int i) {
        this.i = i;
    }

    private void read(int i, String s) {
        System.out.println(" **Reader[" + i + "] is " + s) ;
        for (int j = 0; j < 10; j++) {
            System.out.print("R" + i +".");
            try { sleep(500); } catch (InterruptedException e) { } ;
        }
        System.out.println(" **Reader[" + i + "] " + s + " done") ;
     }

     public void run() {
         do {
             rw.request_read();                // Reading pre protocol

             read(i, "reading") ;              // Reading

             rw.release_read();                // Reading post protocol

             int waitingTime = randomGenerator.nextInt(1000); // milliseconds
             try { sleep(waitingTime) ; } catch (InterruptedException e) { } ;

          } while (true) ;
     }
}
