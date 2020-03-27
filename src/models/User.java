package models;

//This class is for expansibility
public class User {
	private String uId;
	
	public User(String uName) {
		this.uId = uName;
	}
	
	public String getId() {
		return uId;
	}
	
}
