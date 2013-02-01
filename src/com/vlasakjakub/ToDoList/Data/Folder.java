package com.vlasakjakub.ToDoList.Data;

/**
 * Represents folder container.
 *
 */
public class Folder extends Container{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Initializes data.
	 * @param name
	 */
	public Folder (String name){
		super();
		if(!setName(name))
			this.m_Name = "No Name";

	}
	
}