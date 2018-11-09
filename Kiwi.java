import java.awt.Image;

public class Kiwi extends FruitType_Bomb {

	private Image smashFruit;

	public Kiwi() {
		super("kiwi");
		smashFruit = initFruitSmash(100, "green-splash.png");
	}

	public Image getFruitSmash() {
		count++;
		return smashFruit;
	}
}
