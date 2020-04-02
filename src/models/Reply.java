package models;

public class Reply {
	private String postId;
	private double value;
	private String responderId;
	private String type;
	
	public Reply(String[] args, double value) {
		this.postId = args[0];
		this.responderId = args[1];
		this.type = args[2];
		this.value = value;
	}
	
	public String getPostId() {
		return	postId;
	}
	
	public String getResponderId() {
		return responderId;
	}
	
	public double getValue() {
		return value;
	}
	
	public String getType() {
		return type;
	}
	
	public boolean setType(String type) {
		try {
			this.type = type;
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean setPostId(String pId) {
		try {
			this.postId = pId;
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean setResponderId(String rId) {
		try {
			this.responderId = rId;
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean setValue(int value) {
		try {
			this.value = value;
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
