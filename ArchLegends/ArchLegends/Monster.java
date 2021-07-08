import java.util.Random;

public class Monster {
	protected double hp;
	protected double totalHp;
	protected int attackPower;
	protected final int level;
	protected final String monsterType;
	
	
	public double getTotalHp() {
		return totalHp;
	}

	public double getHp() {
		if (hp < 0) 
			hp = 0 ;
		return hp;
	}

	public void setHp(double hp) {
		this.hp = hp;
	}

	public int getLevel() {
		return level;
	}

	public String getMonsterType() {
		return monsterType;
	}

	public Monster(int health,int lvl,String name) {
		this.hp = health;
		this.totalHp = this.hp;
		this.level = lvl;
		this.monsterType = name;
	}
	
	public int attack() {
		Random randi = new Random();
		while (true) {
			this.attackPower = (int)Math.round((double)(randi.nextGaussian()) + (this.hp/8));
			int ul = (int)(0.25 * this.hp);
			if (this.attackPower >= 0 && this.attackPower < ul) {
				  break;
			}
		}
//		System.out.println("ATTACK POWER : " + this.attackPower);
		return this.attackPower;
	}
}

class Goblins extends Monster{
	public Goblins() {
		super(100,1,"Goblin");
	}
}
class Zombies extends Monster{
	public Zombies() {
		super(150,2,"Zombie");
	}
}
class Fiends extends Monster{
	public Fiends() {
		super(200,3,"Fiend");
	}
}
class LionFang extends Monster{
	public LionFang() {
		super(250,4,"Boss");
	}
	
	@Override
	public int attack() {
		Random rand = new Random();
		int choice = rand.nextInt(10);
		if (choice == 5) 
			return -1;
		else
			return super.attack();		
	}
}
