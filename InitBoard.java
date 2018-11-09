import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class InitBoard extends JPanel {

	private final int WIDTH = 1000;
	private final int HEIGHT = 600;
	private JButton start;
	private JButton quit;
	private JButton player;
	private Image background;
	private JTextField user;

	public InitBoard() {

		initButton();
		exitButton();
		playerButton();
		setLayout(null);
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		loadImage();
	}

	/**
	 * Create the text field in which the player types his or her name.
	 * The text field is added to the bottom of the board
	 * 
	 */
	public void setUser() {

		user = new JTextField(15);
		add(user);
		user.setBounds(2 * WIDTH / 5, 3 * HEIGHT / 5, 200, 40);
		Font font = new Font("SANS_SERIF", Font.PLAIN, 20);
		user.setFont(font);
		user.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent key) {}
			@Override
			public void keyReleased(KeyEvent arg0) {}
			
			@Override
			public void keyPressed(KeyEvent key) {
				if(key.getKeyCode()==KeyEvent.VK_ENTER)
					setVisible(false);
			}
		});
	}

	/**
	 * 
	 * @return the name of the user
	 */
	public String getUser() {
		if (user.getText().equals(""))
			user.setText("no_name"); //Default text 
		return user.getText();
	}

	/**
	 * 
	 * @param flag
	 * Set the text field visible or invisible
	 */
	public void setUserVisibility(boolean flag) {
		user.setVisible(flag);
		user.setEnabled(flag);
	}

	/**
	 * Create start button. if the start button is click the initial board is set to invisible 
	 */
	private void initButton() {
		start = new JButton("Start");
		start.setBounds(WIDTH / 4, 3 * HEIGHT / 4, 100, 50);
		start.setBackground(Color.blue);
		start.setForeground(Color.WHITE);
		start.addActionListener(new startListener());
		add(start);
		resetVisibility();
	}

	/**
	 * Create the player button. If the button is click the text field appears. In this text 
	 * field, the player adds his or her name
	 */
	
	private void playerButton() {
		player = new JButton("Player");
		player.setBounds(WIDTH / 2, 3 * HEIGHT / 4, 100, 50);
		player.setBackground(Color.blue);
		player.setForeground(Color.WHITE);
		player.addActionListener(new playerListener());
		add(player);
	}

	/**
	 * Create the quit button. If the button is clicked, a box appears asking the user
	 * if the player want to quit. 
	 */
	private void exitButton() {

		quit = new JButton("Quit");
		quit.setBounds(3 * WIDTH / 4, 3 * HEIGHT / 4, 100, 50);
		quit.setBackground(Color.blue);
		quit.setForeground(Color.WHITE);
		quit.addActionListener(new quitListener());
		add(quit);

	}

	/**
	 * Set visible the board
	 */
	
	public void resetVisibility() {
		setVisible(true);
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

	
	@Override
	protected void paintComponent(Graphics g) {

		g.drawImage(background, 0, 0, this);
		drawTitle(g);
	}

	/**
	 * Draw the name of the game
	 * @param g
	 */
	private void drawTitle(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		g.setColor(new Color(218, 165, 32));
		Font font = new Font("SANS_SERIF", Font.BOLD, 100);
		g.setFont(font);
		g2.drawString("Fruit Punch", WIDTH / 4, HEIGHT / 2);
	}

	/**
	 * Set the board invisible
	 *
	 */
	
	private class startListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			setVisible(false);
		}

	}

	/**
	 * Set the text field visible, so the player can add his or her name to the game 
	 */
	private class playerListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			setUserVisibility(true);
			user.setText("");
		}

	}

	/**
	 * Ask the user if he or her wants to quit the game. If so, the window that contains the game 
	 * closes 
	 */
	public class quitListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {

			int option = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit", "Exit",
					JOptionPane.YES_NO_OPTION);
			if (option == 0)
				System.exit(0);
		}

	}

}