import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GameBoard extends JPanel implements ActionListener {

	private final int MAX = 4;
	protected final int WIDTH = 1000;
	protected final int HEIGHT = 600;
	private Image background;
	private FruitPunch listFruit;
	private FruitType_Bomb[] item;
	private final int DELAY = 17;
	private Timer timer;
	protected int score;
	private List<Live> live;
	private Timer gameOver;
	private JButton pause;

	public GameBoard() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setLayout(null);
		loadImage();
		pauseButton();
		listFruit = new FruitPunch();
		item = new FruitType_Bomb[MAX];

		for (int i = 0; i < MAX; i++) {
			item[i] = listFruit.getNextItem();// Create a list of fruit and bomb
												// randomly
		}
		createLive();
		timer = new Timer(DELAY, this);
		addMouseListener(new MouseControl());
		initGameOverTimer();
		timer.setInitialDelay(500);
	}

	/**
	 * Create the player's live for the game
	 */
	private void createLive() {
		live = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			live.add(new Live());
		}
	}

	/**
	 * Create pause button
	 */
	private void pauseButton() {

		pause = new JButton(new ImageIcon("pause.png"));
		pause.setBounds(6 * (WIDTH / 7), 10, 48, 48);
		pause.setBackground(new Color(70, 130, 180));
		pause.setEnabled(false);
		pause.addActionListener(new pauseListener());
		add(pause);
	}

	private void initGameOverTimer() {
		gameOver = new Timer(1500, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				timer.stop();
				gameOver.stop();

			}
		});
	}

	/**
	 * Set the background of the panel to a specific image. Show a message if the file does not exist.
	 */
	protected void loadImage() {
		try {
			Image img = ImageIO.read(new File("boxingRing.jpg"));
			ImageIcon imgIcon = new ImageIcon(img.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT));
			background = imgIcon.getImage();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Set the board visible
	 */
	public void resetVisibility() {
		setVisible(true);
	}

	/**
	 * 
	 * @return the score of the player
	 */
	public int getScore() {
		return score;
	}

	public void paintComponent(Graphics g) {
		
		Graphics2D g2d = (Graphics2D) g;
		
		
		g.drawImage(background, 0, 0, this);// Draw the background image.
		drawScore(g);
		
		if (live.isEmpty()) {
			drawGameOver(g);
			timer.stop();
		} else
			drawLive(g);
		
		for (int i = 0; i < MAX; i++) {// Draw the fruit image.
			if (item[i] instanceof Bomb) {
				Bomb bomb = (Bomb) item[i];
				if (bomb.isExploded()) {
					timer.setDelay(50);
					drawExplosion(g);
					drawGameOver(g);
				} else
					g2d.drawImage(item[i].getImage(), item[i].getX(), item[i].getY(), this);

			} else {
				g2d.drawImage(item[i].getImage(), item[i].getX(), item[i].getY(), this);
				if (item[i].isSmash() == true) {
					g2d.drawImage(item[i].getFruitSmash(), item[i].getX(), 
							item[i].getY(), this);//draw the fruit when it is smashed
				}
			}
		}

		Toolkit.getDefaultToolkit().sync();
	}

	/**
	 * Draw the explosion of a fruit when a bomb is clicked
	 * @param g
	 */
	private void drawExplosion(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		for (int i = 0; i < MAX; i++) {
			if (item[i] instanceof Bomb) {
				Bomb bomb = (Bomb) item[i];
				bomb.incrementScale();//The explosion is increasing
				g2d.drawImage(bomb.getExplosion(), 50, 0, this);
				bomb.setExplosion(true);
			} else
				item[i].reset();//Set the image to the original position
		}
	}

	/**
	 * Draw the corresponded score
	 * @param g
	 */
	private void drawScore(Graphics g) {

		String scoreLabel;
		scoreLabel = "Score: ";
		g.setColor(Color.RED);
		Font font = new Font("Serif", Font.BOLD, 32);
		g.setFont(font);
		g.drawString(scoreLabel + Integer.toString(score), 50, 50);
	}

	/**
	 * Game over is draw at the center of the board
	 * @param g
	 */
	private void drawGameOver(Graphics g) {

		g.setColor(Color.BLACK);
		Font font = new Font("Serif", Font.BOLD, 100);
		g.setFont(font);
		g.drawString("Game Over ", WIDTH / 4, HEIGHT / 2);
		gameOver.start();

	}

	/**
	 * Draw the corresponded live
	 * @param g
	 */
	private void drawLive(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		int length = live.size() - 1;
		for (int i = length; i >= 0; i--) {
			g2d.drawImage(live.get(i).getImage(), WIDTH - ((i + 1) * 50), HEIGHT - 50, this);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		for (int i = 0; i < MAX; i++) {

			item[i].move(DELAY / 500.0);
			if (item[i].getY() >= HEIGHT || item[i].getTime() >= 100) {
				//if a fruit is not clicked the player loses a life
				if (!live.isEmpty() && item[i].getY() >= HEIGHT && item[i].isBomb() == false) {
					live.remove(0);
				}
				item[i].reset();
				item[i] = listFruit.getNextItem();
			}
		}
		repaint();
	}

	/**
	 *If the game is started and the player clicked the pause button the game 
	 *will be stopped. Similarly, if the game is paused and the player clicked 
	 *the pause button, the game will be started  
	 *
	 */
	private class pauseListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!live.isEmpty()) {
				if (timer.isRunning()) 
					timer.stop();
				else
					timer.start();
			}
		}
	}

	private class MouseControl implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {}

		/**
		 * Set specific image to the cursor when the game starts. The game start when the mouse is entered 
		 * to the window. 
		 */
		@Override
		public void mouseEntered(MouseEvent arg0) {

			Toolkit tool = Toolkit.getDefaultToolkit();
			ImageIcon img = new ImageIcon("boxing.png");//Image for the cursor
			Cursor boxing = tool.createCustomCursor(img.getImage(), new Point(getX(), getY()), "");
			setCursor(boxing);

			if (!pause.isEnabled()) {
				timer.start();
				pause.setEnabled(true);
			}
		}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		/**
		 * The score is updated if the player clicked  a fruit. 
		 * @param arg0
		 */
		@Override
		public void mousePressed(MouseEvent arg0) {

			Point point = getMousePosition();
			int numFruit = 0;

			int xMouse = (int) point.getX();
			int yMouse = (int) point.getY();

			for (int i = 0; i < MAX; i++) {
				int x = item[i].getX();
				int y = item[i].getY();

				if (timer.isRunning() && (xMouse >= x && xMouse <= (x + 48)) && (yMouse >= y && yMouse <= (y + 48))
						&& item[i].isSmash() == false) {
					if (item[i] instanceof Bomb) {//Check if a bomb is clicked, if so the bomb is exploded
						Bomb bomb = (Bomb) item[i];
						bomb.setExplosion(true);
					} else {
						item[i].setSmashFruit(true);
						numFruit++;
					}
				}
			}
			if (numFruit > 1)//If more than 1 fruit is clicked at the same time the 
							//score of these fruit is duplicated
				numFruit *= 2;
			score += numFruit * 10;
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {}

	}

}