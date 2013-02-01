package com.vlasakjakub.ToDoList.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.vlasakjakub.ToDoList.Data.Container;
import com.vlasakjakub.ToDoList.Data.File;
import com.vlasakjakub.ToDoList.Data.Folder;
import com.vlasakjakub.ToDoList.Data.Project;
import com.vlasakjakub.ToDoList.Data.Registr;
import com.vlasakjakub.ToDoList.Data.Task;



/**
 * Main window. Contains tasks, side bar, menu bar.
 *
 */
public class MainFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	
	static final int WIDTH = 800; /**< Window width. */
	static final int HEIGHT =  WIDTH * 3 / 4; /**< Window height. */
	static final Color SIDEBAR_COLOR = new Color(220,220,220); /**< Side bar background color. */
	
	private JPanel m_container;
	private Box m_sidebar;
	private JScrollPane m_scrollLeft;
	private TasksPanel m_mainPanel; 
	private JLabel m_StatusBar;
	private JList<String> m_ListFold, m_ListProj;
	
	private Container m_CurrData; /**< Current tasks container which is visible in frame.*/
	private Registr m_AllData;	/**< Contains all data.*/

	
	/**
	 * @param title window title.
	 */
	public MainFrame( String title ){
		initData();		
		init();
		setTitle(title);	
	}
	
	/**
	 * Initializes database, and current data.
	 */
	private void initData(){
		m_AllData = new Registr();

		Registr tests = File.readRegistr();
		if(tests != null) m_AllData = tests;
		
		m_CurrData = new Folder("Today");
		Collection<Task> test = m_AllData.getTasksFolder("Today");
		m_CurrData.initTasks(test);
	}
	
	/**
	 * Calls initialize methods.
	 */
	private void init(){
		setMenu();
		setPanel();
		setSidebar();
		setFrame();
		setUpData();
		updateStatus();
		add(m_container);
		
		setVisible(true);
	}
	
	/**
	 * Sets up status bar and menu bar.
	 */
	private void setMenu(){
		m_StatusBar = new JLabel(" Statusbar");
        m_StatusBar.setBorder(BorderFactory.createEtchedBorder(
                EtchedBorder.RAISED));
        add(m_StatusBar, BorderLayout.SOUTH);
		
		MenuBar menubar = new MenuBar(m_StatusBar, this);
		setJMenuBar(menubar);
	}
	
	/**
	 * Sets up frame and window listener.
	 */
	private void setFrame(){
		this.setMinimumSize(new Dimension(640,HEIGHT/2));
		this.setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		/**
		 * What I need is when window is closing so the others methods are unimplemented.
		 */
		this.addWindowListener(new WindowListener(){
			public void windowClosing(WindowEvent e) {
				/**Saving data when closing window.*/
				m_AllData.addTasks(m_mainPanel.getNewTasks());
				File.writeRegistr(m_AllData);
		    }
			@Override
			public void windowOpened(WindowEvent e) {}

			@Override
			public void windowClosed(WindowEvent e) {}

			@Override
			public void windowIconified(WindowEvent e) {}

			@Override
			public void windowDeiconified(WindowEvent e) {}

			@Override
			public void windowActivated(WindowEvent e) {}

			@Override
			public void windowDeactivated(WindowEvent e) {}
		});
		
	}
	
	/**
	 * Sets up main panel. How side bar and main panel are located.
	 */
	private void setPanel(){
		
		GridBagConstraints con = new GridBagConstraints();
		con.weighty = 1.0;
		con.fill = GridBagConstraints.BOTH;

		m_container = new JPanel(new GridBagLayout());

		m_sidebar = Box.createVerticalBox();

	    m_scrollLeft = new JScrollPane(m_sidebar);
		m_scrollLeft.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		m_scrollLeft.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		m_scrollLeft.setMaximumSize(new Dimension(200,0));
		m_scrollLeft.setPreferredSize(new Dimension(200,0));
		m_scrollLeft.setMinimumSize(new Dimension(200,0));
		m_scrollLeft.getViewport().setBackground(SIDEBAR_COLOR);
		m_scrollLeft.setBackground(SIDEBAR_COLOR);
		m_scrollLeft.setBorder(new MatteBorder(0,0,0,1,Color.GRAY));
		
		m_mainPanel = new TasksPanel(m_CurrData);
		
		m_container.add(m_scrollLeft,con);
		con.weightx = 30.0;
		m_container.add(m_mainPanel,con);

	}
	
	/**
	 * Sets up side bar. 
	 * Side bar contains: List of Folders and Projects.
	 */
	private void setSidebar(){
		JLabel labelProj = new JLabel("PROJECTS: ");
		labelProj.setAlignmentX(Component.LEFT_ALIGNMENT);
		labelProj.setFont(new Font("Veranda",Font.PLAIN, 13));
		labelProj.setBorder(BorderFactory.createEmptyBorder(10, 5, 0, 0));
			
		m_ListFold = new JList<String>();
		m_ListFold.setName("F");
		setListProp(m_ListFold);
		
		m_ListProj = new JList<String>();
		m_ListProj.setName("P");
		setListProp(m_ListProj);
		
		setListenersToLists(m_ListProj, m_ListFold);
		setListenersToLists(m_ListFold, m_ListProj);
			
		m_sidebar.add(m_ListFold);
		m_sidebar.add(labelProj);
		m_sidebar.add(m_ListProj);
		
	}
	/**
	 * Sets up list properties.
	 * @param list 
	 */
	private void setListProp(JList<String> list){
		list.setBackground(m_sidebar.getBackground());
		list.setAlignmentX(Component.LEFT_ALIGNMENT);
		list.setFixedCellWidth(200);
		list.setFont(new Font("",Font.PLAIN, 13));
		list.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
	}
	
	/**
	 * Sets up listener for both of lists.
	 * Changes filters for Project or Folder. If user choose Folder than
	 * Project list will be unselected and vice versa. Lists are like filters for tasks.
	 * @param first list which will be set up listener
	 * @param second list
	 */
	private void setListenersToLists(final JList<String> first, final JList<String> second){
		first.addListSelectionListener(new ListSelectionListener(){
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting()){
					second.clearSelection();
				}else{
					JList<String> list = (JList<String>)e.getSource();
					if(list.getSelectedIndex()==-1)return;
					String text = list.getSelectedValue().trim();
					m_AllData.addTasks(m_mainPanel.getNewTasks());
					if(list.getName().equals("F")){
						m_CurrData = new Folder(text);
						m_CurrData.initTasks(m_AllData.getTasksFolder(text));
					}else if(list.getName().equals("P")){
						m_CurrData = m_AllData.getProject(text);
						m_CurrData.initTasks(m_AllData.getTasksProject(text));
					}
					m_mainPanel.changeData(m_CurrData);
					updateStatus();
					m_AllData.addTasks(m_mainPanel.getNewTasks());
					File.writeRegistr(m_AllData);
				}
			}	
		});
	}
	
	/**
	 * Adds new task.
	 */
	public void addNewTask(){
		m_mainPanel.addNewTask();
	}
	
	/**
	 * Shows dialog for new Project. And adds it.
	 */
	public void addNewProject(){
		ProjectDialog dialog = new ProjectDialog("New Project");
		Collection<String> projects = new ArrayList<String>();
		for(String item : m_AllData.getProjectsNames())
			projects.add(item);
		dialog.setProjects(projects);
		dialog.setVisible(true);
		/** Adding new Project */
		if(dialog.getValues()!= null){
			m_AllData.addProject(dialog.getValues());
			setUpData();
		}
		m_AllData.addTasks(m_mainPanel.getNewTasks());
		File.writeRegistr(m_AllData);
	
	}
	
	/**
	 * Adjusts Strings. Giving them artificial indent.
	 * @param items array of Strings to adjust.
	 * @return Adjusted String array.
	 */
	private String[] listItemsEdit(String[] items){
		for( int i = 0; i < items.length; i++){
			items[i] = "   " + items[i];
		}
		return items;
	}
	
	
	/**
	 * Updates status bar text.
	 */
	private void updateStatus(){
		m_StatusBar.setText("Today's tasks: " + m_AllData.getTodayCount() + "    |   " + "Tasks count: "+ m_CurrData.getTasksCount());
	}
	
	/**
	 * Setting up data like Folders/Projects list and giving TaskEditDialog Projects, Folders lists.
	 */
	private void setUpData(){
		TaskEditDialog.setData( m_AllData.getProjectsNames(),  m_AllData.getFoldersNames());
		
		m_ListFold.setListData(listItemsEdit(m_AllData.getFoldersNames()));
		m_ListProj.setListData(listItemsEdit( m_AllData.getProjectsNames()));
		m_ListFold.setSelectedIndex(1);
		
		revalidate();
		repaint();
		
	}
	
	/**
	 * Removes Done Tasks.
	 */
	public void trashDone(){
		m_AllData.cleanDoneTasks();
		if(m_ListProj.getSelectedIndex() == -1)
			m_CurrData.initTasks(m_AllData.getTasksFolder(m_ListFold.getSelectedValue().trim()));
		else
			m_CurrData.initTasks(m_AllData.getTasksProject(m_ListProj.getSelectedValue().trim()));
		m_mainPanel.changeData(m_CurrData);
		updateStatus();
		m_AllData.addTasks(m_mainPanel.getNewTasks());
		File.writeRegistr(m_AllData);
	}
	
	/**
	 * Deletes Project. Asks which one and deletes it.
	 */
	public void deleteProject(){
		String[] possibilities = m_AllData.getProjectsNames();
		String delProject = (String)JOptionPane.showInputDialog( this,"Project to delte:","Delete Project",JOptionPane.PLAIN_MESSAGE,null,possibilities, 1);

		//If a string was returned.
		if ((delProject != null) && (delProject.length() > 0)) {
			//Really want to delete project dialog
			int really = JOptionPane.showConfirmDialog(this,
					"Really want to delete " + delProject + " project?.\n All tasks in this project will be removed.",
					"Delete Project " + delProject,
				    JOptionPane.YES_NO_OPTION);
			//If really want to delete than delete
			if(really == 0){
				m_AllData.addTasks(m_mainPanel.getNewTasks());
				m_AllData.removeProject(m_AllData.getProject(delProject));
				setUpData();
			}		
		    
		}

	}
	
	/**
	 * Closes this.
	 */
	 public void close() {	 
		 
         WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
         Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
	 }
	
	 /**
	  * Imports new Project. Shows finder and user can choose which file to import.
	  * Than imports project. 
	  */
	 public void importProject(){
		 	
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new FileNameExtensionFilter(".tdl files",  "tdl"));
		int actionDialog = chooser.showOpenDialog(this);
		if(actionDialog == JFileChooser.APPROVE_OPTION){
			String path = chooser.getSelectedFile().getPath();
			Project importedProject = File.readProject(path);
			if(m_AllData.projectIsIn(importedProject)){
				JOptionPane.showMessageDialog(this, "You already has this project imported.", "Same Project", JOptionPane.ERROR_MESSAGE, null);
				return;
			}
			m_AllData.addProject(importedProject);
			setUpData();
		}
	 }
	 
	 /**
	  * Exports chosen Project. Asks which one by dialog and export it to file which user choose from dialog.
	  */
	 public void exportProject(){
		 m_AllData.addTasks(m_mainPanel.getNewTasks());
		 
		String[] possibilities = m_AllData.getProjectsNames();
		String name = (String)JOptionPane.showInputDialog( this,"Project to delte:","Delete Project",JOptionPane.PLAIN_MESSAGE,null,possibilities, 1);
		
		if ((name != null) && (name.length() > 0)) {
			Project exportProject = new Project(m_AllData.getProject(name));
			exportProject.initTasks(m_AllData.getTasksProject(name));		
	
			JFileChooser chooser = new JFileChooser();
			chooser.setFileFilter(new FileNameExtensionFilter(".tdl files",  "tdl"));
			int actionDialog = chooser.showSaveDialog(this);
			if(actionDialog == JFileChooser.APPROVE_OPTION){
				String path =  chooser.getSelectedFile( ) + ".tdl" ;
				File.writeProject(exportProject, path);
			}

		}
	 }

}