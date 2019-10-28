import java.io.Serializable;

//this class holds all posting information submitted by the clients
public class Posting implements Serializable {
    int id;
    String location;
    String dogB;
    String duration;
    String owner;
    Boolean accepted;
    String sitter;

    Posting(int id, String loc, String dog, String duration, String owner, Boolean accepted, String sitter){
        super();
        this.id = id;
        this.location = loc;
        this.dogB = dog;
        this.duration = duration;
        this.owner = owner;
        this.accepted = accepted;
        this.sitter = sitter;
    }
}
