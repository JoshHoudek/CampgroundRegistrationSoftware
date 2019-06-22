package com.techelevator.view;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

import com.techelevator.campground.Park;
import com.techelevator.campground.Reservation;

public class NewMenu {

	private PrintWriter out;
	private Scanner in;

	public NewMenu(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}

	public Object getChoiceFromOptions(Object[] options) {
		Object choice = null;
		while(choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		return choice;
	}
	
	public Object getChoiceFromParkOptions(Object[] options) {
		Object choice = null;
		while(choice == null) {
			displayMainMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		return choice;
	}

	private Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		if (userInput.equals("Q")) {
			
			System.out.println("Goodbye, and thanks for visiting the National Parks!");
			
			System.exit(0);
		}
		else try {
			
			int selectedOption = Integer.valueOf(userInput);
			
			if(selectedOption <= options.length) {
				choice = options[selectedOption - 1];
			} 
			
		} catch(NumberFormatException e) {
			// eat the exception, an error message will be displayed below since choice will be null
		}
		if(choice == null) {
			out.println("\n*** "+userInput+" make a *different* selection.. ***\n");
		} 
		return choice;
	}

	private void displayMainMenuOptions(Object[] options) {
		out.println();
		for(int i = 0; i < options.length; i++) {
			int optionNum = i+1;
			Park eachPark = (Park) options[i];
			
			out.println(optionNum+") "+ eachPark.getPark_name());
		}
		out.println("Q) Quit");
		out.print("\nMake a selection: ");
		out.flush();
	}
	
	private void displayMenuOptions(Object[] options) {
		out.println();
		for(int i = 0; i < options.length; i++) {
			int optionNum = i+1;			
			out.println(optionNum+") "+ options[i]);
		}
		out.print("\nMake a selection: ");
		out.flush();
	}
	
	public void displaySearchedReservation(List<Reservation> userReservation) {
		
		if (userReservation.size() > 0) {
			System.out.println("We found " + userReservation.size() + " reservation!");
			
			System.out.println("The "+ userReservation.get(0).getName() + " is at site #" + userReservation.get(0).getSite_id() + "\n");
		} else {
			
			System.out.println("We found 0 reservations, maybe it's time to make one!");
		}

	}
	
	
	
}
