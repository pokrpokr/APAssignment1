package models;

import java.util.*;

import exceptions.SavingReplyException;

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
		ArrayList<Reply> replies = this.getReplies();
		if (attCount == 0 || replies.size() == 0) return "Empty";
		String attList = replies.get(0).getResponderId();
		for (int i = 1; i < replies.size(); i++) {
			attList = attList + ", " + replies.get(i).getResponderId();
		}
		return attList;
	}
	
	@Override
	public boolean handleReply(Reply reply) {
		if (reply.getResponderId().equals(this.getCreatorId()))  return false;
		if (reply.getValue() != 1.0) return false;
		if (attCount >= capacity) return false;
		if (ifReplierIncluded(reply.getResponderId())) return false;
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
