import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JPanel;


/**
 * 
 * @author Marisabel Chang
 *
 *This panel contains 4 boards of the fruit punch game. The boards are store in sequence, so when the score board appears, 
 *the next board is the initial board
 *     
 */
public class Screen extends JPanel {

	private final int WIDTH = 1000;
	private final int HEIGHT = 600;
	private GameBoard board;//game board
	private InitBoard start;
	private InstructionBoard instruction;
	private GameOverBoard gameOver;

	public Screen() {

		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		start = new InitBoard();
		start.setUser();
		instruction= new InstructionBoard();
		board = new GameBoard();
		gameOver = new GameOverBoard(board,start);
		add(start);
		add (instruction);
		add(board);
		add(gameOver);

	}

	@Override
	protected void paintComponent(Graphics g) {
		checkStartBoard();
		checkInstructionBoard();
		checkGameBoard();
		checkGameOverBoard();
	}

	
	private void checkStartBoard() {

		if (!start.isVisible()) {
			start.resetVisibility();
			start.setUserVisibility(false);// Disappear the text field that contains the name of
										  // the player
		}
	}
	
	private void checkInstructionBoard() {

		if (!instruction.isVisible()) {
			instruction.resetVisibility();
		}
	}

	private void checkGameBoard() {

		if (!board.isVisible()) {
			board = new GameBoard();
			remove(2);//Remove the game board from the panel
			add(board, 2);//Add the new game board to the position of the preview game board	
		}
	}

	private void checkGameOverBoard() {

		if (!gameOver.isVisible()) {
			gameOver.setBoard(board);
			gameOver.resetVisibility();
		}
	}
}
