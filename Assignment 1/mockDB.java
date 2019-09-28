import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class mockDB {
	
	static Socket client = null;
	static ServerSocket myServer = null;
	static private Connector connect = null;
	static Thread thread = null;
	
	public static void main(String[] args) throws IOException, ClassNotFoundException {
			
		// CONNECT TO THE CLIENT - send to connector
		myServer = new ServerSocket(5000);
		System.out.println("Server started on port 5000");
		while(true) {
			client = myServer.accept();
			//create thread
			connect = new Connector(client);
			thread = new Thread(connect);
			thread.start();
		}
				
	}
}

