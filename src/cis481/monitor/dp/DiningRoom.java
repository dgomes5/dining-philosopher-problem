/**
 * @author Daniel Gomes, John Gomes & Robert Larrivee
 * Created at the CIS Department, UMass Dartmouth
 */

package cis481.monitor.dp;

import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class DiningRoom {
	private static DiningRoom INSTANCE = null;
	private static int N = 5; // number of philosophers
	private static Lock lock;

	private enum States {
		hungry, thinking, eating
	}; // states for philosophers

	private static States[] state;
	private static Condition[] self;
	private List<Integer> np = new ArrayList<Integer>(); // philosophers in room

	private DiningRoom() {}

	public static synchronized DiningRoom getInstance() {
		lock = new ReentrantLock();
		state = new States[N];
		self = new Condition[N];

		for (int i = 0; i < N; i++) {
			state[i] = States.thinking;
			self[i] = lock.newCondition();
		}

		if (INSTANCE == null)
			INSTANCE = new DiningRoom();
		return INSTANCE;
	}

	public synchronized void getFork(int id) {
		lock.lock();

		try {
			state[id] = States.hungry;
			test(id);
		} finally {
			lock.unlock();
		}

	}

	public synchronized void relFork(int id) {
		lock.lock();
		try {
			state[id] = States.thinking;

			test((id + 1) % 5);
			test((id + 4) % 5);
		} finally {
			lock.unlock();
		}

	}

	private void test(int id) {
		if ((state[(id + 1) % N] != States.eating)
				&& (state[(id + 4) % N] != States.eating && state[id] == States.hungry)) {

			state[id] = States.eating;

			self[id].signal();
		}
	}

	public synchronized void enterRoom(int id) {
		np.add(id);
		Collections.sort(np);
		System.out.println("-- Phil[" + id + "] enters room { " + np + " }");
	}

	public synchronized void exitRoom(int id) {
		np.removeAll(Arrays.asList(id));
		Collections.sort(np);
		System.out.println("-- Phil[" + id + "] exits room { " + np + " }");
	}

	public static void main(String[] args) {
		Philosopher p[] = new Philosopher[N];

		System.out.println("Creating 5 philosophers: Philosopher[0], Philosopher[1], and Philosopher[2] ...");
		for (int id = 0; id < N; id++) {
			p[id] = new Philosopher(id);
			p[id].start();
		}

	}
}
