import java.util.ArrayList;
import java.util.List;

public class FruitPunch {

	private List<FruitType_Bomb> fruit;

	public FruitPunch() {
		init();
	}

	/**
	 * Create a list of fruit and bomb 
	 */
	private void init() {

		fruit = new ArrayList<FruitType_Bomb>();
		for (int i = 0; i < 2; i++) {
			fruit.add(new Apple());
			fruit.add(new Orange());
			fruit.add(new Bomb());
			fruit.add(new Watermelon());
			fruit.add(new Bomb());
			fruit.add(new Kiwi());
			fruit.add(new Banana());
		}
	}

	/**
	 * 
	 * @return a random fruit or bomb
	 */
	public FruitType_Bomb getNextItem() {
		int index = (int) (Math.random() * fruit.size());
		return fruit.get(index);
	}

}
