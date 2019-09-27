import java.net.Socket;

public class Connector implements Runnable {
	Socket client = null;
	
	Connector(Socket client){
		this.client = client;
	}
	
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
