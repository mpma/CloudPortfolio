package cloudportfolio.ui;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import cloudportfolio.entities.User;
import cloudportfolio.system.CloudPortfolio;

public class Menu
{
	private Scanner sc;
	private CloudPortfolio cp;
	
	private boolean keepRunning;
	private int val;
	
	private User user;
	private File file;
	
	public Menu(CloudPortfolio cp) 
	{
		sc = new Scanner(System.in);
		
		keepRunning = false;
		val = 0;
		
		this.cp = cp;
	}
	
	public void displayStartMenu() throws IOException
	{
		keepRunning = true;
		val =  0;
		
		System.out.println("###\tCloudPortfolio\t###");
		
		while(keepRunning) {
			startOptions();
			val = sc.nextInt();
			
			switch(val) {
			case 1:	// create account
				cp.promptCredentials();
				cp.createAccount();
				break;
				
			case 2: // log in
				cp.promptCredentials();
				if(cp.logIn()) {
					System.out.println("Log in successful!");
					user = cp.getCurrentUser();
					displayUserMenu();
				} else
					System.err.println("Did not find user");
				break;
				
			case 3: // list users
				cp.listUsers();
				break;
				
			case 4: // delete user
				break;
				
			case 5: // exit
				System.exit(0);
				break;
			}
		}
	}
	
	public void displayUserMenu() throws IOException
	{
		keepRunning = true;
		val = 0;
		
		while(keepRunning) {
			userOptions();
			val = sc.nextInt();
			
			switch(val) {
			case 1: // upload file
				System.out.print("Select file: ");
				//file = new File(sc.next());
				user.write(new File(sc.next()));
				break;
			
			case 2: // download file
				user.listFiles();
				System.out.print("\nSelect file: ");
				user.read(new File(user.getUserDir() + "\\" + sc.next()));
				break;
				
			case 3: // list files
				user.listFiles();
				break;
				
			case 4: // delete files
				user.listFiles();
				System.out.print("Select file for deletion: ");
				user.delete(new File(user.getUserDir() + "\\" + sc.next()));
				break;
				
			case 5: // move files
				user.listFiles();
				System.out.print("Select file to move: ");
				File oldFile = new File(user.getUserDir() + "\\" + sc.next());
				System.out.println("New location: ");
				File newFile = new File(sc.next());
				user.move(oldFile, newFile);
				break;
				
			case 6: // create folder
				System.out.println("Name new folder: ");
				cp.createFolder(new File(user.getUserDir() + "\\" + sc.next()));
				break;
				
			case 7: // exit
				System.exit(0);
				break;
			}
		}
	}

	private void startOptions()
	{
		System.out.println("\n1. Create account\n"
				+ "2. Log in\n"
				+ "3. List users\n"
				+ "4. Delete user\n"
				+ "5. Exit");
		System.out.print("Select option: ");
	}
	
	private void userOptions()
	{
		System.out.println("\n1. Upload file\n"
				+ "2. Download file\n"
				+ "3. List files\n"
				+ "4. Delete files\n"
				+ "5. Move files\n"
				+ "6. Create folder\n"
				+ "7. Exit");
		System.out.print("Select option: ");
	}
}
