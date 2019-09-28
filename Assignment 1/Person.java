import java.io.Serializable;

public class Person implements Serializable{
	String name;
	String address;
	String phoneNum;
	String email;
	String memType;
	
	Person(String nm, String add, String phone, String email, String member){
		super();
		this.name = nm;
		this.address = add;
		this.phoneNum = phone;
		this.email = email;
		this.memType = member;	
	}
}
