import java.awt.Image;

public class Watermelon extends FruitType_Bomb {

	private Image smashFruit;

	public Watermelon() {
		super("watermelon");
		smashFruit = initFruitSmash(100, "red-splash.png");
	}

	public Image getFruitSmash() {
		count++;
		return smashFruit;
	}
}
