import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FileInterface extends Remote {
   public final static String SERVICENAME = "hello";
   public String hello() throws RemoteException;
}