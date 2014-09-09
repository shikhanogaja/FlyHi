package com.flyhi.website.utils;

import java.io.File;
import java.io.IOException;

import com.flyhi.website.driver.Driver;

public class FolderManagement 
{
	public static File createDirectory() throws IOException  
    {
        File strDirectory;
        strDirectory = new File ("Result_"+Driver.TStamp+"_"+Driver.vTED);
        strDirectory.mkdir();
        return strDirectory;
    } 
	
	public void deleteDirectory()
    {
    	File directory = new File(Driver.resultPath);
    	 
    	//Make sure directory exists
    	if(!directory.exists())
    	{
           System.out.println("Directory does not exist.");
           System.exit(0);
        }
    	else
    	{
           try
           {
               delete(directory);
           }
           catch(IOException e)
           {
               e.printStackTrace();
               System.exit(0);
           }
        }
    	System.out.println("Done");
    }
    
    public static void delete(File file)throws IOException
    {
    	if(file.isDirectory())
    	{
    		//If directory is empty, then delete it
    		if(file.list().length==0)
    		{
    		   file.delete();
    		   System.out.println("Directory is deleted : " + file.getAbsolutePath());
    		}
    		else
    		{
    		   //List all the directory contents
        	   String files[] = file.list();
 
        	   for (String temp : files) 
        	   {   
        	      //Construct the file structure
        	      File fileDelete = new File(file, temp);
        	      //Recursive delete
        	     delete(fileDelete);
        	   }
 
        	   //Check the directory again, if empty then delete it
        	   if(file.list().length==0)
        	   {
           	     file.delete();
        	     System.out.println("Directory is deleted : " + file.getAbsolutePath());
        	   }
    		}
 
    	}
    	else
    	{
    		//If file, then delete it
    		file.delete();
    		System.out.println("File is deleted : " + file.getAbsolutePath());
    	}
    }

}