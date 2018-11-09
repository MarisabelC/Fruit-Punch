import java.awt.Image;

public class Apple extends FruitType_Bomb {

	private Image smashFruit;

	public Apple() {
		super("apple");
		smashFruit = initFruitSmash(100, "red-splash.png");
	}

	public Image getFruitSmash() {
		count++;
		return smashFruit;
	}

}