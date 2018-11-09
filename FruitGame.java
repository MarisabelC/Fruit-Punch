import javax.swing.JFrame;
/**
 * 
 * @author Marisabel Chang
 * 
 * Create a frame that contains the initial, instruction, game and score board
 *
 */
public class FruitGame {

	void run() {

		JFrame frame = new JFrame("Fruit Punch");
		frame.add(new Screen());
		frame.setResizable(false);
		frame.setLocation(200, 100);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
	}

}
