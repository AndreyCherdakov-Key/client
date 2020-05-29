package client.executors;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

class ExecThreadsTest {

	@Test
	void testGetInstance() {
		assertNotNull(ExecThreads.getInstance());
		ExecThreads.class.isInstance(ExecThreads.getInstance());
	}

	@Test
	void testStop() {
		ExecThreads.getInstance().runRequests(1);
		Executable ex = () -> ExecThreads.getInstance().stop();
		assertAll(ex);
		assertDoesNotThrow(ex);
	}

}
