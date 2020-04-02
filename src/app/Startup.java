package app;

import java.util.*;
import java.util.Scanner;

import models.Event;
import models.Job;
import models.Post;
import models.Reply;
import models.Sale;

public class Startup {
	public static void main(String[] args) {
//		System.out.printf("%-10s","abc");
//		System.out.printf("%-8d",23);
		ArrayList<Event> events = new ArrayList<Event>();
		ArrayList<Sale> sales = new ArrayList<Sale>();
		ArrayList<Job> jobs = new ArrayList<Job>();
		ArrayList<Reply> replies = new ArrayList<Reply>();

		Scanner scanner = new Scanner(System.in);
		// Print login UI
		int choice = 0;
		do {
			loginPrint();
			System.out.print("Enter your choice: ");
			choice = scanner.nextInt();
			scanner.nextLine();
			
			switch (choice) {
			case 1:
				System.out.print("Enter username: ");
				String username = scanner.nextLine();
				ArrayList<Integer> validChoices = new ArrayList<Integer>();
				int userChoice = 0;
				do {
					welcomePrint(username, validChoices);
					System.out.print("Enter your choice: ");
					userChoice = scanner.nextInt();
					scanner.nextLine();
				
					switch (userChoice) {
					case 1:
						newEventPost(scanner, username, events);
						userChoice = 0;
						break;
					case 2:
						newSalePost(scanner, username, sales);
						userChoice = 0;
						break;
					case 3:
						newJobPost(scanner, username, jobs);
						userChoice = 0;
						break;
					case 4:
						replyToPost(scanner, username, events, sales, jobs);
						userChoice = 0;
						break;
					case 5:
						userChoice = 0;
						break;
					case 6:
						userChoice = 0;
						break;
					case 7:
						userChoice = 0;
						break;
					case 8:
						userChoice = 0;
						break;
					case 9:
						System.out.println("You have successfully logged out");
						choice = 0;
						break;
					default:
						break;
					}
				} while (!validChoices.contains(userChoice)) ;
				break;
			case 2:
				System.out.println("System exited! Thanks for using UniLink system ");
				System.exit(0);
			default:
				break;
			}
		} while (choice != 1 && choice != 2 );
	}
	
	private static void replyToPost(Scanner sc, String username, ArrayList<Event> events, ArrayList<Sale> sales, ArrayList<Job> jobs) {
		String postName = null;
		String replyType = null;
		Post post = null;
		do {
			System.out.print("Enter post id or 'Q' to quit: ");
			postName = sc.nextLine();
			
			if (postName.indexOf("E") == 0) {
				replyType = "event";
				post = findEvent(events, postName);
			} else if(postName.indexOf("S") == 0) {
				replyType = "sale";
				post = findSale(sales, postName);
			} else if(postName.indexOf("J") == 0) {
				replyType = "job";
				post = findJob(jobs, postName);
			} else if (postName.indexOf("Q") == 0) {
				return;
			} else {
				System.err.println("Invalid input, back to menu");
				return;
			}
		} while (postName == null || replyType == null || post == null);
		
		//Show Post Details
		System.out.print(post.getPostDetails(username));
		
//		double value;
//		System.out.print("Enter offer or 'Q' to quit: ");
//		value = sc.nextDouble();
//		sc.nextLine();
//		
//		String[] rInfo = {postName, username, replyType};
//		Reply reply = createReply(rInfo, value);
		
	}
	
	private static Reply createReply(String[] rInfo, double value) {
		Reply reply = new Reply(rInfo, value);
		return reply;
	}
	
	private static Event findEvent(ArrayList<Event> posts, String pId) {
		for (int i = 0; i < posts.size(); i++) {
			if (((String) posts.get(i).getId()).equals(pId)) return posts.get(i);
		}
		return null;
	}
	
	private static Sale findSale(ArrayList<Sale> posts, String pId) {
		for (int i = 0; i < posts.size(); i++) {
			if (((String) posts.get(i).getId()) == pId) return posts.get(i);
		}
		return null;
	}
	
	private static Job findJob(ArrayList<Job> posts, String pId) {
		for (int i = 0; i < posts.size(); i++) {
			if (((String) posts.get(i).getId()) == pId) return posts.get(i);
		}
		return null;
	}
	
	private static void jobDetails() {
		
	}
	
	private static void saleDetails() {
		
	}
	
	private static void eventDetails() {
		
	}
	
	private static void newJobPost(Scanner sc, String username, ArrayList<Job> jobs) {
		String id = Job.generateId(jobs);
		String title;
		String description;
		String creatorId = username;
		double proposedPrice;
		
		System.out.println("Enter details of the event below: ");
		System.out.print("Name :");
		title = sc.nextLine();
		System.out.print("Description: ");
		description = sc.nextLine();
		System.out.print("Proposed Price: ");
		proposedPrice = sc.nextDouble();
		sc.nextLine();
		String[] postInfos = {id, title, description, creatorId};
		try {
			Job job = new Job(postInfos, proposedPrice);
			jobs.add(job);
		} catch (Exception e) {
			System.err.println("Failed! Your job has not been created");
		}
		System.out.println("Success! Your job has been created with id " + id);
	}
	
	private static void newSalePost(Scanner sc, String username, ArrayList<Sale> sales) {
		String id = Sale.generateId(sales);
		String title;
		String description;
		String creatorId = username;
		double askingPrice;
		double minimumRaise;
		
		System.out.println("Enter details of the event below: ");
		System.out.print("Name :");
		title = sc.nextLine();
		System.out.print("Description: ");
		description = sc.nextLine();
		System.out.print("Asking Price: ");
		askingPrice = sc.nextDouble();
		sc.nextLine();
		System.out.print("MinimumRaise: ");
		minimumRaise = sc.nextDouble();
		sc.nextLine();
		String[] postInfos = {id, title, description, creatorId};
		try {
			Sale sale = new Sale(postInfos, askingPrice, minimumRaise);
			sales.add(sale);
		} catch (Exception e) {
			System.err.println("Failed! Your sale has not been created");
		}
		System.out.println("Success! Your sale has been created with id " + id);
	}
	
	private static void newEventPost(Scanner sc, String username, ArrayList<Event> events) {
		String id = Event.generateId(events);
		String title;
		String description;
		String creatorId = username;
		String venue;
		String date;
		int capacity;
		
		System.out.println("Enter details of the event below: ");
		System.out.print("Name :");
		title = sc.nextLine();
		System.out.print("Description: ");
		description = sc.nextLine();
		System.out.print("Venue: ");
		venue = sc.nextLine();
		System.out.print("Date: ");
		date = sc.nextLine();
		System.out.print("Capacity: ");
		capacity = sc.nextInt();
		sc.nextLine();
		String[] postInfos = {id, title, description, creatorId};
		try {
			Event event = new Event(postInfos, venue, date, capacity);
			events.add(event);
		} catch (Exception e) {
			System.err.println("Failed! Your event has not been created");
		}
		System.out.println("Success! Your event has been created with id " + id);
	}
	
	private static void welcomePrint(String username, ArrayList<Integer> validChoices) {
		System.out.println("Welcome " + username + "!");
		System.out.println("** Student Menu **");
		String[] options = {"New Event Post", "New Sale Post", "New Job Post", 
				"Reply To Post", "Display My Posts", "Display All Posts", "Close Post", "Delete Post", "Log Out"};
		for (int i = 0; i < options.length; i++) {
			validChoices.add(i + 1);
			System.out.println((i+1) + ". " + options[i]);
		}
	}
	
	private static void loginPrint() {
		System.out.println("** UniLink System **");
		System.out.println("1. Log in");
		System.out.println("2. Quit");
	}
	
	private static void readFile() {
		
	}
	
	private static void writeFile() {
		
	}
}
