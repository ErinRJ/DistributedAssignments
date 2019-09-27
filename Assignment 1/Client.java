import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;

public class Client {
	static Socket myClient = null;

	public static void main(String[] args) throws UnknownHostException, IOException {
		PrintStream out = null;
		
		// create GUI
		JFrame frame = new JFrame("new gui");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//set the size of the new window
		frame.setSize(600,300);
		
		// THINGS TO INCLUDE
		// have different frames/menus:
			// - add new patron
			// - find patron's information, options to delete, update, etc.
			// - get list of all fitness classes
			// - who's in which fitness class
		
		frame.setVisible(true);
		
		// CONNECT TO SERVER
		myClient = new Socket("127.0.0.1", 5000);
		out = new PrintStream(myClient.getOutputStream());
		// send it requests for whatever
		// could send it as one string, the server can take that one string
		//     set the delimiter as ',' and divide it up?
		
		String personInfo = "jeff bezos,555 sin st.,555-555-5555,email@email.com,part-time";
		out.println(personInfo);
		
		
	}

}
