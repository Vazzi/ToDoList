package com.vlasakjakub.ToDoList.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import com.vlasakjakub.ToDoList.Data.Task;


/**
 * Describes task in GUI form.
 *
 */
public class TaskPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	public static TasksPanel parent; /**< Parent component.*/
	
	private final static int DESC_HEIGHT = 100;
	private final static int MAIN_HEIGHT = 30;
	private final static int MAX_WIDTH = 2000;
	private int m_Height = MAIN_HEIGHT;

	private JScrollPane m_DescPane;
	private JLabel m_NameL, m_OpenDescL, m_EditL, m_FileNameL;
	private JCheckBox m_DoneChB;
	private JTextPane m_DescTP; 
	private Task m_Data;
	
	/**
	 * Initializes data and call methods to set panel.
	 * @param gData Task.
	 */
	TaskPanel(Task gData) {
		super();
		m_Data = gData;
		init();
		refreshData();
	}

	/**
	 * Sets Panel look.
	 */
	private void init() {
		this.setPreferredSize(new Dimension(0, m_Height));
		this.setMaximumSize(new Dimension(MAX_WIDTH, m_Height));
		BoxLayout boxLayout = new BoxLayout(this, BoxLayout.Y_AXIS);
		this.setLayout(boxLayout);
		this.setBorder(new RoundedCornerBorder());

		this.add(setMainPanel());
		this.add(setDescPanel());
	}

	/**
	 * Change color of task according to if task is Today task.
	 */
	private void today() {
		
		if (m_Data.folder.equals("Today")) {
			setBackground(new Color(255, 255, 51));
		} else {
			setBackground(new Color(238, 238, 238));
		}
	}
	
	/**
	 * Sets up GUI containers. Main panel which contains name, project/folder...
	 * @return Main panel.
	 */
	private JPanel setMainPanel() {
		JPanel main = new JPanel(new FlowLayout(FlowLayout.LEFT));
		main.setOpaque(false);
		main.setMaximumSize(new Dimension(MAX_WIDTH, MAIN_HEIGHT));
		main.setPreferredSize(new Dimension(MAX_WIDTH, MAIN_HEIGHT));
		main.setMinimumSize(new Dimension(0, MAIN_HEIGHT));

		m_DoneChB = new JCheckBox();
		
		m_DoneChB.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(m_DoneChB.isSelected()) m_Data.done = true;
				else m_Data.done = false;
				setDone(m_Data.done);
			}
	    });
		
		
		m_NameL = new JLabel();
		m_FileNameL = new JLabel();
		m_FileNameL.setFont(new Font(Font.SANS_SERIF,Font.ITALIC, 11));
		m_FileNameL.setForeground(new Color(64,64,64));
		m_OpenDescL = new JLabel();
		m_OpenDescL.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/Desc.png")));
		m_OpenDescL.setToolTipText("Open/Close Description");
		m_EditL = new JLabel();
		m_EditL.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/EditSmall.png")));
		m_EditL.setToolTipText("Edit Task");
		m_EditL.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				showEditWindow();
			}
		});

		m_OpenDescL.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (getDescOpen())
					closeDesc();
				else
					openDesc();
			}
		});

		main.add(m_DoneChB);
		main.add(Box.createHorizontalGlue());
		main.add(m_FileNameL);
		main.add(Box.createHorizontalGlue());
		main.add(m_NameL);
		main.add(Box.createHorizontalGlue());
		main.add(m_OpenDescL);
		main.add(m_EditL);

		return main;

	}

	/**
	 * Sets up GUI containers. Description panel which contains description. Firstly is set to visible false.
	 * @return Description panel.
	 */
	private JScrollPane setDescPanel() {
		m_DescTP = new JTextPane();
		m_DescTP.setEditable(false);
		m_DescTP.setBorder(new EmptyBorder(new Insets(5, 4, 5, 4)));
		m_DescTP.setOpaque(false);

		m_DescPane = new JScrollPane(m_DescTP);
		m_DescPane.setMinimumSize(new Dimension(0, DESC_HEIGHT));
		m_DescPane.setMaximumSize(new Dimension(MAX_WIDTH, DESC_HEIGHT));
		m_DescPane.setBorder(new EmptyBorder(new Insets(0, 10, 10, 10)));
		m_DescPane.setOpaque(false);
		m_DescPane.setVisible(false);
		return m_DescPane;
	}

	/**
	 * Opens description part. Sets it visible and resizes task panel.
	 */
	private void openDesc() {
		m_DescPane.setVisible(true);
		m_Height = DESC_HEIGHT + MAIN_HEIGHT;
		this.setMinimumSize(new Dimension(0, m_Height));
		this.setPreferredSize(new Dimension(this.getSize().width, m_Height));
		this.setMaximumSize(new Dimension(MAX_WIDTH, m_Height));
		this.revalidate();
		this.repaint();

	}

	/**
	 * Closes description part. Sets it invisible and resizes task panel.
	 */
	private void closeDesc() {
		m_DescPane.setVisible(false);
		m_Height = MAIN_HEIGHT;
		this.setMinimumSize(new Dimension(0, m_Height));
		this.setPreferredSize(new Dimension(this.getSize().width, m_Height));
		this.setMaximumSize(new Dimension(MAX_WIDTH, m_Height));
		this.revalidate();
		this.repaint();
	}

	/**
	 * Checks if description part of task is visible or not.
	 * @return description part visibility.
	 */
	private boolean getDescOpen() {
		return m_DescPane.isVisible();
	}

	/**
	 * Resets data and components values according to actual data.
	 */
	private void refreshData() {
		m_NameL.setText(m_Data.name);
		m_DescTP.setText(m_Data.desc);
		String text;
		if(!parent.isProject())
			text = m_Data.project;
		else
			text = m_Data.folder;
				
		if(text.isEmpty()) m_FileNameL.setText("");
		else m_FileNameL.setText(text + ": ");
		
		today();
		if(m_Data.done){ m_DoneChB.setSelected(true); setDone(true);}
		else m_DoneChB.setSelected(false);
		if(m_Data.desc.equals("")) m_OpenDescL.setVisible(false);
		else m_OpenDescL.setVisible(true);
		if(getDescOpen() && m_Data.desc.equals("")) closeDesc();
	}

	/**
	 * Shows dialog to edit task. If task has been edited refreshes data and 
	 * checks if is still in correct Folder/Project. If not hides itself. 
	 */
	private void showEditWindow() {
		TaskEditDialog editDialog = new TaskEditDialog(m_Data);
		editDialog.setVisible(true);
		refreshData();
		if(parent.isProject()){
			if(!m_Data.project.equals(parent.m_Content.getName()))
				setVisible(false);
		}else{
			if(!m_Data.folder.equals(parent.m_Content.getName()))
				setVisible(false);
		}
	}
	
	/**
	 * Changes task's color depends on if it's done or not.
	 * @param done 
	 */
	private void setDone(boolean done){
		if(done){
			setBackground(new Color(208, 255, 200));
		}else
			today();
	}
}
