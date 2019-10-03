import java.io.Serializable;

public class Employees implements Serializable {

	String username;
	String hash;
	
	Employees(String name, String hash){
		this.username = name;
		this.hash = hash;
	}

}
