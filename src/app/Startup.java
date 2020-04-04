package app;

import java.util.*;
import java.util.Scanner;

import models.Event;
import models.Job;
import models.Post;
import models.Reply;
import models.Sale;
import models.UnilinkSystem;

public class Startup {
	public static void main(String[] args) {
		ArrayList<Event> events = new ArrayList<Event>();
		ArrayList<Sale> sales = new ArrayList<Sale>();
		ArrayList<Job> jobs = new ArrayList<Job>();
		events = initData(events, "event");
		sales = initData(sales, "sale");
		jobs = initData(jobs, "job");
		UnilinkSystem uni = new UnilinkSystem();
		
		Scanner scanner = new Scanner(System.in);
		// Print login UI
		int choice = 0;
		do {
			uni.loginPrint();
			System.out.print("Enter your choice: ");
			try {
				choice = scanner.nextInt();
			}
			catch (InputMismatchException ime) {
				System.err.println("Wrong input! Please enter a correct number!");
			}
			catch (NoSuchElementException nee) {
				System.err.println("Wrong input! Please enter a number!");
			}
			catch (IllegalStateException ise) {
				System.err.println("System error! Please try again!");
			}
			catch (Exception e) {
				System.err.println("System error!");
			}
			scanner.nextLine();
			
			switch (choice) {
			case 1:
				String username = "";
				do {
					System.out.print("Enter username: ");
					username = scanner.nextLine();
					if (username.isBlank()) System.err.println("Please enter user name!");
				} while (username.isBlank());
				
				
				ArrayList<Integer> validChoices = new ArrayList<Integer>();
				int userChoice = 0;
				do {
					uni.welcomePrint(username, validChoices);
					System.out.print("Enter your choice: ");
					try {
						userChoice = scanner.nextInt();
					} catch (InputMismatchException ime) {
						System.err.println("Wrong input! Please enter a correct number!");
					}
					catch (NoSuchElementException nee) {
						System.err.println("Wrong input! Please enter a number!");
					}
					catch (IllegalStateException ise) {
						System.err.println("System error! Please try again!");
					}
					catch (Exception e) {
						System.err.println("System error!");
					}
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
						System.err.println("No such operation! Please try again!");
						break;
					}
				} while (!validChoices.contains(userChoice)) ;
				break;
			case 2:
				System.out.println("System exited! Thanks for using UniLink system ");
				System.exit(0);
			default:
				System.err.println("No such operation! Please try again!");
				try {
					Thread.sleep(3);
				} catch (InterruptedException ie) {}
				break;
			}
		} while (choice != 1 && choice != 2 );
	}
	
	private static <T> ArrayList<T> initData(ArrayList<T> posts, String type) {
		switch (type) {
		case "event":
			String[] eveInfos = {"EVE001", "New student welcome party", "A party for new student to know each others", "s3766925"};
			Event event = new Event(eveInfos, "80.002.100", "02/04/2020", 10);
			posts.add((T) event);
			String[] ereInfos = {"EVE001", "s3766926", "event"};
			Reply reply = new Reply(ereInfos, 1.0);
			((Event) posts.get(0)).handleReply(reply);
			break;
		case "sale":
			String[] salInfos = {"SAL001", "Second hand MacBook", "Half new MacBook", "s3755926"};
			Sale sale = new Sale(salInfos, 100.0, 20.0);
			posts.add((T) sale);
			String[] sreInfos = {"SAL001", "s3756872", "sale"};
			Reply reply2 = new Reply(sreInfos, 40.0);
			((Sale) posts.get(0)).handleReply(reply2);
			break;
		case "job":
			String[] jobInfos = {"JOB001", "PF mentoring", "Looking for Programming Fundamental tutor", "s3754123"};
			Job job = new Job(jobInfos, 400);
			posts.add((T) job);
			String[] jreInfos = {"JOB001", "s3766925", "job"};
			Reply reply3 = new Reply(jreInfos, 350);
			((Job) posts.get(0)).handleReply(reply3);
			break;
		}
		return posts;
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
				System.err.println("Invalid Post ID, back to menu");
				return;
			}
			
			if (post == null) {
				System.err.println("Post not found!");
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
		
		double value = 0;
		int validInput = 0;
		do {
			System.out.print("Enter offer or 'Q' to quit(If you are joining an event, please enter 1): ");
			try {
				value = sc.nextDouble();
			} catch (InputMismatchException ime) {
				System.err.println("Wrong input! Please enter a correct number!");
				try {
					Thread.sleep(3);
				} catch (InterruptedException ie) {}
				sc.nextLine();
				continue;
			}
			catch (NoSuchElementException nee) {
				System.err.println("Wrong input! Please enter a number!");
				try {
					Thread.sleep(3);
				} catch (InterruptedException ie) {}
				sc.nextLine();
				continue;
			}
			catch (IllegalStateException ise) {
				System.err.println("System error! Please try again!");
				try {
					Thread.sleep(3);
				} catch (InterruptedException ie) {}
				sc.nextLine();
				continue;
			}
			catch (Exception e) {
				System.err.println("System error!");
				try {
					Thread.sleep(3);
				} catch (InterruptedException ie) {}
				sc.nextLine();
				continue;
			}
			
			if (value < 0) {
				System.err.println("Wrong input Please enter a correct number!");
				try {
					Thread.sleep(3);
				} catch (InterruptedException ie) {}
				continue;
			}
			validInput = 1;
		} while (validInput == 0);
		
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
		String title = "";
		String description = "";
		String creatorId = username;
		double proposedPrice = 0;
		
		int validInput = 0;
		do {
			System.out.println("Enter details of the sale below: ");
			System.out.print("Name :");
			title = sc.nextLine();
			if (title.isBlank()) { 
				System.err.println("Name can not be blank!");
				try {
					Thread.sleep(3);
				} catch (InterruptedException ie) {}
				continue;
			}
			
			System.out.print("Description: ");
			description = sc.nextLine();
			if (description.isBlank()) {
				System.err.println("Description can not be blank!");
				try {
					Thread.sleep(3);
				} catch (InterruptedException ie) {}
				continue;
			}
			
			System.out.print("Proposed Price: $");
			try {
				proposedPrice = sc.nextDouble();
			} catch (InputMismatchException ime) {
				System.err.println("Wrong input! Please enter a correct number!");
				try {
					Thread.sleep(3);
				} catch (InterruptedException ie) {}
				sc.nextLine();
				continue;
			}
			catch (NoSuchElementException nee) {
				System.err.println("Wrong input! Please enter a number!");
				try {
					Thread.sleep(3);
				} catch (InterruptedException ie) {}
				sc.nextLine();
				continue;
			}
			catch (IllegalStateException ise) {
				System.err.println("System error! Please try again!");
				try {
					Thread.sleep(3);
				} catch (InterruptedException ie) {}
				sc.nextLine();
				continue;
			}
			catch (Exception e) {
				System.err.println("System error!");
				try {
					Thread.sleep(3);
				} catch (InterruptedException ie) {}
				sc.nextLine();
				continue;
			}
			
			validInput = 1;
		} while (validInput == 0);
		
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
		String title = "";
		String description = "";
		String creatorId = username;
		double askingPrice = 0.0;
		double minimumRaise = 0.0;
		
		int validInput = 0;
		do {
			System.out.println("Enter details of the sale below: ");
			System.out.print("Name :");
			title = sc.nextLine();
			if (title.isBlank()) { 
				System.err.println("Name can not be blank!");
				try {
					Thread.sleep(3);
				} catch (InterruptedException ie) {}
				continue;
			}
			
			System.out.print("Description: ");
			description = sc.nextLine();
			if (description.isBlank()) {
				System.err.println("Description can not be blank!");
				try {
					Thread.sleep(3);
				} catch (InterruptedException ie) {}
				continue;
			}
			
			System.out.print("Asking Price: $");
			try {
				askingPrice = sc.nextDouble();
			} catch (InputMismatchException ime) {
				System.err.println("Wrong input! Please enter a correct number!");
				try {
					Thread.sleep(3);
				} catch (InterruptedException ie) {}
				sc.nextLine();
				continue;
			}
			catch (NoSuchElementException nee) {
				System.err.println("Wrong input! Please enter a number!");
				try {
					Thread.sleep(3);
				} catch (InterruptedException ie) {}
				sc.nextLine();
				continue;
			}
			catch (IllegalStateException ise) {
				System.err.println("System error! Please try again!");
				try {
					Thread.sleep(3);
				} catch (InterruptedException ie) {}
				sc.nextLine();
				continue;
			}
			catch (Exception e) {
				System.err.println("System error!");
				try {
					Thread.sleep(3);
				} catch (InterruptedException ie) {}
				sc.nextLine();
				continue;
			}
			
			System.out.print("MinimumRaise: $");
			try {
				minimumRaise = sc.nextDouble();
			} catch (InputMismatchException ime) {
				System.err.println("Wrong input! Please enter a correct number!");
				try {
					Thread.sleep(3);
				} catch (InterruptedException ie) {}
				sc.nextLine();
				continue;
			}
			catch (NoSuchElementException nee) {
				System.err.println("Wrong input! Please enter a number!");
				try {
					Thread.sleep(3);
				} catch (InterruptedException ie) {}
				sc.nextLine();
				continue;
			}
			catch (IllegalStateException ise) {
				System.err.println("System error! Please try again!");
				try {
					Thread.sleep(3);
				} catch (InterruptedException ie) {}
				sc.nextLine();
				continue;
			}
			catch (Exception e) {
				System.err.println("System error!");
				try {
					Thread.sleep(3);
				} catch (InterruptedException ie) {}
				sc.nextLine();
				continue;
			}
			
			validInput = 1;
		} while (validInput == 0);
		
		String[] postInfos = {id, title, description, creatorId};
		try {
			Sale sale = new Sale(postInfos, askingPrice, minimumRaise);
			sales.add(sale);
		} catch (Exception e) {
			System.err.println("Failed! Your sale has not been created");
			try {
				Thread.sleep(3);
			} catch (InterruptedException ie) {}
		}
		System.out.println("Success! Your sale has been created with id " + id);
	}
	
	private static void newEventPost(Scanner sc, String username, ArrayList<Event> events) {
		String id = Event.generateId(events);
		String title = "";
		String description = "";
		String creatorId = username;
		String venue ="";
		String date = "";
		int capacity = 0;
		
		int validInput = 0;
		do {
			System.out.println("Enter details of the event below: ");
			System.out.print("Name :");
			title = sc.nextLine();
			if (title.isBlank()) { 
				System.err.println("Name can not be blank!");
				try {
					Thread.sleep(3);
				} catch (InterruptedException ie) {}
				continue;
			}
			
			System.out.print("Description: ");
			description = sc.nextLine();
			if (description.isBlank()) {
				System.err.println("Description can not be blank!");
				try {
					Thread.sleep(3);
				} catch (InterruptedException ie) {}
				continue;
			}
			
			System.out.print("Venue: ");
			venue = sc.nextLine();
			if (venue.isBlank()) {
				System.err.println("Venue can not be blank!");
				try {
					Thread.sleep(3);
				} catch (InterruptedException ie) {}
				continue;
			}
			
			System.out.print("Date: ");
			date = sc.nextLine();
			if (!date.matches("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)(?:0?2|(?:Feb))\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$")) {
			    System.err.println("Wrong date time!");
			    try {
					Thread.sleep(3);
				} catch (InterruptedException ie) {}
			    continue;
			}
			
			System.out.print("Capacity: ");
			try {
				capacity = sc.nextInt();
			} catch (InputMismatchException ime) {
				System.err.println("Wrong input! Please enter a correct number!");
				try {
					Thread.sleep(3);
				} catch (InterruptedException ie) {}
				sc.nextLine();
				continue;
			}
			catch (NoSuchElementException nee) {
				System.err.println("Wrong input! Please enter a number!");
				try {
					Thread.sleep(3);
				} catch (InterruptedException ie) {}
				sc.nextLine();
				continue;
			}
			catch (IllegalStateException ise) {
				System.err.println("System error! Please try again!");
				try {
					Thread.sleep(3);
				} catch (InterruptedException ie) {}
				sc.nextLine();
				continue;
			}
			catch (Exception e) {
				System.err.println("System error!");
				try {
					Thread.sleep(3);
				} catch (InterruptedException ie) {}
				sc.nextLine();
				continue;
			}
			
			validInput = 1;
		} while (validInput == 0);
		
		String[] postInfos = {id, title, description, creatorId};
		try {
			Event event = new Event(postInfos, venue, date, capacity);
			events.add(event);
		} catch (Exception e) {
			System.err.println("Failed! Your event has not been created");
			try {
				Thread.sleep(3);
			} catch (InterruptedException ie) {}
		}
		System.out.println("Success! Your event has been created with id " + id);
	}
}
