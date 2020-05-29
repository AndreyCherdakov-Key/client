package client.constants;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;

public interface Constants {
	
	public static final String LINK = "http://localhost:8080/?clientId=";
	public static final int TIMEOUT_MINUTES = 5;
	public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	public static final int SLEEP = 2000;
	public static final String PACKAGE_TO_SCAN = "client";

}
