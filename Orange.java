import java.awt.Image;

public  class Orange extends FruitType_Bomb {

		private Image smashFruit;

		public Orange() {
			super("orange");
			smashFruit = initFruitSmash(100, "orange-splash.png");
		}

		public Image getFruitSmash() {
			smash = true;
			count++;
			return smashFruit;
		}

	}
