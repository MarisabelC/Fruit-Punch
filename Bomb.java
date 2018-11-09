import java.awt.Image;

public class Bomb extends FruitType_Bomb {

	private Image explosion;
	private boolean exploded;
	private int scale;

	public Bomb() {
		super("bomb");
		scale = 200;
		explosion = loadExplosion(scale, "explosion.png");
	}

	/**
	 * Increment the image of the explosion
	 */
	public void incrementScale() {
		if (scale < WIDTH - 200) {
			scale += 100;
			explosion = loadExplosion(scale, "explosion.png");
		}
	}

	/**
	 * 
	 * @return image of the explosion
	 */
	public Image getExplosion() {
		return explosion;
	}

	/**
	 * 
	 * @return true if the bomb was exploded otherwise false
	 */
	public boolean isExploded() {
		return exploded;
	}

	/**
	 * Set the explosion true or false
	 * @param flag
	 */
	public void setExplosion(boolean flag) {
		exploded = flag;
	}
}