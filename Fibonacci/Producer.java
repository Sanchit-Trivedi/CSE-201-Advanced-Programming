import java.util.*;
public class Producer {
	
	public static Scanner sc = new Scanner(System.in);
	private static int numberOfThreads;
	public volatile static Queue<Integer> inputs = new LinkedList<Integer>();
	public volatile static Queue<Integer> outputs = new LinkedList<Integer>();
	public volatile static Queue<Long> time = new LinkedList<Long>();
	
	private static ArrayList<Thread> threads;
	
	private Object lock = new Object();
	
	public static void displayResults() {
		while(!outputs.isEmpty()){
			System.out.println(outputs.poll());
			System.out.println("Time to execute : " + time.poll());
		}
	}
	
	// Facade
	public static void handleChoice(String choice) {
		choice = choice.toLowerCase();
		switch(choice) {
		case "yes":
			System.out.println("Enter the Number");
			Integer z = sc.nextInt() - 1;
			inputs.add(z);
			System.out.println();
			break;
		case "no" :
			System.out.println("Displaying remaining results and terminating");
			displayResults();
			System.exit(0);
			break;
		case "display":
			System.out.println("Displaying previous results");
			displayResults();
			break;
		default : 
			System.out.println("Please enter a valid input");
			break;
		}
	}
	
	public static void main(String[] args) {
		
		System.out.println("Input the number of threads to generate"); 
		numberOfThreads = sc.nextInt();
		threads = new ArrayList<Thread>();
		
		for (int i = 0; i<numberOfThreads ;i++) {
			Thread t = new Thread(new Consumer());
			threads.add(t);
			t.start();
		}
		
		while (true) {
			System.out.println("Do you want to enter a number YES or NO");
			System.out.println("Enter 'display' when you want to display the previous queries");
			String ch = sc.next();
			handleChoice(ch);
		}
		
	}
}
