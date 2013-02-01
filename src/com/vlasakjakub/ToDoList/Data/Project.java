package com.vlasakjakub.ToDoList.Data;

/**
 * Represents Project container.
 *
 */
public class Project extends Container{
	private static final long serialVersionUID = 1L;
	
	public String desc;	/**< Project description.*/
	
	/**
	 * Initializes data from another Project.
	 * @param input Project to copy from.
	 */
	public Project( Project input ){
		super();
		m_Name = input.getName();
		desc = input.desc;
	}
	
	/**
	 * Initializes data.
	 * @param name
	 * @param desc Project description.
	 */
	public Project(String name, String desc) {
		super();
		if(!setName(name))
			this.m_Name = "No Name";
		this.desc = desc;
	}
	
	
}