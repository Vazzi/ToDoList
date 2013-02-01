package com.vlasakjakub.ToDoList.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Database of tasks, projects and default folders.
 *
 */
public class Registr implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private static Collection<Folder> m_Folders;  /**< Contains default folders*/
	private Collection<Project> m_Projects;
	private Collection<Task> m_Tasks;
	
	/**
	 * Initializes data.
	 */
	public Registr(){
		init();
		m_Projects = new ArrayList<Project>();
		m_Tasks = new ArrayList<Task>();
	}
	
	
	/**
	 * Initializes folder. Adding default folders.
	 */
	public void init(){
		m_Folders = new ArrayList<Folder>();
	
		m_Folders.add(new Folder("Inbox"));
		m_Folders.add(new Folder("Today"));
		m_Folders.add(new Folder("Next"));
		m_Folders.add(new Folder("Someday/Maybe"));		
	}
	
	
	/**
	 * Gets all folders names.
	 * @return folders names in string.
	 */
	public String[] getFoldersNames(){
		Collection<String> listNames = new ArrayList<String>();
		for( Folder folder : m_Folders){
			listNames.add(folder.m_Name);
		}
		
		String[] names = new String[listNames.size()]; 
		names = listNames.toArray(names);
		return names;		
	}
	
	/**
	 * Gets all projects names.
	 * @return projects names.
	 */
	public String[] getProjectsNames(){
		Collection<String> listNames = new ArrayList<String>();
		for( Project project : m_Projects){
			listNames.add(project.m_Name);
		}
		
		String[] names = new String[listNames.size()]; 
		names = listNames.toArray(names);
		return names;		
	}
	
	/**
	 * Gets tasks of chosen Folder.
	 * @param folderName 
	 * @return tasks collection
	 */
	public Collection<Task> getTasksFolder(String folderName) {
		Collection<Task> tasks = new ArrayList<Task>();
		for(Task item : m_Tasks){
			if(item.folder.equals(folderName)) tasks.add(item);
		}
		if(tasks.size() == 0) return null;
		return tasks;
	}
	
	/**
	 * Gets tasks of chosen Project.
	 * @param projectName 
	 * @return tasks collection
	 */
	public Collection<Task> getTasksProject(String projectName) {
		Collection<Task> tasks = new ArrayList<Task>();
		for(Task item : m_Tasks){
			if(item.project.equals(projectName)) tasks.add(item);
		}
		if(tasks.size() == 0) return null;
		return tasks;
	}
	
	/**
	 * Deletes single task.
	 * @param item to delete.
	 */
	public void removeTask(Task item){
		m_Tasks.remove(item);
	}
	
	/**
	 * Deletes Folder with all tasks this folder contains.
	 * @param item  Folder
	 */
	public void removeFolder(Folder item){
		Collection<Task> delTasks = new ArrayList<Task>();
		for ( Task task : m_Tasks ){
			if( task.folder.equals(item.m_Name) ) delTasks.add(task);
		}
		
		for(Task task: delTasks){
			m_Tasks.remove(task);
		}
		m_Folders.remove(item);
	}
	
	/**
	 * Deletes Project with all tasks this folder contains.
	 * @param item - Project
	 */
	public void removeProject(Project item){
		Collection<Task> delTasks = new ArrayList<Task>();
		for ( Task task : m_Tasks ){
			if( task.project.equals(item.m_Name) ) delTasks.add(task);
		}
		
		for(Task task: delTasks){
			m_Tasks.remove(task);
		}
		m_Projects.remove(item);
	}
	
	/**
	 * Counts tasks in Today Folder.
	 * @return Integer counts of tasks in today.
	 */
	public int getTodayCount(){
		int count = 0;
		for ( Task task : m_Tasks ){
			if( task.folder.equals("Today") && !task.done) count++;
		}
		return count;
	}
	
	/**
	 * Deletes all tasks that are tag as Done.
	 * @return true if removes some tasks else false.
	 */
	public boolean cleanDoneTasks(){
		boolean removedAny = false; 
		Collection<Task> tmp = new ArrayList<Task>();
		
		for( Task task: m_Tasks ){
			if(task.done) {
				tmp.add(task);
				removedAny = true;
			}
		}
		
		for(Task task : tmp){
			removeTask(task);
		}
		return removedAny;
	}
	
	/**
	 * Adds multiple tasks.
	 * @param newTasks collection of new tasks.
	 */
	public void addTasks(Collection<Task> newTasks){
		for( Task task : newTasks){
			m_Tasks.add(task);
		}
	}
	
	/**
	 * Adds Project and all his tasks.
	 * @param newProject 
	 */
	public void addProject( Project newProject){
		m_Projects.add(newProject);
		if(newProject.getTasksCount() > 0){
			for(Task p : newProject.getTasks()){
				m_Tasks.add(p);
			}
		}
	}
	
	/**
	 * Finds and returns Project by name.
	 * @param name of Project.
	 * @return Project or null if finds nothing.
	 */
	public Project getProject(String name){
		for ( Project p : m_Projects){
			if(p.getName().equals(name)) return p;
		}
		return null;
	}
	
	/**
	 * Finds if Project is in database.
	 * @param in searched Project.
	 * @return if it finds or not.
	 */
	public boolean projectIsIn(Project in){
		for( Project p : m_Projects){
			if(p.getName().equals(in.getName())) return true;
		}
		return false;
	}


	
}