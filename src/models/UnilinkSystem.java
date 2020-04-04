package models;

import java.util.ArrayList;

public class UnilinkSystem {
	public void welcomePrint(String username, ArrayList<Integer> validChoices) {
		System.out.println("Welcome " + username + "!");
		System.out.println("** Student Menu **");
		String[] options = {"New Event Post", "New Sale Post", "New Job Post", 
				"Reply To Post", "Display My Posts", "Display All Posts", "Close Post", "Delete Post", "Log Out"};
		for (int i = 0; i < options.length; i++) {
			validChoices.add(i + 1);
			System.out.println((i+1) + ". " + options[i]);
		}
	}
	
	public void loginPrint() {
		System.out.println("** UniLink System **");
		System.out.println("1. Log in");
		System.out.println("2. Quit");
	}
}
