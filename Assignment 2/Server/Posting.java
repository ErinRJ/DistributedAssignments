import java.io.Serializable;

public class Posting implements Serializable {
    String location;
    String dogB;
    String duration;

    Posting(String loc, String dog, String duration){
        super();
        this.location = loc;
        this.dogB = dog;
        this.duration = duration;
    }
}
