/**
 * @author Dr. Haiping Xu
 * Created at the CIS Department, UMass Dartmouth
 */

package cis481.monitor.rw;

public class RW_Controller {
    private static RW_Controller INSTANCE = null; 
    private static int N = 3 ;
    private int nr = 0, nw = 0;

    // Define RW_Controller as a singleton object.
    // A private constructor prevents any other class from instantiating.
    private RW_Controller() {}
    
    // Use lazy instantiation to create the singleton; as a result, the singleton instance 
    // is not created until the getInstance() method is called for the first time. 
    // This technique ensures that the singleton instance is created only when needed.
    public static synchronized RW_Controller getInstance() {
    	if (INSTANCE == null)
    	    INSTANCE = new RW_Controller();
        return INSTANCE;
    }
    
    public synchronized void request_read() {
        while (nw > 0) {
            try { wait(); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }
        nr = nr + 1;
    }

    public synchronized void release_read() {
        nr = nr - 1;
        if (nr == 0) notify();
    }

    public synchronized void request_write() {
        while ((nr > 0) || (nw > 0)) {
            try { wait(); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }
        nw = nw + 1;
    }

    public synchronized void release_write() {
        nw = nw - 1;
        notifyAll();
    }

    public static void main(String [] args) {
        Reader p[] = new Reader[N];
        Writer q[] = new Writer[N];

        System.out.println("Creating 3 readers: Reader[0], Reader[1], and Reader[2] ...");
        for(int i=0; i<N; i++) {
          p[i] = new Reader(i);
          p[i].start();
        }
        
        System.out.println("Creating 3 writers: Writer[0], Writer[1], and Writer[2] ...");
        for(int i=0; i<N; i++) {
              q[i] = new Writer(i);
              q[i].start();
        }
    }
}
