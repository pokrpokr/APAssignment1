package models;

import java.util.ArrayList;

import exceptions.SavingReplyException;

public class Sale extends Post {
	private double askingPrice;
	private double highestOffer = 0.0;
	private double minimumRaise;
	
	public Sale(String[] args, double askingPrice, double minimumRaise) {
		super(args);
		this.askingPrice = askingPrice;
		this.minimumRaise = minimumRaise;
	}
	
	public static String generateId(ArrayList<Sale> sales) {
		int saleAmount = sales.size() + 1;
		String sAmount = ""; 
		if (saleAmount < 10) {
			sAmount = "00" + saleAmount;
		}else if (saleAmount < 100) {
			sAmount = "0" + saleAmount; 
		}else {
			sAmount = Integer.toString(saleAmount);
		}
		String salId = "SAL"+ sAmount;
		return salId;
	}

	public String getPostDetails(String currentUser) {
		String format = "%-15s%s";
		String salInfo = super.getPostDetails(currentUser);
		if (this.getCreatorId().equals(currentUser)) {
			salInfo += String.format(format, "Asking Price:", askingPrice) + "\n";
		}
		salInfo += String.format(format, "Minimum Raise:", minimumRaise) + "\n" 
					+ String.format(format, "Highest Offer:", highestOffer) + "\n";
		return salInfo;
	}
	
	public String getReplyDetails() {
		ArrayList<Reply> replies = this.getReplies();
		if (replies.size() == 0) return "No Offer";
		String offers = replies.get(0).getResponderId() + ": " + replies.get(0).getValue();
		for (int i = 1; i < replies.size(); i++) {
			offers += "\n" + replies.get(i).getResponderId() + ": " + replies.get(i).getValue();
		}
		
		return offers;
	}
	
	@Override
	public boolean handleReply(Reply reply) {
		if (reply.getResponderId().equals(this.getCreatorId()))  return false;
		double offeredPrice = reply.getValue();
		if (offeredPrice < (this.highestOffer + this.minimumRaise)) return false;
		
		if (!this.setReplies(reply)) return false;
		if (offeredPrice >= this.askingPrice) {
			this.closePost();
		}
		setHighestOffer(reply.getValue());
		return true;
	}
	
	private void setHighestOffer(double offer) {
		this.highestOffer = offer;
	}
}
