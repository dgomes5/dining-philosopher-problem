/**
 * @author Daniel Gomes, John Gomes & Robert Larrivee
 * Created at the CIS Department, UMass Dartmouth
 */

package cis481.monitor.dp;

import java.util.Random;

public class Philosopher extends Thread {
	private int id;
	private DiningRoom dr = DiningRoom.getInstance();
	private Random randomGenerator = new Random();

	public Philosopher(int id) {
		this.id = id;
	}

	private void eat(int id, String s) {
		System.out.println(" **Phil[" + id + "] is " + s);
		for (int j = 0; j < 10; j++) {
			System.out.print("P" + id + ".");
			try {
				sleep(500);
			} catch (InterruptedException e) {
			}
			;
		}
		System.out.println(" **Phil[" + id + "] has finished " + s);
	}

	public void run() {
		do {
			// phil enters the room
			dr.enterRoom(id);
			
			// phil tries to get a fork
			dr.getFork(id);

			// eat when phil has forks
			eat(id, "eating");

			// phil releases fork when done eating
			dr.relFork(id);
			
			// phil leaves the room
			dr.exitRoom(id);

			int waitingTime = randomGenerator.nextInt(1000); // milliseconds
			try {
				sleep(waitingTime);
			} catch (InterruptedException e) {
			}
			;

		} while (true);
	}
}
