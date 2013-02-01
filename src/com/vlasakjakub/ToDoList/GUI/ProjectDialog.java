package com.vlasakjakub.ToDoList.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import com.vlasakjakub.ToDoList.Data.Project;


/**
 * Dialog which represents Project. Describes Project's name and description. 
 *
 */
public class ProjectDialog extends JDialog{
	private static final long serialVersionUID = 1L;
	
	private static final int WIDTH = 400;
	private static final int HEIGHT = 220;
	
	private Project m_Project;
	private Collection<String> m_ExistProjects;
	private JTextField m_NameTF;
	private JTextPane m_DescTP;
	private boolean m_HaveValues;
	
	/**
	 * Initializes  all values.
	 * @param title window's.
	 */
	ProjectDialog( String title ){
		super();		
		setTitle(title);
		init();
		m_Project = new Project("","");
		m_HaveValues = false;
		m_ExistProjects = new ArrayList<String>();
	}
	
	/**
	 * Sets up frame and components.
	 */
	protected void init(){
		JPanel mainDialogPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		
		setSize( WIDTH, HEIGHT );
		setModal(true);
		setResizable(false);
		add(mainDialogPanel);
		setModal(true);
		setLocationRelativeTo(new JFrame());
		
		JLabel name = new JLabel("Name: ");
		m_NameTF = new JTextField();
		m_NameTF.setPreferredSize(new Dimension(WIDTH-75, 30));
		JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		namePanel.add(name);
		namePanel.add(m_NameTF);
		
		
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
		buttonPanel.add(Box.createHorizontalStrut(WIDTH/2 - 10));
		buttonPanel.add(acceptB);

		cancelB.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
			}
		});

		acceptB.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(m_ExistProjects.contains(m_NameTF.getText())){
					m_NameTF.setBackground(Color.RED);
					return;
				}
				if(!m_Project.setName(m_NameTF.getText())){
					m_NameTF.setBackground(Color.RED);
					return;
				}
				m_Project.desc = m_DescTP.getText();
				setVisible(false);
				m_HaveValues = true;
			}
		});
		
		mainDialogPanel.add(namePanel);
		mainDialogPanel.add(descPanel);
		mainDialogPanel.add(buttonPanel);
		
		
	}
	/**
	 * Sets up Project list which user sees in dialog.
	 * @param projects collection.
	 */
	public void setProjects(Collection<String> projects){
		m_ExistProjects = projects;
	}
	
	/**
	 * Returns values which user put in.
	 * @return Project or null. Depends if user accepts or cancel dialog.
	 */
	public Project getValues(){
		if(m_HaveValues)
			return m_Project;
		else
			return null;
	}
	
}