package com.vlasakjakub.ToDoList.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;


import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import com.vlasakjakub.ToDoList.Data.Container;
import com.vlasakjakub.ToDoList.Data.Project;
import com.vlasakjakub.ToDoList.Data.Task;


/**
 * Panel which contains all tasks of Project or Folder.
 *
 */
public class TasksPanel extends JScrollPane{
	private static final long serialVersionUID = 1L;
	
	private static Box m_MainBox = Box.createVerticalBox();
	private static Box m_TasksBox = Box.createVerticalBox();
	private JLabel m_PName;
	private JTextPane m_PDesc;
	private JScrollPane m_PDescPane;
	protected Container m_Content;
	private Collection<Task> m_NewTasks;
	private boolean m_IsProject;
	
	/**
	 * @param content data.
	 */
	TasksPanel ( Container content ){
		super(m_MainBox);
		m_Content = content;
		TaskPanel.parent = this;
		init();
	}
	
	/**
	 * Initializes panel and sets up panel properties. And adds tasks.
	 */
	protected void init(){
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.setIgnoreRepaint(false);
		this.setBorder(null);
		this.getViewport().setBackground(new Color(192,192,192));
		this.setBackground(new Color(192,192,192));
		
		m_NewTasks = new ArrayList<Task>();
		m_IsProject = true;
		
		m_TasksBox.setBorder(new EmptyBorder(new Insets(5, 10, 5, 10)));
		Collection<Task> tasks = m_Content.getTasks();
		if(tasks != null){
			for ( Task item :  tasks){
				m_TasksBox.add(new TaskPanel(item));
			}
		}
		m_MainBox.add(m_TasksBox);	
	}
	
	/**
	 * Changes content.
	 * @param newContent
	 */
	public void changeData(Container newContent){
		m_NewTasks.clear();
		m_TasksBox.removeAll();
		m_MainBox.removeAll();
		m_Content = newContent;
		
		if(newContent instanceof Project){
			showProjectBar();
			m_IsProject = true;
		}else
			m_IsProject = false;
		m_MainBox.add(m_TasksBox);
		Collection<Task> tasks = m_Content.getTasks();
		if(tasks != null){
			for ( Task item : m_Content.getTasks() ){
				m_TasksBox.add(new TaskPanel(item));
			}
		}
		revalidate();
		repaint();	
	}
	
	
	/**
	 * If content is a Project than is need to do this method. 
	 * Shows Project bar. Which is a panel on the top of all tasks.
	 * Contains name and description of current Project.
	 */
	private void showProjectBar(){
		JPanel projectPanel = new JPanel();
		BoxLayout boxLayout = new BoxLayout(projectPanel, BoxLayout.Y_AXIS);
		projectPanel.setLayout(boxLayout);
		
		projectPanel.setPreferredSize(new Dimension(0,100));
		projectPanel.setMaximumSize(new Dimension(2000,100));
	
		JPanel title = new JPanel(new FlowLayout(FlowLayout.LEFT)); 
		JLabel project = new JLabel("PROJECT: ");
		project.setFont(new Font(Font.SERIF, Font.PLAIN, 16));
		m_PName = new JLabel(m_Content.getName());
		m_PName.setFont(new Font("", Font.PLAIN, 16));
		JLabel editL = new JLabel();
		editL.setIcon(new ImageIcon("res/icons/Edit.png"));
		editL.setToolTipText("Edit project.");
		
		editL.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(m_PDesc.isEditable()){
					m_PDesc.setEditable(false);
					((Project)m_Content).desc = m_PDesc.getText();
					((JLabel)e.getSource()).setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/Edit.png")));
				
				}else{
					m_PDesc.setEditable(true);
					((JLabel)e.getSource()).setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/Ok.png")));
					m_PDesc.requestFocus();
				}
			}
		});
		
		
		title.add(Box.createVerticalStrut(20));
		title.add(project);
		title.add(m_PName);
		title.add(Box.createGlue());
		title.add(editL);
		title.setMinimumSize(new Dimension(0, 20));

		m_PDesc = new JTextPane();
		m_PDesc.setText(((Project)m_Content).desc);
		m_PDesc.setEditable(false);
		m_PDesc.setOpaque(false);
		m_PDesc.setBorder(new EmptyBorder(new Insets(0, 10, 10, 10)));
		
		m_PDescPane = new JScrollPane(m_PDesc);
		m_PDescPane.setMinimumSize(new Dimension(0,60));
		m_PDescPane.setPreferredSize(new Dimension(2000,60));
		m_PDescPane.setBorder(new EmptyBorder(new Insets(0, 0, 0, 0)));
		
		projectPanel.add(title);
		projectPanel.add(m_PDescPane);

		m_MainBox.add(projectPanel);

	}
	
	/**
	 * Shows empty task dialog. Than create new task.
	 */
	public void addNewTask(){
		Task newTask = new Task();
		TaskEditDialog taskDialog = new TaskEditDialog(newTask);
		taskDialog.setVisible(true);
				
		if(!newTask.isEmpty()){
			if(newTask.folder.equals(m_Content.getName()) || newTask.project.equals(m_Content.getName())){
				m_TasksBox.add(new TaskPanel(newTask));
				m_Content.addTask(newTask);
				this.revalidate();
				this.repaint();	
			}
			m_NewTasks.add(newTask);
		}	
	}
	
	/**
	 * Returns tasks that are created meanwhile. 
	 * @return all new tasks.
	 */
	public Collection<Task> getNewTasks(){
		Collection<Task> newTasks = new ArrayList<Task>(m_NewTasks);
		m_NewTasks.clear();
		
		return newTasks;
	}
	
	/**
	 * Sets task visible false.
	 * @param item task.
	 */
	public void hideTask(TaskPanel item){
		item.setVisible(false);
	}
	
	/**
	 * 
	 * @return if this object is a project or not.
	 */
	public boolean isProject(){
		return m_IsProject;
	}

}
