import java.util.*;

public class User {

	public static Scanner in = new Scanner(System.in);

	public static void main(String[] args) {
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
				+ ">> Good News: Each Trampoline can help you advance by " + posArr.get(3) + " number of Tiles \r\n"
				+ ">> Enter the Player Name");
		String name = in.next();
		myComp.setUserName(name);
		System.out.println(">> Starting the game with " + name + " at Tile-1\r\n"
				+ ">> Control transferred to Computer for rolling the Dice for Josh\r\n"
				+ ">> Hit enter to start the game");
		in.nextLine();
		System.out.println(">> Game Started ======================>");
		try {
			myComp.startGame();
		} catch (GameWinnerException gwe) {
			myComp.gameWon();
		} catch (StackOverflowError soe) {
			System.out.println("Sorry cannot complete the game with the generated map . Please restart the program");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}                                                                                                                                                     
}

class InvalidInputException extends Exception {

}
