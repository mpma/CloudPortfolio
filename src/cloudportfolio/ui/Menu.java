package cloudportfolio.ui;

import java.util.Scanner;

import cloudportfolio.system.CloudPortfolio;

public class Menu
{
	private Scanner sc;
	private CloudPortfolio cp;
	
	public Menu() 
	{
		sc = new Scanner(System.in);
	}
	
	public void displayStartMenu()
	{
		boolean keepRunning = true;
		int val =  0;
		
		System.out.println("###\tCloudPortfolio\t###");
		
		while(keepRunning) {
			showMenu();
			val = sc.nextInt();
			
			switch(val) {
			case 1:	// create account
				
			}
		}
	}

	private void showMenu()
	{
		
	}
}
