package client;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import client.executors.ExecThreads;

public class ClientApp {
	
	private static Scanner scan;
	private static int numberOfClient;

	public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        scan = new Scanner(System.in);
        /*
         * Waiting for a number of clients to be entered greater than zero
         */
        scanDigit();
        /*
         * Running threads generating server requests
         */
        runThreads();
        /*
         * Waiting for input for safe completion of working threads
         */
		stopProgramm();
	}
	
	private static void scanDigit() {
		System.out.println("Please enter count of clients");
		while (true) {
        	try {
        		numberOfClient = Integer.parseInt(scan.nextLine());
        	} catch (NumberFormatException e) {
        		System.out.println("Please enter again ...");
        	}
        	if (numberOfClient > 0) {
        		break;
        	}
        }
	}
	
	private static void runThreads() {
			ExecThreads.getInstance().runRequests(numberOfClient);
	}
	
	private static void stopProgramm() {
		System.out.println("Press 'Enter' to terminate");
        scan.nextLine();
		ExecThreads.getInstance().stop();
        System.out.println("Exit programm");
        System.exit(0);
	}

}
