import java.rmi.Remote;
import java.rmi.RemoteException;

//this interface holds all functions described in the implementation class
public interface FileInterface extends Remote {
    //name of registry
    public final static String SERVICENAME = "hello";
    //function to be called
    public void createPost(String location, String dog, String duration, String owner) throws RemoteException;
    public String[] viewPosts() throws RemoteException;
    public void changeAvailability(int job, String sitter) throws RemoteException;
    public Object[] viewPersonalPosts(String owner) throws RemoteException;
    public Object[] viewJobsTaken(String sitter) throws RemoteException;
}