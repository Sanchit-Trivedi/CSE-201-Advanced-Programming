import java.util.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Game {
	private ArrayList<Integer> path;
	private ArrayList<Integer> locations;
	private User user;
	public Game(User myUser){
		user = myUser;
		path = new ArrayList<>();
		locations = new ArrayList<Integer>();
		path.add(-1);
		locations.add(0);
	}
	
	public int[] getMonsterOnLocation() {
		Random rand = new Random();
		rand.setSeed(0);
		int [] arr = new int[3];
		int Limit = 3;
		if (path.size() >= 4) 
			Limit = 4;
		arr[0] = rand.nextInt(Limit);
		arr[1] = rand.nextInt(Limit);
		arr[2] = rand.nextInt(Limit);
		return arr;
	}
	
	public void getHint(int[] loc, int[] temparr) {
		int index = -1;
		List locList = Arrays.asList(loc);
		if (path.size() > 3) {
			index  = locList.indexOf(3);
		}
		if (index ==-1) {
			index = locList.indexOf(Collections.min(locList));
		}
		System.out.println("Suggested location "+ temparr[index]);
	}
	
	public void setMonster(Scanner in) {
		int[] loc = getMonsterOnLocation();
		Random rand = new Random();
		if (path.size() == 1) 
			System.out.println("You are at the starting location. Choose path:" );
		else {
			System.out.println("Fight won proceed to the next location.");
			System.out.println("You are at location "+ locations.get(locations.size()-1)+ " Choose path:");
		}
		int [] temparr = new int[3];
		for (int i = 0; i < loc.length; i++) {
			int temp = loc[i] + rand.nextInt(15);
			System.out.println(i+1 + ") Go to location " + temp);
			temparr[i] = temp; 
		}
		if (path.size() > 1) {
			System.out.println("4) Go back");
		}
		System.out.println("Do you want a hint? Enter Y or N");
		System.out.println("Enter -1 to exit");
		String hint = in.next();
		if (hint.equalsIgnoreCase("Y")){
			this.getHint(loc, temparr);
		}
		int choice = in.nextInt();
		choice--;
		if (choice == -2) {
			user.setCurrentMonster(null);
			System.out.println("Successfully exited");
		}
		else if(choice == 3) {
			locations.remove(locations.size()-1);
			System.out.println("Going back");
			setMonster(in);
		}
		else if(loc[choice] == 0) {
			System.out.println("Moving to location " + temparr[choice]);
			locations.add(temparr[choice]);
			path.add(0);
			user.setCurrentMonster(new Goblins());
			}
		else if(loc[choice] == 1) {
			locations.add(temparr[choice]);
			path.add(1);
			System.out.println("Moving to location " + temparr[choice]);
			user.setCurrentMonster(new Zombies());
			}
		else if(loc[choice] == 2) {
			System.out.println("Moving to location " + temparr[choice]);
			locations.add(temparr[choice]);
			path.add(2);
			user.setCurrentMonster(new Fiends());
			}
		else if (loc[choice]==3) {
			path.add(3);
			System.out.println("Moving to location " + temparr[choice]);
			locations.add(temparr[choice]);
			user.setCurrentMonster(new LionFang());
		}
	}
	
	public void startFight(Scanner in) {
		System.out.println("------Starting Fight-----");
		Monster myMonster = user.getCurrentMonster();
		Hero myHero = user.getHero();
//		Scanner in = new Scanner(System.in);
		System.out.println("Fight Started. You are fighting a level "+ myMonster.getLevel() + " Monster");
		while (myHero.getHp() > 0 && myMonster.getHp() > 0) {
			System.out.println("Choose move:\r\n" + "1) Attack\r\n" + "2) Defense");
			if (myHero.checkForSpecialPower()) {
				System.out.println("3) Special Power");
			}
			int action = in.nextInt();
			switch(action) {
			case 1:
				System.out.println("You choose to attack.");
				int damage = myHero.attack();
				myMonster.setHp(myMonster.getHp()-damage);
				if (myHero.getType().equals("Mage") && myHero.getSpecialPowerStatus()) {
					int reduce = (int)(0.05 * myMonster.getHp());
					myMonster.setHp(myMonster.getHp()-reduce);
					System.out.println("Extra "+reduce+"HP deducted due to MAGE's special power");
				}
				else if (myHero.getType().equals("Thief") && myHero.getSpecialPowerStatus()) {
					myHero.setHp(myHero.getHp() + (int)(0.30 * myMonster.getHp()));
					System.out.println("Stole "+(int)(0.30 * myMonster.getHp()) + " from the Monster");
					myMonster.setHp((int)(0.70 * myMonster.getHp()));
					myHero.deactivatePower();
				}
				System.out.println("You attacked and inflicted " + damage + " damage to the monster.");
				System.out.println("Your Hp : " + myHero.getHp() + "/" + myHero.getTotalhp() + " Monsters Hp : " + myMonster.getHp() + "/" + myMonster.getTotalHp());
				
				if (myMonster.getHp() == 0) 
					continue;
				int mDamage = -1;
				System.out.println("Monster Attack!");
				if (myMonster.getMonsterType().equals("Boss")) {
					Random rand = new Random();
					int choice = rand.nextInt(10);
					if (choice == 5) 
						mDamage = myHero.getHp()/2;
				}
				if (mDamage == -1)
					mDamage = myMonster.attack();		
				System.out.println("The monster attacked and inflicted " +mDamage+" damage to you.");
				myHero.setHp(myHero.getHp()-mDamage);
				System.out.println("Your Hp : " + myHero.getHp() + "/" + myHero.getTotalhp() + " Monsters Hp : " + myMonster.getHp() + "/" + myMonster.getTotalHp());
				break;
			case 2:
				System.out.println("You choose to Defend");
				int defval = myHero.defend();
				if (myHero.getType().equals("Mage") && myHero.getSpecialPowerStatus()) {
					int reduce = (int)(0.05 * myMonster.getHp());
					myMonster.setHp(myMonster.getHp()-reduce);
					System.out.println("Extra "+reduce+"HP deducted due to MAGE's special power");
				}
				else if (myHero.getType().equals("Thief") && myHero.getSpecialPowerStatus()) {
					myHero.setHp(myHero.getHp() + (int)(0.30 * myMonster.getHp()));
					System.out.println("Stole "+(int)(0.30 * myMonster.getHp()) + " from the Monster");
					myMonster.setHp((int)(0.70 * myMonster.getHp()));
					myHero.deactivatePower();
				}
				System.out.println("You shall receive " + defval + "less damage in the next attack");
				System.out.println("Your Hp : " + myHero.getHp() + "/" + myHero.getTotalhp() + " Monsters Hp : " + myMonster.getHp() + "/" + myMonster.getTotalHp());
				int mdamage = -1;
				System.out.println("Monster Attack!");
				if (myMonster.getMonsterType().equals("Boss")) {
					Random rand = new Random();
					int choice = rand.nextInt(10);
					if (choice == 5) 
						mdamage = myHero.getHp()/2;
				}
				if (mdamage == -1)
					mdamage = myMonster.attack();		
				mdamage -= defval; 
				System.out.println("The monster attacked and inflicted " +mdamage+" damage to you.");
				myHero.setHp(myHero.getHp()-mdamage);
				System.out.println("Your Hp : " + myHero.getHp() + "/" + myHero.getTotalhp() + " Monsters Hp : " + myMonster.getHp() + "/" + myMonster.getTotalHp());
				break;
			case 3:
				myHero.specialPower();
				System.out.println("Special Power activated ");
				System.out.println("Performing special attack ");
				if (myHero.getType().equals("Mage") && myHero.getSpecialPowerStatus()) {
					int reduce = (int)(0.05 * myMonster.getHp());
					myMonster.setHp(myMonster.getHp()-reduce);
					myHero.incrementMove();
					System.out.println("Extra "+reduce+"HP deducted due to MAGE's special power");
					System.out.println("Your Hp : " + myHero.getHp() + "/" + myHero.getTotalhp() + " Monsters Hp : " + myMonster.getHp() + "/" + myMonster.getTotalHp());
				}
				else if (myHero.getType().equals("Thief") && myHero.getSpecialPowerStatus()) {
					System.out.println("Stole "+(int)(0.30 * myMonster.getHp()) + " from the Monster");
					myHero.setHp(myHero.getHp() + (int)(0.30 * myMonster.getHp()));
					myMonster.setHp((int)(0.70 * myMonster.getHp()));
					myHero.deactivatePower();
					myHero.incrementMove();
					System.out.println("Your Hp : " + myHero.getHp() + "/" + myHero.getTotalhp() + " Monsters Hp : " + myMonster.getHp() + "/" + myMonster.getTotalHp());
				}
				else if (myHero.getType().equals("Warrior") && myHero.getSpecialPowerStatus()) {
					System.out.println("Attacking with increased Power");
					int bdamage = myHero.attack();
					myMonster.setHp(myMonster.getHp()-bdamage);
					System.out.println("You attacked and inflicted " + bdamage + " damage to the monster.");
					System.out.println("Your Hp : " + myHero.getHp() + "/" + myHero.getTotalhp() + " Monsters Hp : " + myMonster.getHp() + "/" + myMonster.getTotalHp());
				} 
				else if (myHero.getType().equals("Healer") && myHero.getSpecialPowerStatus()) {
					System.out.println("Regenrating health");
					myHero.incrementMove();
					int increase = (int) (0.05 * myHero.getHp());
					myHero.setHp(myHero.getHp() + increase);
					System.out.println("HP increased by "+ increase);
					System.out.println("Your Hp : " + myHero.getHp() + "/" + myHero.getTotalhp() + " Monsters Hp : " + myMonster.getHp() + "/" + myMonster.getTotalHp());
				}
				if (myMonster.getHp() == 0) 
					continue;
				mDamage = -1;
				System.out.println("Monster Attack!");
				if (myMonster.getMonsterType().equals("Boss")) {
					Random rand = new Random();
					int choice = rand.nextInt(10);
					if (choice == 5) 
						mDamage = myHero.getHp()/2;
				}
				if (mDamage == -1)
					mDamage = myMonster.attack();		
				System.out.println("The monster attacked and inflicted " +mDamage+" damage to you.");
				myHero.setHp(myHero.getHp()-mDamage);
				System.out.println("Your Hp : " + myHero.getHp() + "/" + myHero.getTotalhp() + " Monsters Hp : " + myMonster.getHp() + "/" + myMonster.getTotalHp());
				break;
			}
		}
		if (myMonster.getHp()==0) {
			System.out.println("Congrats Monster defeated");
			myHero.addXp(myMonster.getLevel());
		}
		else
			System.out.println("You Lost");
	}
}