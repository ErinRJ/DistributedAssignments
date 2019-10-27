import java.rmi.Remote;
import java.rmi.RemoteException;
public interface FileInterface extends Remote {
   public final static String SERVICENAME = "hello";

   public void createPost(String location, String dog, String duration, String owner) throws RemoteException;
   public String[] viewPosts() throws RemoteException;
   public void removePost(int job, String sitter) throws RemoteException;
   public String button4() throws RemoteException;
   public String button5() throws RemoteException;

}