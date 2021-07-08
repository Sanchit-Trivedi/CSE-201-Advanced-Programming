import java.util.*;
import java.io.*;

public class User {
	
	public static HashMap<String,Computer> Users;
	
	public static void deserialize() 
			throws IOException,ClassNotFoundException {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream("Users.txt"));
			Users = (HashMap<String,Computer>) ois.readObject();
		}
		finally {
			ois.close();
		}
	}
	
	public static void serialize()
			throws IOException{
		ObjectOutputStream outp = null;
		File userFile = new File("Users.txt");
		if (userFile.exists()) {
			userFile.delete();
		}
		try {
			outp = new ObjectOutputStream(new FileOutputStream("Users.txt"));
			outp.writeObject(Users);
		} finally {
			outp.close();
		}
	}

	public static void main(String[] args) 
			throws IOException,ClassNotFoundException{
		File userFile = new File("Users.txt");
		if (userFile.exists()) {
			deserialize();
		}
		else {
			Users = new HashMap<>();
		}
		Scanner in = new Scanner(System.in);
		System.out.println(">> Enter the Player Name");
		String name = in.next();
		if (Users.containsKey(name)) {
			Computer comp = Users.get(name);
			try {
				System.out.println("Resuming Game");
				comp.resumeGame(in);
			} catch (GameWinnerException gwe) {
				Users.remove(comp.getUsername());
				serialize();
				comp.gameWon();
			} catch (StackOverflowError soe) {
				System.out.println("Sorry cannot complete the game with the generated map . Please restart the program");
			} catch (SaveGameException sge) {
				Users.put(name, comp);
				serialize();
				System.out.println("Exiting");
				System.exit(0);
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		int l;
		while (true) {
			System.out.println(">> Enter total number of tiles on the race track (length)");
			try {
				l = in.nextInt();
				if (l >= 10) {
					break;
				} else {
					throw new InvalidInputException();
				}
			} catch (InvalidInputException iie) {
				System.out.println("Please enter an input greater than 10");
			}
		}
		Computer myComp = new Computer(l);
		ArrayList<Integer> noArr = myComp.getMobCounter();
		ArrayList<Integer> posArr = myComp.getPosData();
		System.out.println(">> Setting up the race track...\r\n" + ">> Danger: There are " + noArr.get(0) + ","
				+ noArr.get(1) + "," + noArr.get(2)
				+ " numbers of Snakes, Cricket, and Vultures respectively on your track! // X, Y, Z are\r\n"
				+ ">> Danger: Each Snake, Cricket, and Vultures can throw you back by " + posArr.get(0) + ","
				+ posArr.get(1) + "," + posArr.get(2) + " number of Tiles respectively! \r\n"
				+ ">> Good News: There are " + noArr.get(3) + " number of Trampolines on your track!\r\n"
				+ ">> Good News: Each Trampoline can help you advance by " + posArr.get(3) + " number of Tiles \r\n");
		myComp.setUserName(name);
		System.out.println(">> Starting the game with " + name + " at Tile-1\r\n"
				+ ">> Control transferred to Computer for rolling the Dice for Josh\r\n"
				+ ">> Hit enter to start the game");
		in.nextLine();
		System.out.println(">> Game Started ======================>");
		try {
			myComp.startGame(in);
		} catch (GameWinnerException gwe) {
			Users.remove(myComp.getUsername());
			serialize();
			myComp.gameWon();
		} catch (StackOverflowError soe) {
			System.out.println("Sorry cannot complete the game with the generated map . Please restart the program");
		} catch (SaveGameException sge) {
			Users.put(name, myComp);
			serialize();
			System.out.println("Exiting");
			System.exit(0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		in.close();
	}
	
	public static void gameWon() throws GameWinnerException{
		throw new GameWinnerException();
	}
}

class InvalidInputException extends Exception {

}
