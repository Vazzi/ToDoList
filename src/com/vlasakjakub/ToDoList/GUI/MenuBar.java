package com.vlasakjakub.ToDoList.GUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Represents menu bar of application. 
 *
 */
public class MenuBar extends JMenuBar{
	private static final long serialVersionUID = 1L;
	
	
	private MainFrame m_Parent;
	private JLabel statusBar;
	static final Color MAIN_COLOR = new Color(192,192,192);
	
	/**
	 * Initialize status bar and parent frame.
	 * @param status bar
	 * @param par parent
	 */
	MenuBar(JLabel status, MainFrame par){
		this.statusBar = status;
		m_Parent = par;
		this.setBackground(MAIN_COLOR);
		init();
	}
	
	/**
	 * Calls initialized methods.
	 */
	private void init(){
	    makeFileMenu();
	    makeViewMenu();
	    makeDoMenu();
	}
	
	/**
	 * Creates Do menu.
	 */
	private void makeDoMenu(){
		JMenu doMenu = new JMenu("Do");
		doMenu.setMnemonic(KeyEvent.VK_D);
		doMenu.setBackground(MAIN_COLOR);
	    addDoItems(doMenu);
	    this.add(doMenu);
	}
	
	/**
	 * Creates View menu.
	 */
	private void makeViewMenu(){
		JMenu view = new JMenu("View");
	    view.setMnemonic(KeyEvent.VK_V);
	    view.setBackground(MAIN_COLOR);
	    addViewItems(view);
	    this.add(view);
	}
	
	/**
	 * Creates File menu.
	 */
	private void makeFileMenu(){
		JMenu file = new JMenu("File");
	    file.setMnemonic(KeyEvent.VK_F);
	    file.setBackground(MAIN_COLOR);
	    addFileItems(file);
	    this.add(file);
	}
	
	/**
	 * Adds items to Do menu.
	 * @param doMenu
	 */
	private void addDoItems(JMenu doMenu){
		
		//Trash Done
		JMenuItem trashDone = new JMenuItem("Trash Done");
		trashDone.setMnemonic(KeyEvent.VK_T);
		trashDone.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              m_Parent.trashDone();
            }
        });
		
		//Import Project
		JMenuItem importP = new JMenuItem("Import Project");
		importP.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              m_Parent.importProject();
            }
        });
		
		//Export Project
		JMenuItem exportP = new JMenuItem("Export Project");
		exportP.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              m_Parent.exportProject();
            }
        });
		
		doMenu.add(trashDone);
		doMenu.addSeparator();
		doMenu.add(importP);
		doMenu.add(exportP);
	}
	
	/**
	 * Adds items to View menu.
	 * @param viewMenu
	 */
	private void addViewItems(JMenu viewMenu){
		//View Status bar
		JCheckBoxMenuItem viewStatusBar = new JCheckBoxMenuItem("Status bar");
		viewStatusBar.setMnemonic(KeyEvent.VK_S);
		viewStatusBar.setState(true);
		
		viewStatusBar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
              if (statusBar.isVisible()) {
                  statusBar.setVisible(false);
              } else {
                  statusBar.setVisible(true);
              }
            }
        });

		viewMenu.add(viewStatusBar);
	}
	
	/**
	 * Adds items to File menu.
	 * @param fileMenu
	 */
	private void addFileItems(JMenu fileMenu){
		
		//NewToDo
		JMenuItem newToDo = new JMenuItem("New To Do");
		newToDo.setMnemonic(KeyEvent.VK_N);
		newToDo.setToolTipText("Create new to do entry");
		newToDo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				m_Parent.addNewTask();
				
			}
		});
				
		//NewProject
		JMenuItem newProject = new JMenuItem("New Project");
		newProject.setMnemonic(KeyEvent.VK_P);
		newProject.setToolTipText("Create new project");
		
		newProject.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              m_Parent.addNewProject();
            }
        });
		
		//DeleteProject
		JMenuItem delProject = new JMenuItem("Delete Project");
		delProject.setMnemonic(KeyEvent.VK_R);
		delProject.setToolTipText("Delete project");
		
		delProject.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              m_Parent.deleteProject();
            }
        });

		//Exit
		JMenuItem exit = new JMenuItem("Exit");
		exit.setMnemonic(KeyEvent.VK_E);
		exit.setToolTipText("Exit application");
		exit.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		m_Parent.close();
	        }
	
	    });
		
		fileMenu.add(newToDo);
		fileMenu.add(newProject);
		fileMenu.addSeparator();
		fileMenu.add(delProject);
		fileMenu.addSeparator();
		fileMenu.add(exit);
		
	}
	
}