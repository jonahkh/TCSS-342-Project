/* Bui, John
 * Howard, Jonah
 * Lai, Henry
 * Taylor, Lisa
 * TCSS 342 - Data Structures
 * Professor Donald Chinn
 * Homework 6B
 * March 8, 2016
 */

package view;

import model.Spreadsheet;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;

/**
 * This class represents the View drop down menu.
 */
public class WindowMenu {
    
    /** JMenu to hold Window menu items. */
    private final JMenu myWindowMenu;
    
    /** The JFrame that the spreadsheet is displayed on. */
    private final JFrame myFrame;
    
    /** The Spreadsheet. */
    private final Spreadsheet mySpreadsheet;
    
    /** A list of window actions. */
    private List<WindowAction> myWindowActions;
    
    /**
     * Constructs a Window menu.
     * 
     * @param thePanel the CanvasPanel
     */
    public WindowMenu(final JFrame theFrame, final Spreadsheet theSpreadsheet) {
        
        myWindowMenu = new JMenu("Window");
        
        myFrame = theFrame;
        mySpreadsheet = theSpreadsheet;
        
        setupWindowActions();
        
        setupWindowMenu();
    }
    
    /**
     * Returns the Window menu.
     * 
     * @return the Options menu
     */
    public JMenu getWindowMenu() {
        
        return myWindowMenu;
    }
    
    /**
     * Returns the list of windows actions.
     * 
     * @return the Tool actions
     */
    public List<WindowAction> getWindowActions() {
        
        return myWindowActions;
    }
    
    /**
     * Adds menu items to WindowMenu and sets a Mnemonic.
     * Puts window view actions into a button group.
     */
    private void setupWindowMenu() {
        
        final ButtonGroup btngrp = new ButtonGroup();
        
        for (final WindowAction toolAction : myWindowActions) {
            
            final JRadioButtonMenuItem btn = new JRadioButtonMenuItem(toolAction);
            btngrp.add(btn);
            myWindowMenu.add(btn);
        }
    }
    
    /**
     * Adds menu items to WindowMenu and a short description.
     */
    private void setupWindowActions() {
        
        final WindowAction formulaAction = new WindowAction("Display Formulas");
        final WindowAction valueAction = new WindowAction("Display Values");
        
        //formulaAction.putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, 8);
        formulaAction.putValue(Action.SHORT_DESCRIPTION, "View Formulas");
        
        //valueAction.putValue(Action.DISPLAYED_MNEMONIC_INDEX_KEY, 8);
        valueAction.putValue(Action.SHORT_DESCRIPTION, "View (Integer) Values");
        
        myWindowActions = new ArrayList<WindowAction>();
        
        myWindowActions.add(formulaAction);
        myWindowActions.add(valueAction);;
    }
    
     /** Inner Action class for making Window Actions. */ 
    public class WindowAction extends AbstractAction {

        /** A generated serial version UID. */
        private static final long serialVersionUID = -1317743352381047973L;

        /**
         * Constructs an action for a particular spreadsheet view.
         * 
         * @param actionName the view name
         */
        public WindowAction(final String actionName) {
            
            super(actionName);        

            putValue(Action.SELECTED_KEY, true);
        }        
        
        @Override
        public void actionPerformed(final ActionEvent theEvent) {
            
            if ("Display Formulas".equals(theEvent.getActionCommand())) {
                
                // Update the spreadsheet
                mySpreadsheet.setDisplayFormulas(true);
                
                // Fill each active cell with its corresponding formula
                for (int i = 0; i < mySpreadsheet.getRows(); i++) {
                    for (int j = 1; j < mySpreadsheet.getColumns() + 1; j++) {
                        mySpreadsheet.getSpreadsheet()[i][j] = 
                                mySpreadsheet.getCells()[i][j].getFormula(); 
                    }
                }
                myFrame.repaint();
                
            } else {
                
                // Update the spreadsheet
                mySpreadsheet.setDisplayFormulas(false);
                
                // Fill each active cell with its corresponding value
                for (int i = 0; i < mySpreadsheet.getRows(); i++) {
                    for (int j = 1; j < mySpreadsheet.getColumns() + 1; j++) {
                        if (mySpreadsheet.getCells()[i][j].hasInput()) {
                            mySpreadsheet.getSpreadsheet()[i][j] = 
                                    mySpreadsheet.getCells()[i][j].getValue(); 
                        } else {
                            mySpreadsheet.getSpreadsheet()[i][j] = "";
                        }
                    }
                }
                myFrame.repaint();
            } 
        }  
    }
}
