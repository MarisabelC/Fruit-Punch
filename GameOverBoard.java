import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * 
 * @author Marisabel Chang
 * 
 * This panel contains the 3 highest score and the current score of the player. The user must add the initial and the game boards.     
 *
 */
public class GameOverBoard extends JPanel {

	/*
	 * Create a pair that contains a string and integer. The integer needs to be a positive number
	 */
	
	public class Pair {
		private String name;
		private int score;

		Pair(String name, Integer score) {
			this.name = name;
			this.score = score;
		}

		public int getScore() {
			return score;
		}

		public String getName() {
			return name;
		}
		
		@Override 
		public String toString(){
			String str=name+" "+Integer.toString(score);
			return  str;
		}

	}

	private final int WIDTH = 1000;
	private final int HEIGHT = 600;
	private GameBoard board;
	private InitBoard initBoard;
	private List<Pair> data;
	private final int MAX = 3;
	Image background;
	int score;

	public GameOverBoard(GameBoard board, InitBoard initBoard) {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		this.board = board;
		this.initBoard = initBoard;
		loadImage();
		data = new ArrayList<>();
		addMouseListener(new MouseControl());
		checkFile();//Check if the file with the 3 highest score exist 
	}

	/**
	 * 
	 * @param board- it is the game board
	 */
	public void setBoard(GameBoard board) {
		this.board = board;
	}

	/**
	 * Check if the file with the 3 highest score exists. If so, add these scores to the list
	 * of score. Otherwise throw and message that says that the file does not exist  
	 */
	
	private void checkFile() {

		Pair pair;
		try (Scanner in = new Scanner(new File("score"))) {

			while (in.hasNext()) {
				String name = in.next();
				Integer score = in.nextInt();
				pair = new Pair(name, score);
				data.add(pair);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	 * Set the visibility of the panel to true.
	 */
	public void resetVisibility() {
		setVisible(true);
	}

	@Override
	public void paintComponent(Graphics g) {

		g.drawImage(background, 0, 0, this);
		drawScore(g);
	}

	/**
	 * Draw some strings that contain the name of the 3 players who have the highest score 
	 * with their respective score. Draw the current score of the player
	 *  
	 * @param g
	 * 
	 */
	private void drawScore(Graphics g) {

		int width=WIDTH / 4;//The x position where the string starts to print
		int height=HEIGHT / 4; //Y position 
		
		if (!board.isVisible()) {//Check visibility of the game board
			Graphics2D g2 = (Graphics2D) g;
			g.setColor(Color.BLACK);
			Font font = new Font("Serif", Font.BOLD, 40);
			g.setFont(font);
			g2.drawString("Score :", width, height);
			checkScore();

			try (PrintWriter out = new PrintWriter("score")) {

				for (int i = 0; i < MAX; i++) {

					g2.drawString(data.get(i).getName(), width, height + 
							((i + 1) * HEIGHT / 9));//name of a player
					g2.drawString(Integer.toString(data.get(i).getScore()), WIDTH / 2,
							height + ((i + 1) * HEIGHT / 9));//score of the corresponded player
					out.println(data.get(i)); //Rewrite the score file
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			g2.drawString("Your Score:", width, height + ((MAX + 1) * HEIGHT / 9)); 
			g2.drawString(Integer.toString(board.score), WIDTH / 2, height + 
					((MAX + 1) * HEIGHT / 9));//Current player's score
			g.setColor(Color.WHITE);
			g2.drawString("Click to continue...", width, (height) + ((MAX + 3) * HEIGHT / 9));
		}
	}

	private void checkScore() {

		String user = initBoard.getUser();// get player's name
		
		for (int i = 0; i < MAX; i++) {
			
			if (data.get(i).getScore() <= board.getScore()) { // Check if the current score is bigger than a element in the 
															  // score list
				
				data.add(i, new Pair(user, Integer.valueOf(board.getScore())));
				data.remove(MAX);//Remove the last element of the score list
				break;
			}
		}

	}

	public class MouseControl implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent arg0) {	}

		@Override
		public void mouseEntered(MouseEvent arg0) {	}

		@Override
		public void mouseExited(MouseEvent arg0) {}

		@Override
		public void mousePressed(MouseEvent arg0) {
			setVisible(false);  
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {}

	}

}
