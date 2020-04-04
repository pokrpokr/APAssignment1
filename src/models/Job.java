package models;

import java.util.ArrayList;

public class Job extends Post {
	private double proposedPrice;
	private double lowestOffer;
	
	public Job(String[] args, double propPrice) {
		super(args);
		this.proposedPrice = propPrice;
		//Initial lowest offer as same as proposed price
		this.lowestOffer = propPrice;
	}
	
	public static String generateId(ArrayList<Job> jobs) {
		int jobAmount = jobs.size() + 1;
		String jAmount = ""; 
		if (jobAmount < 10) {
			jAmount = "00" + jobAmount;
		}else if (jobAmount < 100) {
			jAmount = "0" + jobAmount; 
		}else {
			jAmount = Integer.toString(jobAmount);
		}
		String jobId = "JOB"+ jAmount;
		return jobId;
	}
	
	public String getPostDetails(String currentUser) {
		String format = "%-20s%s";
		String jobInfo = super.getPostDetails(currentUser);
		String.format(format, "Lowest Offer:", lowestOffer);
		jobInfo += String.format(format, "Proposed price: ", "$" + proposedPrice) + "\n";
		if (lowestOffer == proposedPrice) {
			jobInfo += String.format(format, "Lowest Offer:", "No Offer") + "\n";
		}else {
			jobInfo += String.format(format, "Lowest Offer: ", "$" + lowestOffer) + "\n";
		}
		
		return jobInfo;
	}
	
	public String getReplyDetails() {
		ArrayList<Reply> replies = this.getReplies();
		if (replies.size() == 0) return "No Offer";
		String format = "%-20s%s";
		String offers = String.format(format, replies.get(replies.size() - 1).getResponderId() + ": ", "$" + replies.get(replies.size() - 1).getValue());
		for (int i = replies.size() - 2; i >= 0; i--) {
			offers += "\n" + String.format(format, replies.get(i).getResponderId() + ": ", "$" + replies.get(i).getValue());
		}
		
		return offers;
	}
	
	public boolean handleReply(Reply reply) {
		if (reply.getResponderId().equals(this.getCreatorId())) {
			System.err.println("You are the creator, you can not reply yourself!");
			return false;
		} 
		if (reply.getValue() >= this.lowestOffer) {
			System.err.println("There is a lowest offer!");
			return false;
		} 
		if (!this.setReplies(reply)) return false;
		setLowestOffer(reply.getValue());
		return true;
	}
	
	private void setLowestOffer(double offer) {
		this.lowestOffer = offer;
	}
}
