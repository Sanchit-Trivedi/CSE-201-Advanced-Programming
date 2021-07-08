abstract class Tile {
	protected int positions;

	public Tile(int n) {
		positions = n;
	}

	public int getPositions() {
		return positions;
	}

	public void setPositions(int n) {
	}

	public abstract void shake() throws Exception;

}

class Snake extends Tile {

	public Snake(int n) {
		super(n);
	}

	@Override
	public void shake() throws SnakeBiteException {
		System.out.println(">>\t Hiss...! I am a Snake, you go back " + this.getPositions() + " tiles!");
		throw new SnakeBiteException();
	}

}

class Vulture extends Tile {

	public Vulture(int n) {
		super(n);
	}

	@Override
	public void shake() throws VultureBiteException {
		System.out.println(">>\t Yapping...! I am a Vulture, you go back " + this.getPositions() + " tiles!");
		throw new VultureBiteException();
	}
}

class Cricket extends Tile {

	public Cricket(int n) {
		super(n);

	}

	@Override
	public void shake() throws CricketBiteException {
		System.out.println(">>\t Chirp...! I am a Cricket, you go back " + this.getPositions() + " tiles!");
		throw new CricketBiteException();
	}

}

class Trampoline extends Tile {

	public Trampoline(int n) {
		super(n);

	}

	@Override
	public void shake() throws TrampolineException {
		System.out.println(">>\t PingPong! I am a Trampoline, you advance " + this.getPositions() + " tiles");
		throw new TrampolineException();
	}

}

class White extends Tile {

	public White() {
		super(0);
	}

	@Override
	public void shake() throws WhiteTileException {
		System.out.println(">>\t I am a White tile!");
		throw new WhiteTileException();
	}
}

class SnakeBiteException extends Exception {

}

class VultureBiteException extends Exception {

}

class CricketBiteException extends Exception {

}

class TrampolineException extends Exception {

}

class WhiteTileException extends Exception {

}