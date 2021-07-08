import java.util.Comparator;
public class SideKick {
	protected double hp;
	protected int xp;
	protected int basecost;
	protected double attackPower;
	protected double maxHp;
	protected String type;

	
	protected SideKick( int paidXp, int basecost , int basePower , String Type) {
		this.hp = 100;
		this.xp = 0;
		this.maxHp = 100;
		this.basecost = basecost;
		this.attackPower = (paidXp - basecost) * 0.5 + basePower;
		this.type = Type;
	}
	
	protected double attack() {
		return this.attackPower;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o != null && getClass() == o.getClass()) {
			SideKick sk = (SideKick) o;
			return this.xp == sk.xp;
		}
		else
			return false;
	}

	@Override
	public String toString() {
		return "You bought a sidekick: "+this.type+"\r\n" + 
				"XP of sidekick is " +this.xp + "\r\n" + 
				"Attack of sidekick is " + this.attackPower;
	}
	
	@Override
	protected Object clone() {
		return this;
	}

	protected double getHp() {
		return hp;
	}

	protected int getXp() {
		return xp;
	}

	protected double getAttackPower() {
		return attackPower;
	}

	protected double getMaxHp() {
		return maxHp;
	}

	protected String getType() {
		return type;
	}

	protected void setHp(double hp) {
		this.hp = hp;
	}
	
	protected void addXp(int n) {
		this.xp += n*2;
		System.out.println(n*2 + " XP awarded to SideKick ");
	}
}

class SortByXp implements Comparator<SideKick>{

	public int compare(SideKick one , SideKick two) {
		return one.xp - two.xp;
	}
	
	
}

class Minion extends SideKick{
	
	private boolean isCloned;
	private boolean hasBeenCloned;
	
	public Minion(int paidXp) {
		super(paidXp,5,1,"Minion");
		this.hasBeenCloned = false;
	}
	
	@Override
	public Object clone() {
		this.isCloned = true;
		return this;
	}
	
	@Override
	public double attack() {
		if (this.isCloned) 
			return 4*super.attack();
		else
			return super.attack();
	}
	
	public boolean getHasBeenCloned() {
		return this.hasBeenCloned;
	}
	
	public void setHasBeenCloned(boolean x) {
		this.hasBeenCloned = x;
	}
	
	public boolean getCloned() {
		return this.isCloned;
	}
	
	public void setCloned(boolean z) {
		this.isCloned = z;
	}
}

class Knight extends SideKick{

	public Knight(int paidXp) {
		super(paidXp,8,2,"Knight");
	}
	
}
