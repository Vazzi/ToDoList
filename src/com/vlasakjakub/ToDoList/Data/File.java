package com.vlasakjakub.ToDoList.Data;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;



/**
 * File operations. Read/write data to file. 
 * Implemented specially for this program.
 *
 */ 
public class File {
	final static private String DATABASE = "Database.tdl"; /**< Default file name for saving registry.*/ 
	
	/**
	 * Writes data in the default file. Saves it next to application.
	 * @param obj - data which will be written in file.
	 */
	static public void writeRegistr(Registr obj){
		ObjectOutputStream outputStream = null;
        
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(DATABASE));

            outputStream.writeObject(obj);          
            
        } catch (FileNotFoundException ex) {
            System.err.print(ex);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
	}
	
	/**
	 * Reads data from default file which is saved next to application.
	 * @return return read data or null if fails.
	 */
	static public Registr readRegistr(){
		Registr out = null;
		ObjectInputStream inputStream = null;
        try {
            inputStream = new ObjectInputStream(new FileInputStream(DATABASE));
            Object obj = null;
            
            while ((obj = inputStream.readObject()) != null) {
                
                if (obj instanceof Registr) {
	                out = (Registr)obj;
                }
            }
        } catch (EOFException ex) { 
           
        } catch (ClassNotFoundException ex) {
            System.err.print(ex);
        } catch (FileNotFoundException ex) {
       
        } catch (IOException ex) {
        	 System.err.print(ex);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return out;
        
	}
	
	/**
	 * Reads data from file.
	 * @param path to file
	 * @return Project 
	 */
	static public Project readProject(String path){
		Project out = null;
		ObjectInputStream inputStream = null;
        try {
            inputStream = new ObjectInputStream(new FileInputStream(path));
            Object obj = null;
            
            while ((obj = inputStream.readObject()) != null) {
                
                if (obj instanceof Project) {
	                out = (Project) obj;
                }
            }
         
        } catch (EOFException ex) { 
           
        } catch (ClassNotFoundException ex) {
            System.err.print(ex);
        } catch (FileNotFoundException ex) {
       
        } catch (IOException ex) {
        	 System.err.print(ex);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return out;
	}
	
	/**
	 * Writes project to specified location.
	 * @param obj project to write.
	 * @param path to save file.
	 * @return	true if saved false if any error occurred.
	 */
	static public boolean writeProject(Project obj, String path){
		ObjectOutputStream outputStream = null;
        boolean result = true;
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream(path));
            outputStream.writeObject(obj);          
        } catch (FileNotFoundException ex) {
            System.err.print(ex);
            result = false;
        } catch (IOException ex) {
            ex.printStackTrace();
            result = false;
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                result = false;
            }
        }
        return result;
	}
		
	
	
}
