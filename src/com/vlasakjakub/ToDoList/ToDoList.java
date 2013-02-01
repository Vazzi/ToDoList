package com.vlasakjakub.ToDoList;

import javax.swing.SwingUtilities;

import com.vlasakjakub.ToDoList.GUI.MainFrame;

/**
 * The main class. Executes main frame.
 *
 * @author Jakub Vlasák
 */
public class ToDoList{
	
	
	public static void main(String[] args) {
				
		SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	
            	MainFrame mainFrame = new MainFrame("To Do List");
            	mainFrame.setVisible(true);
            	
            }
        });
				
	}
	
	
}