public abstract class Hero{
	
	protected int level;
	protected int hp;
	protected int Totalhp; 
	protected int xp;
	protected int specialPowerCounter;
	protected int moves;
	protected boolean specialPowerStatus;
	protected int attackDamage;
	protected int defenseValue;
	protected final String type;
	
	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getTotalhp() {
		return Totalhp;
	}
	
	public int getLevel() {
		return level;
	}

	public int getHp() {
		return hp;
	}

	public int getXp() {
		return xp;
	}

	public int getSpecialPowerCounter() {
		return specialPowerCounter;
	}

	public int getMoves() {
		return moves;
	}

	public int getAttackDamage() {
		return attackDamage;
	}

	public int getDefenseValue() {
		return defenseValue;
	}

	public String getType() {
		return type;
	}

	public Hero(int ad , int dv , String name) {
		this.level = 1;
		this.hp = 100;
		this.Totalhp = this.hp;
		this.xp = 0;
		this.moves = 1;
		this.specialPowerCounter = 0;
		this.attackDamage = ad;
		this.defenseValue = dv;
		this.type = name;
	}
	
	private void levelUpMessage() {
		System.out.println("Level Up : level : " + this.level);
	}
	
	private int checkForLevelUp() {
		if (this.xp >= 60) {
			this.level = 4;
			this.hp = 250;
			this.Totalhp = this.hp;
			this.levelUpMessage();
			return 1;
		}
		else if (this.xp >= 40) {
			this.hp = 200;
			this.Totalhp = this.hp;
			this.level = 3;
			this.levelUpMessage();
			return 1;
		}
		else if (this.xp >=20) {
			this.level = 2;
			this.hp = 150;
			this.Totalhp = this.hp;
			this.levelUpMessage();
			return 1;
		}
		else
			return 0;
	}
	
	protected void addXp(int n) {
		this.xp += n*20;
		System.out.println(n*20 + "XP awarded");
		if (this.checkForLevelUp() == 1){
			this.levelUp();
		}
	}

	protected void deactivatePower() {
		this.specialPowerStatus = false;
	}
	
	protected void levelUp() {
		this.attackDamage ++;
		this.defenseValue ++;
	}
	
	public boolean getSpecialPowerStatus() {
		return this.specialPowerStatus;
	} 
	
	public boolean checkForSpecialPower() {
		return this.moves >3 ;
	}
	
	public void incrementMove() {
		this.moves++;
		this.specialPowerCounter++;
	}
	protected abstract int attack();
	protected abstract int defend();
	protected abstract void specialPower() ;
}

class Warrior extends Hero{

	public Warrior() {
		super(10,3,"Warrior");
	}
	@Override
	protected int attack() {
		if (this.specialPowerStatus)
			this.specialPowerCounter++;
		this.moves++;
		if (this.specialPowerCounter == 4)
			this.deactivatePower();
		return this.attackDamage;
	}
	@Override
	protected int defend() {
		if (this.specialPowerStatus) 
			this.specialPowerCounter++;
		this.moves++;
		if (this.specialPowerCounter == 4)
			this.deactivatePower(); 
		return this.defenseValue;
	}
		
	@Override
	public void specialPower() {
		if (this.moves > 3) {
			this.specialPowerStatus = true;
			this.moves = 0;
			this.attackDamage += 5 ;
			this.defenseValue += 5 ; 
		}
	}
	
	@Override
	public void deactivatePower() {
		super.deactivatePower();
		this.attackDamage -= 5;
		this.attackDamage -= 5;
	}
}

class Mage extends Hero{
	
	public Mage() {
		super(5,5,"Mage");
	}
	@Override
	public int attack() {
		if (this.specialPowerStatus)
			this.specialPowerCounter++;
		this.moves++;
		if (this.specialPowerCounter == 4)
			this.deactivatePower();
		return this.attackDamage;
	}
	@Override
	protected int defend() {
		if (this.specialPowerStatus) 
			this.specialPowerCounter++;
		this.moves++;
		if (this.specialPowerCounter == 4)
			this.deactivatePower(); 
		return this.defenseValue;
	}
	@Override
	protected void specialPower() {
		if (this.moves > 3) {
			this.specialPowerStatus = true;
			this.moves = 0;
		}
	}
}

class Thief extends Hero{
	
	public Thief() {
		super(6,4,"Thief");
	}
	
	@Override
	public int attack() {
		if (this.specialPowerStatus)
			this.specialPowerCounter++;
		this.moves++;
		return this.attackDamage;
	}
	@Override
	protected int defend() {
		if (this.specialPowerStatus) 
			this.specialPowerCounter++;
		this.moves++;
		return this.defenseValue;
	}
	@Override
	protected void specialPower() {
		if (this.moves > 3) {
			this.specialPowerStatus = true;
			this.moves = 0;
		}
	}
}

class Healer extends Hero{
	
	public Healer() {
		super(4,8,"Healer");
	}
	@Override
	public int attack() {
		if (this.specialPowerStatus)
			this.specialPowerCounter++;
		this.moves++;
		if (this.specialPowerCounter == 4)
			this.deactivatePower();
		this.hp = (int) Math.round(1.05*this.hp);
		return this.attackDamage;
	}
	@Override
	protected int defend() {
		if (this.specialPowerStatus) 
			this.specialPowerCounter++;
		this.moves++;
		if (this.specialPowerCounter == 4)
			this.deactivatePower();
		this.hp = (int) Math.round(1.05*this.hp);
		return this.defenseValue;
	}
	@Override
	protected void specialPower() {
		if (this.moves > 3) {
			this.specialPowerStatus = true;
			this.moves = 0;
		}
	}	
}