package com.vlasakjakub.ToDoList.GUI;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;

import java.awt.event.MouseEvent;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import com.vlasakjakub.ToDoList.Data.Task;


/**
 * Dialog for editing or creating Task.
 *
 */
public class TaskEditDialog extends JDialog{
	private static final long serialVersionUID = 1L;
	private static final int HEIGHT = 260;
	private static final int WIDTH = 450;
	
	private static String[] m_Projects, m_Folders;
	
	private Task m_Data;
	
	private JTextField m_NameTF;
	private JTextPane m_DescTP;
	private JComboBox<String> m_ProjectsCB, m_FolderCB;
		
	/**
	 * @param data Task to show.
	 */
	TaskEditDialog ( Task data ){
		m_Data = data;
		init();
		setDataToComponents();
	}
	
	/**
	 * Initializes frame. Defines components layout.
	 */
	private void init(){
		JPanel mainDialogPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		// initializing dialog
		setLocationRelativeTo(new JFrame());
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		add(mainDialogPanel);
		setModal(true);
						
		// Name edit panel
		JLabel name = new JLabel("Name: ");
		m_NameTF = new JTextField();
		m_NameTF.setPreferredSize(new Dimension(WIDTH-75, 30));
		JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		namePanel.add(name);
		namePanel.add(m_NameTF);

		// Projects and Files edit Panel
		JPanel locationPane = new JPanel(new FlowLayout(FlowLayout.LEFT));
		locationPane.setOpaque(false);

		JLabel projectLabel = new JLabel("Project: ");
		JLabel folderLabel = new JLabel("Folder: ");
		
		m_ProjectsCB = new JComboBox<String>();
		m_FolderCB = new JComboBox<String>();

		locationPane.add(projectLabel);
		locationPane.add(m_ProjectsCB);
		locationPane.add(Box.createHorizontalStrut(20));
		locationPane.add(folderLabel);
		locationPane.add(m_FolderCB);

		// Description edit
		m_DescTP = new JTextPane();
		m_DescTP.setEditable(true);

		JScrollPane descPanel = new JScrollPane(m_DescTP);
		descPanel.setPreferredSize(new Dimension(WIDTH -10, 100));
		descPanel.setMaximumSize(new Dimension(WIDTH -10, 100));
		descPanel.setBorder(new EmptyBorder(new Insets(5, 4, 5, 4)));

		// Buttons
		JButton cancelB = new JButton("Cancel");
		JButton acceptB = new JButton("Accept");
		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.add(cancelB);
		buttonPanel.add(Box.createHorizontalStrut(WIDTH/2 + 20));
		buttonPanel.add(acceptB);

		cancelB.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
			}
		});

		acceptB.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				m_Data.change(m_NameTF.getText(), m_DescTP.getText(),m_FolderCB.getSelectedItem()
							.toString(), m_ProjectsCB.getSelectedItem().toString());	
				setVisible(false);
			}
		});

		// Adding to Dialog
		mainDialogPanel.add(namePanel);
		mainDialogPanel.add(locationPane);
		mainDialogPanel.add(descPanel);
		mainDialogPanel.add(buttonPanel);
	}
	
	
	/**
	 * Sets up data to components from data given in constructor.
	 */
	private void setDataToComponents(){
		//Setting title
		if(m_Data.name == "") setTitle("Edit new Task");
		else setTitle("Edit task: " + m_Data.name);
		
		m_NameTF.setText(m_Data.name);
		
		for (String item : m_Projects)
			m_ProjectsCB.addItem(item);
		m_ProjectsCB.setSelectedItem(m_Data.project);
		m_ProjectsCB.setPreferredSize(new Dimension(145,25));
		
		for (String item : m_Folders)
			m_FolderCB.addItem(item);
		m_FolderCB.setSelectedItem(m_Data.folder);
		m_FolderCB.setPreferredSize(new Dimension(145,25));
		
		m_DescTP.setText(m_Data.desc);
		
	}
	
	/**
	 * Sets up data which are showed in dialog.
	 * @param projects names.
	 * @param folders names.
	 */
	public static void setData(final String[] projects, final String[] folders){
		m_Projects = new String[projects.length + 1];
		m_Projects[0] = "";
		
		for( int i = 0; i < projects.length; i++){
			m_Projects[i+1] = projects[i];
		}
		
		m_Folders = folders;
		
		
	}
	
	
}