import java.awt.Image;

public class Banana extends FruitType_Bomb {

	private Image smashFruit;

	public Banana() {
		super("banana");
		smashFruit = initFruitSmash(100, "yellow-splash.png");
	}

	public Image getFruitSmash() {
		count++;
		return smashFruit;
	}
}
