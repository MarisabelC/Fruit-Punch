import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 * Create a panel that contains the instruction of the game
 * @author Marisabel Chang
 *
 */
public class InstructionBoard extends JPanel{

	private final int WIDTH = 1000;
	private final int HEIGHT = 600;
	private Image background;
	
	InstructionBoard(){
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		loadImage();
		addMouseListener(new mouseListener());
	}
	
	/**
	 * Set the board visible
	 */
	public void resetVisibility(){
		setVisible(true);
	}
	
	@Override
	protected void paintComponent(Graphics g ){
		
		Graphics2D g2d=(Graphics2D) g;
		g2d.drawImage(background, 0, 0,this);
		g.setColor(Color.WHITE);
		Font font = new Font("Serif", Font.BOLD, 35);
		g.setFont(font);
		//Draw the instruction
		g2d.drawString("* INSTRUCTION", WIDTH/3, HEIGHT/3);
		g2d.drawString("* PUNCH COMBOS FOR A HIGHER SCORE", WIDTH/7, HEIGHT/2);
		g2d.drawString("* DON'T PUNCH BOMBS OR MISS FRUIT", WIDTH/7, 3*HEIGHT/5);
	}
	
	/**
	 * Set the background of the panel to a specific image. Show a message if the file does not exist.
	 */
	private void loadImage() {
		try {
			Image img = ImageIO.read(new File("start.jpg"));
			ImageIcon imgIcon = new ImageIcon(img.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT));
			background = imgIcon.getImage();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	class mouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {	}

		/**
		 * Set the board invisible
		 */
		@Override
		public void mousePressed(MouseEvent e) {
			setVisible(false);
		}

		@Override
		public void mouseReleased(MouseEvent e) {}
		
	}
}
