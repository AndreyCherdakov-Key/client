package client.net;

import java.io.IOException;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;

import client.constants.Constants;
import client.executors.ExecThreads;
/*
 * Thread
 * I implement Runnable here because I don’t need to return anything from threads, I don’t need to use Callable
 */
public class HttpRequestsSingl implements Runnable {
	
	Logger logger;
	private GenericUrl url;
	private int clientId;
	private Random rnd;

	public HttpRequestsSingl(int clientId) {
		super();
		this.clientId = clientId;
		this.rnd = new Random();
		this.logger = LoggerFactory.getLogger(this.getClass());
	}
	
/*
 * In a loop, I create an url with the client id parameter. 
 * I take the client randomly from one to the number of client.
 */
	public void run() {
		logger.info(Thread.currentThread().getName() + " is running");
		int client, res = 0;
		/*
		 * The loop stops and the thread ends when the global variable is true
		 */
		while (!ExecThreads.STOP) {
			client = rnd.nextInt(clientId) + 1;
			url = new GenericUrl(Constants.LINK + client);
			/*
			 * I create a HTTP request and execute it.
			 * Save return code to the variable
			 * If I get code 503 from the server, I get an exception. If i have exception a save digit 503
			 */
			try {
				HttpRequest httpRequest = Constants.HTTP_TRANSPORT.createRequestFactory().buildGetRequest(url);
				HttpResponse response = httpRequest.execute();
				res = response.getStatusCode();
				response.disconnect();
			} catch (HttpResponseException e1) {
				res = Integer.parseInt(e1.getMessage());
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
			logger.info(Thread.currentThread().getName() + " " + Thread.currentThread().getId() + ": client " + client + " code " + res);
			/*
			 * The thread falls asleep for a random number of milliseconds from 0 to 2000
			 */
			try {
				Thread.sleep(rnd.nextInt(Constants.SLEEP));
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
			}
		}
		logger.info(Thread.currentThread().getName() + " was finished");
    }

}
