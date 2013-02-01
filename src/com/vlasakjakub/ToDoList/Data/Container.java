package com.vlasakjakub.ToDoList.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents container of tasks.
 *
 */
public abstract class Container implements Serializable{
	private static final long serialVersionUID = 1L;
	
	protected String m_Name;
	protected Collection<Task> m_tasks;
	
	/**
	 * Initializes tasks.
	 */
	protected void Collection(){
		m_tasks = new ArrayList<Task>();
	}
	
	/**
	 * Initializes tasks from given parameter.
	 * @param tasks 
	 */
	public void initTasks(Collection<Task> tasks){
		if(tasks == null)
			return;
		m_tasks = tasks;
	}
	
	/**
	 * Adds new single task.
	 * @param task
	 */
	public void addTask(Task task){
		if(m_tasks == null ) m_tasks = new ArrayList<Task>();
		m_tasks.add(task);	
	}
	
	/**
	 * Removes single task.
	 * @param task
	 */
	public void deleteTask(Task task){
		m_tasks.remove(task);
	}
	
	/**
	 * Getter for tasks.
	 * @return Tasks.
	 */
	public Collection<Task> getTasks(){
		return m_tasks;
	}
	
	/**
	 * Gets tasks count.
	 * @return 0 if no tasks else tasks count.
	 */
	public int getTasksCount(){
		if(m_tasks == null) return 0;
		return m_tasks.size();
	}
	
	/**
	 * Getter for name.
	 * @return name.
	 */
	public String getName(){
		return m_Name;
	}
	
	/**
	 * Sets up name. Controls if name is in correct form (Length).
	 * @param newName of the object.
	 * @return true if name is ok or false.
	 */
	public boolean setName(String newName){
		String input = newName.trim();		
		if(input.length() < 1 || input.length() > 30)
			return false;
		m_Name = input;
		return true;
	}
		
}
