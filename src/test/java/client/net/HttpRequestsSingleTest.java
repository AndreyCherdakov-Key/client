package client.net;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import client.executors.ExecThreads;

class HttpRequestsSingleTest {

	HttpRequestsSingl thread = new HttpRequestsSingl(1);

	@Test
	void testRun() {
		ExecThreads.STOP = false;
		Executable ex = () -> {
			thread.run();
		};
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		ExecThreads.STOP = true;
		assertAll(ex);
		assertDoesNotThrow(ex);
	}

}
