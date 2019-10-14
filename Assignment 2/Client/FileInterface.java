import java.rmi.Remote;
import java.rmi.RemoteException;

public interface FileInterface extends Remote {
    //name of registry
    public final static String SERVICENAME = "hello";
    //function to be called
    public String hello() throws RemoteException;
    public String button1() throws RemoteException;
    public String button2() throws RemoteException;
    public String button3() throws RemoteException;
    public String button4() throws RemoteException;
    public String button5() throws RemoteException;
}