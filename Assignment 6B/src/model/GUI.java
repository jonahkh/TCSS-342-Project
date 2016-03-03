package model;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * This class runs the GUI interface for the spreadsheet application.
 * 
 * @author Jonah Howard
 * @author Henry Lai
 */
public class GUI extends Observable {
	
	/** The minimum width allowed for the window. */
	private static final int MIN_WIDTH = 400;
	
	/** The minimum height allowed for the window. */
	private static final int MIN_HEIGHT = 300;
	
	/** The JFrame that the spreadsheet is displayed on. */
	private final JFrame myFrame;
	
	/** The spreadsheet that contains all the data. */
	private final Spreadsheet mySpreadsheet;

	/**
	 * This constructor initializes the GUI interface.
	 */
	public GUI() {
		// Sets the title of the program in the title bar.
		myFrame = new JFrame("TCSS 342 Spreadsheet - Group 8");
		final Dimension dimension = initialize();
		mySpreadsheet = new Spreadsheet((int) dimension.getWidth(), 
				(int) dimension.getHeight());
	}
	
	/**
	 * Prompts the user to enter the desired size of the spread sheet.
	 * 
	 * @return the size of the spread sheet
	 */
	private Dimension initialize() {
		// This code in this method was used from
		// http://stackoverflow.com/questions/6555040/multiple-input-in-joptionpane-showinputdialog
		// Some minor modifications have been made to variable names
		final Dimension dim = new Dimension();
		JTextField rowSize = new JTextField(5);
		JTextField columnSize = new JTextField(5);
		// Format the text fields
		
		// The main panel for the dialog
		JPanel panel = new JPanel();
		// Holds the text boxes
		JPanel bottomPanel = new JPanel();

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(new JLabel("Minimum size is 3x3")); // This is asserted
		
		bottomPanel.add(new JLabel("Rows:"));
		bottomPanel.add(rowSize);
		bottomPanel.add(Box.createVerticalStrut(15));
		bottomPanel.add(new JLabel("Columns"));
		bottomPanel.add(columnSize);
	
		panel.add(bottomPanel);
		int result = JOptionPane.showConfirmDialog(myFrame, panel, 
				"Please enter the size of the spreadsheet:", 
				JOptionPane.OK_CANCEL_OPTION);
		if (result == JOptionPane.OK_OPTION) {
			int col = Integer.parseInt(columnSize.getText());
			int row = Integer.parseInt(rowSize.getText());
			// Assert the minimum size of 3x3
			if (col < 3) {
				col = 3;
			}
			if (row < 3) {
				row = 3;
			}
			dim.setSize(row, col);
		} else {
			System.exit(0);	// Terminate the program
		}
		return dim;
	}

	/**
	 * Fills the GUI of its contents.
	 */
	public void run() {
		final JPanel panel = new JPanel(new FlowLayout());
		addObserver(mySpreadsheet);
		myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Adds the scrollable pane to the JFrame to enable the scrollbar
		myFrame.add(new JScrollPane(mySpreadsheet.getTable(), 
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS), BorderLayout.CENTER);
		// Sets the minimum size for the window.
		myFrame.setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		myFrame.setLocationRelativeTo(null);
		
		// Add buttons to the frame
		JButton formulas = new JButton("Display Formulas");
		JButton values = new JButton("Display Values");
		
		formulas.setEnabled(false);
		formulas.doClick();
		addListeners(formulas, values);

		panel.add(formulas, BorderLayout.SOUTH);
		panel.add(values, BorderLayout.SOUTH);
		
		myFrame.add(panel, BorderLayout.SOUTH);
		myFrame.pack();
		myFrame.setVisible(true);
	}
	
	
	
	/**
	 * Adds the action listeners for the "Display Values" and "Display Formulas" buttons.
	 * 
	 * @param buttonOne the Display Formulas button
	 * @param buttonTwo the Display Values button
	 */
	private void addListeners(final JButton formulaButton, final JButton valuesButton) {
		formulaButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent theEvent) {
				// Reverse the enabling of the buttons
				valuesButton.setEnabled(true);
				formulaButton.setEnabled(false);
				// Update the spreadsheet
				setChanged();
				notifyObservers(true);
				clearChanged();
				Spreadsheet.displayFormulas = true;
				// Fill each active cell with its corresponding formula
				for (int i = 0; i < mySpreadsheet.getRows(); i++) {
					for (int j = 1; j < mySpreadsheet.getColumns() + 1; j++) {
						mySpreadsheet.getSpreadsheet()[i][j] = 
								mySpreadsheet.getCells()[i][j].getFormula(); 
					}
				}
				myFrame.repaint();
			}
		});
		
		valuesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent theEvent) {
				// Reverse the enablign of the buttons
				formulaButton.setEnabled(true);
				valuesButton.setEnabled(false);
				// Update the spreadsheet
				setChanged();
				notifyObservers(false);
				clearChanged();
				Spreadsheet.displayFormulas = false;
				// Fill each active cell with its corresponding value
				for (int i = 0; i < mySpreadsheet.getRows(); i++) {
					for (int j = 1; j < mySpreadsheet.getColumns() + 1; j++) {
						if (mySpreadsheet.getCells()[i][j].getValue() != 0) {
							mySpreadsheet.getSpreadsheet()[i][j] = 
									mySpreadsheet.getCells()[i][j].getValue(); 
						} else {
							mySpreadsheet.getSpreadsheet()[i][j] = "";
						}
					}
				}
				myFrame.repaint();
			}
		});
	}

	/**
	 * Main driver for this program.
	 * 
	 * @param theArgs command line arguments, to be ignored
	 */
	public static void main(String... theArgs) {
		GUI gui = new GUI();
		gui.run();
	}
}
