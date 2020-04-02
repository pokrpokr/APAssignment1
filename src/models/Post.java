package models;

import java.lang.reflect.Field;
import java.util.ArrayList;

import exceptions.SavingReplyException;

public abstract class Post {
	private String id;
	private String title;
	private String description;
	private String creatorId;
	private String status;
	private ArrayList<Reply> replies;
	private boolean isDeleted = false;
	
	public Post(String[] args) {
		this.id = args[0];
		this.title = args[1];
		this.description = args[2];
		this.creatorId = args[3];
		this.status = "OPEN";
		this.replies = new ArrayList<Reply>();
	}
	
	abstract protected boolean handleReply(Reply reply);
	
	public String getPostDetails(String currentUser) {
		String format = "%-15s%s";
		String information = String.format(format, "Id:", id) + "\n" 
							+ String.format(format, "Title", title) + "\n"
							+ String.format(format, "Description:", description) + "\n"
							+ String.format(format, "Creator ID:", creatorId) + "\n" 
							+ String.format(format, "Status:", status) + "\n";
		return information;
	}
	
	//Soft delete
	protected boolean deletePost() {
		this.isDeleted = true;
		return isDeleted;
	}
	
	//Close post
	protected boolean closePost() {
		this.status = "CLOSED";
		return true;
	}
	
	public String getId() {
		return this.id;
	}
	
	public String getCreatorId() {
		return this.creatorId;
	}
	
	public ArrayList<Reply> getReplies() {
		return this.replies;
	}
	
	public boolean setReplies(Reply reply) {
		try {
			this.replies.add(reply);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
}
