package es.ucm.fdi.tp.extra.jboard;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class BoardExample extends JFrame {

	private JBoard boardComp;
	private Integer[][] board;

	private JTextField rows;
	private JTextField cols;
	
	private int numOfRows;
	private int numOfCols;

	public BoardExample() {
		super("[=] JBoard Example! [=]");
		initGUI();
	}

	private void initGUI() {
		createBoardData(10, 10);

		JPanel mainPanel = new JPanel(new BorderLayout());

		boardComp = new JBoard() {

			@Override
			protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {
				System.out.println("Mouse: " + clickCount + "clicks at position (" + row + "," + col + ") with Button "
						+ mouseButton);
			}

			@Override
			protected void keyTyped(int keyCode) {
				System.out.println("Key " + keyCode + " pressed ..");
			}

			@Override
			protected Shape getShape(int player) {
				return Shape.CIRCLE;
			}

			@Override
			protected Integer getPosition(int row, int col) {
				return BoardExample.this.getPosition(row, col);
			}

			@Override
			protected int getNumRows() {
				return numOfRows;
			}

			@Override
			protected int getNumCols() {
				return numOfCols;
			}

			@Override
			protected Color getColor(int player) {
				return BoardExample.this.getColor(player);
			}

			@Override
			protected Color getBackground(int row, int col) {
				return Color.LIGHT_GRAY;

				// use this for 2 chess like board
				// return (row+col) % 2 == 0 ? Color.LIGHT_GRAY : Color.BLACK;
			}

			@Override
			protected int getSepPixels() {
				return 1; // put to 0 if you don't want a separator between
							// cells
			}
		};

		mainPanel.add(boardComp, BorderLayout.CENTER);
		JPanel sizePabel = new JPanel();
		mainPanel.add(sizePabel, BorderLayout.PAGE_START);

		rows = new JTextField(5);
		cols = new JTextField(5);
		sizePabel.add(new JLabel("Rows"));
		sizePabel.add(rows);
		sizePabel.add(new JLabel("Cols"));
		sizePabel.add(cols);
		JButton setSize = new JButton("Set Size");
		sizePabel.add(setSize);
		setSize.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int numOfRows = new Integer(rows.getText());
					int numOfCols = new Integer(cols.getText());
					createBoardData(numOfRows, numOfCols);
					boardComp.repaint();
				} catch (NumberFormatException _e) {
				}
			}
		});

		mainPanel.setOpaque(true);
		this.setContentPane(mainPanel);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 500);
		this.setVisible(true);
	}


	private void createBoardData(int numOfRows, int numOfCols) {
		this.numOfRows = numOfRows;
		this.numOfCols = numOfCols;
		board = new Integer[numOfRows][numOfCols];
		for (int i = 0; i < numOfRows; i++)
			for (int j = 0; j < numOfCols; j++) {
				double d = Math.random();
				if (d < 0.6) {
					board[i][j] = null;
				} else if (d < 0.8) {
					board[i][j] = 0;
				} else {
					board[i][j] = 1;
				}
			}
	}

	protected Integer getPosition(int row, int col) {
		return board[row][col];
	}

	protected Color getColor(int player) {
		return player == 0 ? Color.BLUE : Color.RED;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new BoardExample();
			}
		});
	}
}
