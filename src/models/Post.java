package models;

import java.util.ArrayList;

public abstract class Post {
	private String id;
	private String title;
	private String description;
	private String creatorId;
	private String status;
	private ArrayList<Reply> replies;
	
}
