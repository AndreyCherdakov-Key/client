package client.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import client.constants.Constants;
import client.factory.ObjectFactory;

/*
 * Thread control class
 * I create a flow control system like Singleton
 */

public final class ExecThreads {

	private volatile static ExecThreads instance;
	private static ExecutorService executor;
	/*
	 * Thread safe termination variable
	 */
	public static volatile boolean STOP;
	
	private ExecThreads() {
		super();
		ExecThreads.STOP = false;
	}
	
	public static ExecThreads getInstance() {
		if (instance == null) {
			synchronized (ExecThreads.class) {
				if (instance == null) {
					instance = new ExecThreads();
				}
			}
		}
		return instance;
	}

	/*
	 * I start the threads generating HTTP requests to the server. The number of threads equals the number of clients
	 * Also here I create an executor with a fixed number of threads. 
	 * I can t create an executor together with a class instance because I don t know the number of threads
	 */
	public void runRequests(int clientId) {
		if (executor == null) {
			synchronized (ExecThreads.class) {
				if (executor == null) {
					executor = Executors.newFixedThreadPool(clientId);
				}
			}
		}
		/*
		 * To create an instace of thread class, I use ObjectFactory
		 */
		for (int i = 1; i < clientId + 1; i++) {
			executor.execute(ObjectFactory.getInstance().createObject(Runnable.class, i));
		}
	}
	/*
	 * If I accidentally call the mrthod stop before method runRequests, I must definitely check if my executor is created or not
	 */
	public void stop() {
		if (executor == null) {
			return;
		}
		System.out.println("Starting to terminate all threads");
		/*
		 * Enable the flag to safely stop all threads
		 */
		ExecThreads.STOP = true;
		executor.shutdown();
		/*
		 * Waiting for all threads to complete with set timeouts in 5 seconds
		 */
		try {
			executor.awaitTermination(Constants.TIMEOUT_MINUTES, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}
	}
}
