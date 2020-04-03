package models;

import java.util.*;

public class Event extends Post {
	private String venue;
	private String date;
	private int capacity;
	// Attendee count
	private int attCount = 0;
	
	public Event(String[] args, String venue, String date, int capacity) {
		super(args);
		this.venue = venue;
		this.date = date;
		this.capacity = capacity;
	}
	
	public static String generateId(ArrayList<Event> events) {
		int eventAmount = events.size() + 1;
		String eAmount = ""; 
		if (eventAmount < 10) {
			eAmount = "00" + eventAmount;
		}else if (eventAmount < 100) {
			eAmount = "0" + eventAmount; 
		}else {
			eAmount = Integer.toString(eventAmount);
		}
		String eveId = "EVE"+ eAmount;
		return eveId;
	}
	
	public String getPostDetails(String currentUser) {
		String format = "%-15s%s";
		String eveInfo = super.getPostDetails(currentUser);
		eveInfo += String.format(format, "Venue:", venue) + "\n" 
					+ String.format(format, "Date:", date) + "\n" 
					+ String.format(format, "Capacity:", capacity) + "\n" 
					+ String.format(format, "Attendees:", attCount) + "\n";
		return eveInfo;
	}
	
	public String getReplyDetails() {
		String attList = "Empty";
		ArrayList<Reply> replies = this.getReplies();
		if (attCount == 0 || replies.size() == 0) {
		} else {
			attList = replies.get(0).getResponderId();
			for (int i = 1; i < replies.size(); i++) {
				attList = attList + ", " + replies.get(i).getResponderId();
			}
		}
		String format = "%-15s%s";
		return String.format(format, "Attendee List:", attList);
	}
	
	@Override
	public boolean handleReply(Reply reply) {
		if (reply.getResponderId().equals(this.getCreatorId())) {
			System.err.println("You are the creator, you can not reply yourself!");
			return false;
		} 
		if (reply.getValue() != 1.0) {
			System.err.println("Wrong input!");
			return false;
		} 
		if (attCount >= capacity) {
			System.err.println("Event is full!");
			return false;
		} 
		if (ifReplierIncluded(reply.getResponderId())) {
			System.err.println("You can not join an same event twice!");
			return false;
		} 
		// Adding the new reply
		if (!this.setReplies(reply)) return false;
		setAttCount(attCount + 1);
		return true;
	}
	
	// Judging whether replier participate the event
	private boolean ifReplierIncluded(String rId) {
		ArrayList<Reply> replies = this.getReplies();
		for (int i = 0; i < replies.size(); i++) {
			if (replies.get(i).getResponderId().equals(rId)) return true;
		}
		return false;
	}
	
	private void setAttCount(int aCount) {
		this.attCount = aCount;
	}
}
