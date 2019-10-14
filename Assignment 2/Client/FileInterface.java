import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FileInterface extends Remote {
	//name of registry
   public final static String SERVICENAME = "hello";
   //function to be called
   public String hello() throws RemoteException;
}