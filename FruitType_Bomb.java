import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class FruitType_Bomb {

	protected final int WIDTH = 1000;
	protected final int HEIGHT = 600;
	private Image fruit;
	private int x;
	private int y;
	private double pos;//how much the fruit needs to move in the x position  
	private int dy = 125;//velocity in the y axis
	private int dx = 10;//velocity in the x axis
	private int dyf;//final velocity of the fruit
	private int dv_y = 78; // acceleration in the y axis
	private int dv_x= 8;// acceleration in the y axis
	private boolean up;
	protected boolean smash;
	private double time;
	protected int count;// how many time the get fruit method has been called
	private String name;
	private boolean move;

	public FruitType_Bomb(String nameFile) {
		initFruit(nameFile + ".png");
		name = nameFile;
	}

	/**
	 * Set the image to the corresponded fruit or bomb 
	 * @param nameFile
	 */
	protected void initFruit(String nameFile) {
		ImageIcon imgIcon = new ImageIcon(nameFile);
		fruit = imgIcon.getImage();
		reset();
	}

	/**
	 * Get a random position for the x axis of the fruit 
	 */
	public void reset() {
		x = (int) ((Math.random() * (WIDTH / 3)) + WIDTH / 4);
		y = HEIGHT;
		up = true;
		smash = false;
		dyf = dy;
		pos = computePosX(time);//Get a random path for the x axis
		count = 0;
	}

	/**
	 * Move the fruit to one position to another if the fruit is not smashed and the time>0
	 * @param time needs to be greater than zero. Time is how fast the fruit is move on the window
	 */
	public void move(double time) {

		this.time = time * 10;
		if (smash == false && time > 0) {
			y -= computePos(time);
			x -= pos;
		}
	}

	/**
	 * Set the movement of the fruit 
	 * @param flag
	 */
	public void setMove(boolean flag) {
		move = flag;
	}

	/**
	 * 
	 * @return true if the fruit move otherwise false
	 */
	public boolean getMove() {
		return move;
	}

	/**
	 * @return the x position of the fruit
	 */
	public int getX() {
		return x;
	}

	/**
	 * 
	 * @return the y position of the fruit
	 */
	public int getY() {
		return y;

	}

	/**
	 * 
	 * @return the image of the fruit
	 */
	public Image getImage() {
		return fruit;
	}

	/**
	 * Set smashed true or false
	 * @param flag
	 */
	public void setSmashFruit(boolean flag) {
		smash = flag;
	}

	/**
	 * 
	 * @return true if the fruit was smashed otherwise false
	 */
	public boolean isSmash() {
		return smash;
	}

	/**
	 * 
	 * @return true if the object is a bomb otherwise false
	 */
	public boolean isBomb() {
		if (name.equals("bomb"))
			return true;
		return false;
	}

	/**
	 * 
	 * @param scale of the image
	 * @param fileName- the file that contains the image
	 * @return if the file exists return true, otherwise it will print stack trace 
	 *  
	 */
	protected Image initFruitSmash(int scale, String fileName) {
		return loadImage(scale, fileName);
	}

	/**
	 * 
	 * @param scale of the image
	 * @param fileName- the file that contains the image
	 * @return if the file exists return true, otherwise it will print stack trace 
	 *  
	 */
	protected Image loadExplosion(int scale, String fileName) {
		return loadImage(scale, fileName);
	}

	/**
	 * 
	 * @param scale of the image
	 * @param fileName- the file that contains the image
	 * @return if the file exists return true, otherwise it will print stack trace 
	 *  
	 */
	protected Image loadImage(int scale, String fileName) {
		Image img = null;
		try {
			img = ImageIO.read(new File(fileName));
			ImageIcon imgIcon = new ImageIcon(img.getScaledInstance(scale, scale, Image.SCALE_DEFAULT));
			img = imgIcon.getImage();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}

	/**
	 * The method are used in the subclasses
	 * @return
	 */
	public Image getFruitSmash() {
		return null;
	}
	
	/**
	 * 
	 * @return how many time the get fruit method has been called
	 */
	public int getTime() {
		return count;
	}

	/**
	 * Compute the y position of the fruit
	 * @param time- how fast the fruit needs to move
	 * @return the y position that the fruit needs to move
	 */
	private double computePos(double time) {

		double pos;
		if (isUp()) {
			pos = (dy * time) + (dv_y * time * time);
			dyf = (int) Math.sqrt(((dy * dy) + (2 * dv_y * (y - HEIGHT / 2))));

		} else 
			pos = -(dy * time) + (dv_y * time * time);

		return pos;
	}

	/**
	 * Compute the x position of the fruit
	 * @param time- how fast the fruit needs to move
	 * @return the x position that the fruit needs to move
	 */
	private double computePosX(double time) {

		pos = (dx * time) + (dv_x * time * time) - ((int) (Math.random() * 2) * 2 * (dx * time) + (dv_x * time * time));
		return pos / 2;
	}

	/**
	 * 
	 * @return true if the fruit is moving upward otherwise false
	 */
	private boolean isUp() {
		if (up == true && dyf > 0)
			return true;
		up = false;
		return false;
	}

}
