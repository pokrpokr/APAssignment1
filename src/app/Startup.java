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
						myPostDetails(username, events, sales, jobs);
						userChoice = 0;
						break;
					case 6:
						allPostsDetails(events);
						allPostsDetails(sales);
						allPostsDetails(jobs);
						userChoice = 0;
						break;
					case 7:
						closeMenu(scanner, username, events, sales, jobs);
						userChoice = 0;
						break;
					case 8:
						deleteMenu(scanner, username, events, sales, jobs);
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
	
	private static void deleteMenu(Scanner sc, String currentUser, ArrayList<Event> events, ArrayList<Sale> sales, ArrayList<Job> jobs) {
		System.out.println("Enter Post ID: ");
		String pId = sc.nextLine();
		closeDeletePost(currentUser, pId, "delete", events, sales, jobs);
	}
	
	private static void closeMenu(Scanner sc, String currentUser, ArrayList<Event> events, ArrayList<Sale> sales, ArrayList<Job> jobs) {
		System.out.println("Enter Post ID: ");
		String pId = sc.nextLine();
		closeDeletePost(currentUser, pId, "close", events, sales, jobs);
	}
	
	private static void closeDeletePost(String currentUser, String pId, String operation,ArrayList<Event> events, ArrayList<Sale> sales, ArrayList<Job> jobs) {
		Post post = null;
		if (pId.indexOf("E") == 0) {
			post = findPost(events, pId);
		} else if (pId.indexOf("S") == 0) {
			post = findPost(sales, pId);
		} else if (pId.indexOf("J") == 0) {
			post = findPost(jobs, pId);
		} else {
			System.err.println("Wrong Post Name!");
			return;
		}
		
		if (post == null) {
			System.err.println("Post not found!"); 
			return;
		}  
		if (post.isDeleted()) {
			System.err.println("Post already deleted!");
			return;
		}
		if (!post.getCreatorId().equals(currentUser)) {
			System.err.println("Operation denied! You are not creator.");
			return;
		}
		switch (operation) {
		case "close":
			if (post.isClosed()) {
				System.err.println("Post already closed!");
				break;
			}
			if (post.closePost()) {
				System.out.println("Post closed success!");
			};
			break;
		case "delete":
			if (post.deletePost()) {
				System.out.println("Post deleted success!");
			}
			break;
		default:
			System.err.println("Wrong operation!");
			break;
		}
	}
	
	private static <T> void allPostsDetails(ArrayList<T> posts) {
		for (int i = 0; i < posts.size(); i++) {
			if (!((Post) posts.get(i)).isDeleted()) {
				System.out.print(((Post) posts.get(i)).getPostDetails(null));
				System.out.println("-----------------------------------");
			}
		}
	}
	
	
	private static void myPostDetails(String currentUser, ArrayList<Event> events, ArrayList<Sale> sales, ArrayList<Job> jobs) {
		for (int i = 0; i < events.size(); i++) {
			if (events.get(i).isDeleted()) continue;
			if (events.get(i).getCreatorId().equals(currentUser)) {
				System.out.print(events.get(i).getPostDetails(currentUser));
				System.out.println(events.get(i).getReplyDetails());
				System.out.println("-----------------------------------");
			}
		}
		for (int i = 0; i < sales.size(); i++) {
			if (sales.get(i).isDeleted()) continue;
			if (sales.get(i).getCreatorId().equals(currentUser)) {
				System.out.print(sales.get(i).getPostDetails(currentUser));
				System.out.println("-- Offer History --");
				System.out.println(sales.get(i).getReplyDetails());
				System.out.println("-----------------------------------");
			}
		}
		for (int i = 0; i < jobs.size(); i++) {
			if (jobs.get(i).isDeleted()) continue;
			if (jobs.get(i).getCreatorId().equals(currentUser)) {
				System.out.print(jobs.get(i).getPostDetails(currentUser));
				System.out.println("-- Offer History --");
				System.out.println(jobs.get(i).getReplyDetails());
				System.out.println("-----------------------------------");
			}
		}
	}
	
	private static void replyToPost(Scanner sc, String username, ArrayList<Event> events, ArrayList<Sale> sales, ArrayList<Job> jobs) {
		String postName = null;
		String replyType = null;
		Post post = null;
		Reply reply = null;
		do {
			System.out.print("Enter post id or 'Q' to quit: ");
			postName = sc.nextLine();
			
			if (postName.indexOf("E") == 0) {
				replyType = "event";
				post = findPost(events, postName);
			} else if(postName.indexOf("S") == 0) {
				replyType = "sale";
				post = findPost(sales, postName);
			} else if(postName.indexOf("J") == 0) {
				replyType = "job";
				post = findPost(jobs, postName);
			} else if (postName.indexOf("Q") == 0) {
				return;
			} else {
				System.err.println("Invalid input, back to menu");
				return;
			}
		} while (postName == null || replyType == null || post == null);
		
		if (post.isDeleted()) {
			System.err.println("Post not exist!");
			return;
		}
		
		if (post.isClosed()) {
			System.err.println("Post is closed!");
			return;
		}
		
		//Show Post Details
		System.out.print(post.getPostDetails(username));
		
		double value;
		System.out.print("Enter offer or 'Q' to quit: ");
		value = sc.nextDouble();
		sc.nextLine();
		
		String[] rInfo = {postName, username, replyType};
		
		reply = createReply(rInfo, value);
		
		if (post.handleReply(reply)) {
			System.out.println("Offer accepted!");
		} else {
			System.err.println("Offer refused!");
		}
	}
	
	private static Reply createReply(String[] rInfo, double value) {
		Reply reply = new Reply(rInfo, value);
		return reply;
	}

	private static <T> T findPost(ArrayList<T> posts, String pId) {
		for (int i = 0; i < posts.size(); i++) {
			if (((Post) posts.get(i)).getId().equals(pId)) return posts.get(i);
		}
		return null;
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
