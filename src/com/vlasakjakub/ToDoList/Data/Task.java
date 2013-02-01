package com.vlasakjakub.ToDoList.Data;

import java.io.Serializable;


/**
 * Represents task.
 *
 */
public class Task implements Serializable{
	private static final long serialVersionUID = 1L;
	
	public String name, desc, folder, project; 
	public boolean done;
	
	/**
	 * Initializes data.
	 */
	public Task(){
		name = desc = folder = project = "";
		done = false;
	}
	
	/**
	 * Initializes data.
	 * @param gName Task's name.
	 * @param gDesc	Task's description.
	 * @param gFolder Folder in which task is.
	 * @param gProject Project in which task is.
	 */
	public Task(String gName,String gDesc,String gFolder,String gProject){
		name = gName;
		desc = gDesc;
		folder = gFolder;
		project = gProject;
		done = false;
	}
	
	/**
	 * Changes all data.
	 * @param gName Task's name.
	 * @param gDesc	Task's description.
	 * @param gFolder Folder in which task is.
	 * @param gProject Project in which task is.
	 */
	public void change(String gName,String gDesc,String gFolder,String gProject){
		name = gName;
		desc = gDesc;
		folder = gFolder;
		project = gProject;
	}
	
	/**
	 * Finds out if main data is empty.
	 * @return true if is empty or false.
	 */
	public boolean isEmpty(){
		if(name.isEmpty() && desc.isEmpty()) return true;
		return false;
	}
	

}


