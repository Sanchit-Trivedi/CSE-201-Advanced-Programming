import java.util.*;

class Computer {

	private ArrayList<Tile> map;
	private Map myMap;
	private final int length;
	private int mobTotal;
	private ArrayList<Integer> mobCounter;
	private ArrayList<Integer> posData;
	private Dice myDice;
	private boolean onStart;
	private String username;
	private int curPos = 0;
	private int startPos = 0;
	private int endPos;
	private int snakeBites = 0;
	private int vultureBites = 0;
	private int cricketBites = 0;
	private int trampolineJumps = 0;
	private int rollCounter = 0;

	public Computer(int length) {
		this.length = length;
		Random rand = new Random();
		mobTotal = (int) ((((double) (rand.nextInt(31) + 50)) / 100) * length);
		myMap = new Map(this.length, this.mobTotal);
		mobCounter = myMap.getMobCounter();
		myDice = new Dice();
		posData = myMap.getPosData();
		onStart = true;
		endPos = length - 1;
		map = myMap.getMap();
	}

	public void setUserName(String user) {
		username = user;
	}

	public ArrayList<Integer> getMobCounter() {
		return mobCounter;
	}

	public ArrayList<Integer> getPosData() {
		return posData;
	}

	public void showMessage(int n) {
		System.out.print(">>[Roll-" + rollCounter + "]:" + username + " rolled a " + n + ".");
	}

	public void gameWon() {
		System.out.println(">> " + "\t " + username + " wins the race in " + rollCounter + " rolls!\r\n"
				+ ">>\t Total Snake Bites = " + snakeBites + "\r\n" + ">>\t Total Vulture Bites = " + vultureBites
				+ "\r\n" + ">>\t Total Cricket Bites = " + cricketBites + "\r\n" + ">>\t Total Trampolines = "
				+ trampolineJumps);
		System.exit(0);
	}

	public void decrementCurrentPosition(int z) {
		curPos -= z;
		int flag = 0;
		if (curPos < 0) {
			flag = 1;
			curPos = startPos;
			onStart = true;
		}
		System.out.print(">>\t " + username + " moved to Tile - " + (this.curPos + 1));
		if (flag == 1) {
			System.out.println(" as it can't go back further");
			try {
				this.startGame();
			} catch (GameWinnerException gwe) {
				this.gameWon();
			} catch (StackOverflowError soe) {
				System.out
						.println("Sorry cannot complete the game with the generated map . Please restart the program");
				System.exit(0);
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println();
		}
	}

	public void incrementCurrentPosition(int z) {
		if (curPos + z > endPos) {
			System.out.println(">>\t No space to jump ahead");
		} else {
			curPos += z;
			System.out.println(">>\t " + username + " moved to Tile - " + (this.curPos + 1));
		}
	}

	public void startGame() throws GameWinnerException {
		while (onStart) {
			rollCounter++;
			int n = myDice.roll();
			showMessage(n);
			if (n == 6) {
				onStart = false;
				System.out.println("You are out of the cage! You get a free roll");
			} else
				System.out.println("OOPs you need 6 to start");
		}
		while (true) {
			if (curPos == endPos) {
				throw new GameWinnerException();
			}
			rollCounter++;
			int n = myDice.roll();
			showMessage(n);
			if (curPos + n > endPos) {
				System.out.println("Landed on Tile " + ((curPos + 1)));
				continue;
			}
			curPos += n;
			System.out.println("Landed on Tile " + (curPos + 1));
			if (curPos == endPos) {
				throw new GameWinnerException();
			} else {
				try {
					Tile curTile = map.get(curPos);
					curTile.shake();
				} catch (SnakeBiteException sb) {
					snakeBites++;
					decrementCurrentPosition(posData.get(0));
				} catch (VultureBiteException vb) {
					vultureBites++;
					decrementCurrentPosition(posData.get(1));
				} catch (CricketBiteException cb) {
					cricketBites++;
					decrementCurrentPosition(posData.get(2));
				} catch (TrampolineException te) {
					trampolineJumps++;
					incrementCurrentPosition(posData.get(3));
				} catch (WhiteTileException wte) {
					incrementCurrentPosition(0);
				} catch (Exception e) {
//					System.out.println(e.getMessage());
					System.out.println("Some error occured");
				}
			}

		}
	}
}

class GameWinnerException extends Exception {

}

class Map {

	private ArrayList<Integer> mobCounter;
	private ArrayList<Tile> map;
	private ArrayList<Integer> posData;
	private int length, mobTotal;

	public Map(int length, int mobTotal) {
		this.length = length;
		this.mobTotal = mobTotal;
		map = new ArrayList<>();
		mobCounter = new ArrayList<>();
		posData = new ArrayList<>();
		this.n_Random();
		this.mapGenerator();
	}

	public void n_Random() {
		Random r = new Random();
		int temp = 0;
		int sum = 0;
		for (int i = 1; i <= 4; i++) {
			if (i != 4) {
				temp = r.nextInt((mobTotal - 1 - sum) / (4 - i)) + 1;
				mobCounter.add(temp);
				sum += temp;
			} else {
				int last = (mobTotal - sum);
				mobCounter.add(last);
				sum += last;
			}
		}
	}

	public void mapGenerator() {
		mobCounter.add(length - mobTotal);
		int counter = 1;
		Random r = new Random();
		for (Integer i : mobCounter) {
			int n = r.nextInt(10) + 1;
			if (counter < 5)
				posData.add(n);
			else
				posData.add(0);
			while (i-- > 0) {
				switch (counter) {
				case 1:
					map.add(new Snake(n));
					break;
				case 2:
					map.add(new Vulture(n));
					break;
				case 3:
					map.add(new Cricket(n));
					break;
				case 4:
					map.add(new Trampoline(n));
					break;
				case 5:
					map.add(new White());
					break;
				}
			}
			counter++;
		}
		Collections.shuffle(map);
	}

	public ArrayList<Integer> getMobCounter() {
		return mobCounter;
	}

	public ArrayList<Tile> getMap() {
		return map;
	}

	public ArrayList<Integer> getPosData() {
		return posData;
	}

	public int getLength() {
		return length;
	}

	public int getMobTotal() {
		return mobTotal;
	}
}

class Dice {

	private Random rand;

	public Dice() {
		rand = new Random();
	}

	public int roll() {
		return 1 + rand.nextInt(6);
	}
}