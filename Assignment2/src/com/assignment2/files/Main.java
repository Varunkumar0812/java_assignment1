package com.assignment2.files;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	
	static String dbmsUser = "root";
	static String dbmsPassword = "Tvarun@0812";
	static String dbmsDatabase = "sample1";
	
	//Main Function invoked when the users chooses the registration option
	//Collects all necessary details from the user while checking
	// if each attribute satisfies its necessary requirements
	//Finally inserts the record into the database
	public static void UserRegistration() {
		Scanner input = new Scanner(System.in);
		
		// An arraylist to contain all the attributes to be added
		// to the databasse table in string format
		// The attributes include :
		//    * Username
		//    * First Name
		//    * Last Name
		//    * Password
		//    * Confirm Password
		//    * Recovery email
		//    * Date of Birth
		//    * Gender
		ArrayList<String> tuple = new ArrayList<String>();
		
		
		// Username - Must be of length 6 to 30 characters
		//          - Shouldn't contain any special characters also uppercase letters
		//          - Shouldn't already exist in the database table
		//		    - Allowed characters (abcdefghijklmnopqrstuvwxyz_0123456789)
		String username = null;
				
	    while(true) {
			System.out.println("\nUsername   : ");
			username = input.next();
			
			if(username.length() <= 6 || username.length() >= 30) {
				System.out.println("\n!! Username must be of length 6 to 30 characters. !!");
			}
			else if(!Validation.usernameValidity(username)) {
				System.out.println("\n!! Username shouldn't contain special characters. !!");
			}
			else if(!Validation.usernameExists(username)) {
				System.out.println("\n!! Username already exist. Try a different one. !!");
			}
			else {
				break;
			}
		}

		tuple.add(username);
			
		
		// First Name and Last Name - Can be uppercase and lowercase alphabets and numbers
		// 							- Shouldn't contain special characters
		String firstName = null;
		String lastName = null;
			
		while(true ) {
			System.out.println("\nFirst Name : ");
			firstName = input.next();
		
			if(!Validation.nameValidity(firstName)) {
				System.out.println("\n!! First Name shouldn't contain special characters. !!");
			}
			else {
				break;
			}
		}

		tuple.add(firstName);
			
		while(true ) {
			System.out.println("\nLast Name  : ");
			lastName = input.next();
		
			if(!Validation.nameValidity(lastName)) {
				System.out.println("\n!! Last Name shouldn't contain special characters. !!");
			}
			else {
				break;
			}
		}
		
		tuple.add(lastName);
		

		// Password - Should be of minimum length 8 characters 
		//			- Should contain atleast one uppercase, number and symbol
		String password = null;
		
		while(true) {
			System.out.println("\nPassword   :");
			password = input.next();
			
			switch (Validation.passwordValidity(password)) {
				case 1 :
					System.out.println("\n!! Password should be minimum of 8 characters long. !!");
					break;
				case 2 :
					System.out.println("\n!! Password should conatin atleast one uppercase letter, number and symbol. !!");
					break;
				case 3 :
					System.out.println("\n!! Password should contain atleast one uppercase letter and number. !!");
					break;
				case 4 :
					System.out.println("\n!! Password should contain atleast one uppercase letter and symbol. !!");
					break;
				case 5 :
					System.out.println("\n!! Password should contain atleast one number and symbol. !!");
					break;
				case 6 :
					System.out.println("\n!! Password should contain atleast one uppercase letter. !!");
					break;
				case 7 :
					System.out.println("\n!! Password should contain atleast one number. !!");
					break;
				case 8 :
					System.out.println("\n!! Password should contain atleast one symbol. !!");
					break;
				default :
					break;
			}
			if (Validation.passwordValidity(password) == 9) {
				break;
			}
		}
		
		String encryptedPassword = Encryption.encrypt(password);
		
		tuple.add(encryptedPassword);
		
		
		// Confirm Password - Should be same as the Password
		String confirm_password = null;
		
		while(true) {
			System.out.println("\nConfirm Password  :");
			confirm_password = input.next();
			
			if(!confirm_password.equals(password)) {
				System.out.println("\n!! Confirm Password doesn't match the actual password !!");
			}
			else {
				break;
			}
		}
		
		
		// Recovery email - Should be present in the database table
		String recoveryEmail = null;
		
		while(true) {
			System.out.println("\nRecovery Email  : ");
			recoveryEmail = input.next();
			
			if(Validation.usernameExists(recoveryEmail)) {
				System.out.println("\n!! The given recovery email is not valid. !!");
	    	}
			else {
				break;
			}
    	}
		
	    tuple.add(recoveryEmail);
		
	    
	    // Date of Birth - Should be a valid date and in (yyyy/mm/dd) format only
		String dob = null;
		
		while(true) {
			System.out.println("\nDOB (yyyy-mm-dd)  : ");
			dob = input.next();
			
			if(!Validation.dateValidity(dob)) {
				System.out.println("\n!! The given date of birth is not valid. !!");
			}
			else {
				break;
			}
		}
		
		tuple.add(dob);
		
		
		// Gender - Can be either "male" or "female" (Case insensitive)
		String gender = null;
		
		while(true) {
			System.out.println("\nGender     : ");
			gender = input.next();
			
			if("MALE".equals(gender.toUpperCase()) || "FEMALE".equals(gender.toUpperCase())) {
				break;
			}
			else {
				System.out.println("\n!! Invalid gender. !!");
			}
		}
		
		tuple.add(gender.toUpperCase());
		
		
		int count = dbmsOperations.insertRecord(tuple);
		if(count == 0) {
			System.out.println("\n!! Registration Failed :( , try after some time. !!");
		}
		else {
			System.out.println("\n!! Registration Successfull :) !!");
		}
		
		input.close();
	}
	
	//Main function to be invoked if the user chooses the 
	// login option
	public static void UserLogin() {
		Scanner input = new Scanner(System.in);
		
		String username = null;
		
		while(true) {
			System.out.println("\nUsername   : ");
			username = input.next();
			
			if(Validation.usernameExists(username)) {
				System.out.println("\n!! Invalid Username !!");
			}
			else {
				break;
			}
		}
		
		String password = null;
		
		int count = 0;
		
		while(true && count <= 5) {
			System.out.println("\nPassword   : ");
			password = input.next();
			
			if(!Validation.loginValidity(username, password)) {
				count++;
				System.out.println("\n!! Invalid username and password. Please try again. !!");
			}
			else {
				System.out.println("\n!! Successfully logged in. !!");
				break;
			}
		}
		
		if(count > 5) {
			System.out.println("\nForgot Password (y/n)");
			String forgot = input.next();
					
			if ("Y".equals(forgot.toUpperCase())) {
				forgotPassword();
			}
			else {
				System.out.println("\n!! Please try again after some time. !!");
			}
		}
		
		input.close();
	}
	
	//Main function to be invoked if the user chooses the forgot password option
	// Gets the username and recovery email to change the password
	public static void forgotPassword() {
		Scanner input = new Scanner(System.in);
		
		String username = null;
		
		while(true) {
			System.out.println("\nUsername   : ");
			username = input.next();
			
			if(Validation.usernameExists(username)) {
				System.out.println("\n!! Invalid Username !!");
			}
			else {
				break;
			}
		}
		
		String recovery_email = null;
		String new_password = null;
		
		while(true) {
			System.out.println("\nRecovery email : ");
			recovery_email = input.next();
			
			System.out.println("\nNew Password   : ");
			new_password = input.next();
			
			if(!Validation.forgotPasswordValidity(username, recovery_email)) {
				System.out.println("\n!! Wrong recovery email. !!");
			}
			else {
				break;
			}
		}
		
		int count = dbmsOperations.updatePassword(username, new_password);
		
		if(count == 0) {
			System.out.println("\n!! Error has occured while changing password. Please try again. !!");
		}
		else {
			System.out.println("\n!! New password updated successfully. !!");
		}
		
		input.close();
	}
	
	// The actual main function - shows the menu to the user
	public static void main(String[] args) {
	    Scanner input = new Scanner(System.in);
	    
	    System.out.println("-----------------------------------------------------\n");
	    System.out.println("Enter the number to choose the options : ");
	    System.out.println("\n\t1 - Sign Up\n\t2 - Sign In\n\t3 - Exit");
	    System.out.println("\n-----------------------------------------------------");
	    System.out.println("\nEnter your choice : ");
	      
		int op = input.nextInt();
		
		switch (op) {
		     case 1 :
		         System.out.println("User Registration");
		         UserRegistration();
		         break;
		     case 2 :
		         System.out.println("User Login");
		         UserLogin();
		         break;
		     case 3 :
		      	 System.out.println("Exited successfully !!");
		       	 input.close();
		       	 System.exit(0);
		     default :
		         System.out.println("\n!! Invalid Input !!");
		         break;
		}
	}
}
