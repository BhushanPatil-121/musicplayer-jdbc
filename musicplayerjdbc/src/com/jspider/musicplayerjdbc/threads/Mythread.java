package com.jspider.musicplayerjdbc.threads;

import java.util.Scanner;

public class  Mythread implements Runnable{
	 private static int userInput = -1;
	    private Scanner scanner;
	    
	    public Mythread(Scanner scanner) {
	        this.scanner = scanner;
	    }
	    
	    public void run() {
	        while(true) {
	            System.out.print("Enter a number (0 to exit): ");
	            userInput = scanner.nextInt();
	            if (userInput==0) {
	            	System.out.println("\nGoing Back..Please Wait....\n");
	            	try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
	        }
	    }
	    
	    public static int getUserInput() {
	        return userInput;
	    }
	
}
