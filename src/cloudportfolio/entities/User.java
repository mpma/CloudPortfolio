package cloudportfolio.entities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import cloudportfolio.system.Account;
import cloudportfolio.system.CloudPortfolio;
import cloudportfolio.utilities.Uploader;

public class User
{
	
	private String name;
	private long id;
	private Account acc;
	private File userDir;
	private List<File> files;
	private Uploader uploader;
	
	private Scanner sc = new Scanner(System.in);
	
	public User(String name, long id, File userDir)
	{
		this.name = name;
		this.id = id;
		this.userDir = userDir;
		
		files = new ArrayList<File>();
		acc = new Account(this.name);
	}
	
	private File readFile(File f)
	{
		return null;
	}
	
	public void requestAccess(File f)
	{
		
	}
	
	public void read(File f) throws IOException
	{
		String line;
		BufferedReader br = new BufferedReader(new FileReader(f));
		while((line = br.readLine()) != null)
		{
			System.out.println(line);

		}
	}
	
	public void write(File f)
	{
		File file = new File(userDir + "\\" + f.getName());
		f.renameTo(file);
		files.add(file);
	}
	
	public void listFiles()
	{
		File[] listofFiles = userDir.listFiles();
		String filer;

		for(int i = 0; i < listofFiles.length; i++) {
			if(listofFiles[i].isFile()) {
				filer = listofFiles[i].getName();
				System.out.println(filer);
			}

			else if(listofFiles[i].isDirectory()) {
				filer = listofFiles[i].getName();
				System.out.println(filer+ "\\");
			}
		}
	}
	
	public void move(File oldFile, File newFile)
	{
		try {
			if(oldFile.renameTo(newFile)) {
				System.out.println("File was moved successfully!");
			} else {
				System.out.println("File failed to move!");
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean delete(File f)
	{
		try {
			if(f.delete()) {
				System.out.println(f.getName() + " deleted");
			} else {
				System.out.println("Delete operation failed.");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void deleteAccount()
	{
		
	}
	
	public String getName() { return name; }
	
	public long getID() { return id; }

}
