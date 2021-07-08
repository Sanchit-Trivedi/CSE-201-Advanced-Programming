import java.util.*;
import java.util.Scanner;

// Name    : Sanchit Trivedi
// Roll No : 2018091
// Branch  : CSE
// Advanced Programming LAB 2

public class ArchLegends {
	private static HashMap<String,User> users = new HashMap<>() ;
	public static Scanner in = new Scanner(System.in);
	public static void main(String[] args) {
		int exitFlag = 0;
		while (exitFlag == 0) {
			System.out.println("Welcome to ArchLegends");
			System.out.println("Choose your option\r\n" + 
					"1) New User\r\n" + 
					"2) Existing User\r\n" + 
					"3) Exit");
			int choice = in.nextInt();
			switch (choice) {
			case 1 : 
				System.out.println("Enter Username");
				String name = in.next(); 
				User cur = new User(name);
				users.put(name,cur);
				System.out.println("Choose a Hero\r\n" + 
						"1) Warrior\r\n" + 
						"2) Thief\r\n" + 
						"3) Mage\r\n" + 
						"4) Healer");
				int heroChoice = in.nextInt();
				cur.chooseHero(heroChoice);
				System.out.println("User Creation done. Username: " + cur.getUsername() + " Hero type:" + cur.getHero().getType() +". Log in to play the game . Exiting");
				break;
			case 2:
				System.out.println("Enter username");
				String n = in.next();
				User myUser = users.get(n);
				if (myUser == null) {
					System.out.println("User not Found. Enter a valid input");
					continue;
				}
				else {
					System.out.println("User Found ... Logging In");
					System.out.println("Welcome "+myUser.getUsername());
				}
				Game myGame = new Game(myUser);
				while (myUser.getHero().getHp() > 0) {
					myGame.setMonster(in);
					if (myUser.getCurrentMonster() == null) {
						break;
					}
					myGame.startFight(in);
				}
				break;
			case 3:
				exitFlag = 1;
				System.out.println("Quitting");
				break;
			default:
				System.out.println("Enter a valid input");
				break;
			}
		}
		in.close();
	}
}


class User{
	private final String username;
	private Hero hero;
	private Monster monster;
	public User(String name) {
		this.username = name;
		this.monster = null;
	}
	
	public void setCurrentMonster(Monster m) {
		this.monster = m;
	}
	
	public Monster getCurrentMonster() {
		return this.monster;
	}
	
	public Hero getHero() {
		return hero;
	}

	public void setHero(Hero hero) {
		this.hero = hero;
	}

	public String getUsername() {
		return username;
	}

	public void chooseHero(int m) {
		switch (m) {
		case 1:
			hero = new Warrior();
			break;
		case 2:
			hero = new Thief();
			break;
		case 3:
			hero = new Mage();
			break;
		case 4:
			hero = new Healer();
			break;
		}
	}
	
}