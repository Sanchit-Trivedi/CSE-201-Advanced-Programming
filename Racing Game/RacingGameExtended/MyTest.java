
import org.junit.Test;

public class MyTest {
	@Test(expected = SnakeBiteException.class)
	public void testSnake() throws SnakeBiteException {
		Snake s = new Snake(5);
		s.shake();
	}
	
	@Test(expected = CricketBiteException.class)
	public void testCricket() throws CricketBiteException {
		Cricket c = new Cricket(4);
		c.shake();
	}
	
	@Test(expected = VultureBiteException.class)
	public void testVulture() throws VultureBiteException {
		Vulture v = new Vulture(3);
		v.shake();
	}
	
	@Test(expected = TrampolineException.class)
	public void testTrampoline() throws TrampolineException {
		Trampoline t = new Trampoline(2);
		t.shake();
	}
	
	@Test(expected = GameWinnerException.class)
	public void testWinning() throws GameWinnerException {
		User.gameWon();
	}
}

