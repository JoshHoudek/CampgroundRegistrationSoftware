package com.techelevator.view;

import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import com.techelevator.ReservationSystem;
import com.techelevator.campground.Campground;
import com.techelevator.campground.Reservation;

public class Menu {

	ReservationSystem ourReservation;
	String userChoice;
	long userSelectedPark;
	String parkName;
	Long[] listOfSiteIDs = new Long[5];

	public String getUserSelectionFromChoice() {

		Scanner scanner = new Scanner(System.in);

		userChoice = scanner.nextLine();
		return userChoice;

	}

	public void actualCodeToDisplayCampgroundsInSelectedPark() {
		String toMonth;
		String fromMonth;

		System.out.println(String.format("%-3s%-34s%-12s%-12s%s", "#", "Name", "Open", "Close", "Daily Fee"));

		List<Campground> listOfCampgroundsAtUsersSelectedPark = ourReservation
				.getAllCampgroundsByParkSelection(userSelectedPark);
		if (listOfCampgroundsAtUsersSelectedPark.size() > 0) {
			for (Campground campground : listOfCampgroundsAtUsersSelectedPark) {
				fromMonth = new DateFormatSymbols().getMonths()[campground.getOpen_from_mm() - 1];
				toMonth = new DateFormatSymbols().getMonths()[campground.getOpen_to_mm() - 1];
				System.out.println(String.format("%-3s%-34s%-12s%-12s%s", campground.getCampground_id() + ") ",
						campground.getCampground_name(), fromMonth, toMonth, "  $" + campground.getDaily_fee() + "0"));
			}
		} else {
			System.out.println("\n*** No results ***");
		}
	}

	public void displayAvailbleReservations(ReservationSystem myReservation, long campground_id, LocalDate from_date,
			LocalDate to_date) {

		List<Reservation> listOfAvailableReservation = myReservation.getAllAvailableReservations(campground_id,
				from_date, to_date);
		Period lengthOfStay = Period.between(from_date, to_date);
		int totalCostOfTrip = (lengthOfStay.getDays());
		System.out.println("Results Matching Your Search Criteria");
		System.out.println(String.format("%-13s%-19s%-19s%-19s%-19s%s", "Site #", "Max Occup.", "Accessible",
				"Max RV Length", "Utility", "Cost"));

		for (Reservation reservation : listOfAvailableReservation) {
			System.out.println(String.format("%-13s%-19s%-19s%-19s%-19s%s", reservation.getSite_id(),
					reservation.getMaxOccupancy(), reservation.getIsAccessible(), reservation.getMaxRVLengthString(),
					reservation.getUtilitiesString(),
					"$" + (totalCostOfTrip * reservation.getDailyCost().doubleValue()) + "0"));

			getAllSiteIDs(listOfAvailableReservation);

		}

	}

	private void getAllSiteIDs(List<Reservation> listOfAvailableReservation) {

		for (int i = 0; i < listOfAvailableReservation.size(); i++) {

			listOfSiteIDs[i] = listOfAvailableReservation.get(i).getSite_id();

		}
	}

	public void makeANewReservation(ReservationSystem myReservation, LocalDate from_date, LocalDate to_date) {
		System.out.println("What site should be reserved? __ ");

		long site_idLong = Long.parseLong(getUserSelectionFromChoice());

		for (int i = 0; i < listOfSiteIDs.length;) {
			if (listOfSiteIDs[i] == site_idLong) {
				System.out.println("What name should the reservation be under? __ ");
				String familyName = getUserSelectionFromChoice() + " Family Reservation";

				List<Reservation> completedReservation = myReservation.getConfirmationOfCreatedReservation(site_idLong,
						familyName, from_date, to_date);
				System.out.println("The reservation has been made and the confirmation ID is: "
						+ completedReservation.get(0).getReservation_id());
				break;
			} else {
				i++;
				if (i == listOfSiteIDs.length) {
					System.out.println("That site is not available. Try again.");
				}
			}
		}
	}

	public static LocalDate convertToDate(String userInput) {
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		LocalDate userDate = LocalDate.parse(userInput, dateFormat);

		return userDate;
	}
}
