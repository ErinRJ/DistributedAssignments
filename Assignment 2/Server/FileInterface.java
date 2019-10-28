import java.rmi.Remote;
import java.rmi.RemoteException;
public interface FileInterface extends Remote {
   public final static String SERVICENAME = "hello";

   public void createPost(String location, String dog, String duration, String owner) throws RemoteException;
   public String[] viewPosts() throws RemoteException;
   public void removePost(int job, String sitter) throws RemoteException;
   public Object[] viewPersonalPosts(String owner) throws RemoteException;
   public Object[] viewJobsTaken(String sitter) throws RemoteException;
}