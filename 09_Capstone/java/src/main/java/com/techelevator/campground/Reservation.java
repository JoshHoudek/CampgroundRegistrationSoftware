package com.techelevator.campground;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class Reservation{

	private long reservation_id;
	private long campground_id;
	private long site_id;
	private String name;
	private LocalDate from_date;
	private LocalDate to_date;
	private LocalDate create_date;
	
	
	
	
	/**
	 * @return the reservation_id
	 */
	public long getReservation_id() {
		return reservation_id;
	}
	/**
	 * @param reservation_id the reservation_id to set
	 */
	public void setReservation_id(long reservation_id) {
		this.reservation_id = reservation_id;
	}
	/**
	 * @return the campground_id
	 */
	public long getCampground_id() {
		return campground_id;
	}
	/**
	 * @param campground_id the campground_id to set
	 */
	public void setCampground_id(long campground_id) {
		this.campground_id = campground_id;
	}
	/**
	 * @return the site_id
	 */
	public long getSite_id() {
		return site_id;
	}
	/**
	 * @param site_id the site_id to set
	 */
	public void setSite_id(long site_id) {
		this.site_id = site_id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the from_date
	 */
	public LocalDate getFrom_date() {
		return from_date;
	}
	/**
	 * @param from_date the from_date to set
	 */
	public void setFrom_date(LocalDate from_date) {
		this.from_date = from_date;
	}
	/**
	 * @return the to_date
	 */
	public LocalDate getTo_date() {
		return to_date;
	}
	/**
	 * @param to_date the to_date to set
	 */
	public void setTo_date(LocalDate to_date) {
		this.to_date = to_date;
	}
	/**
	 * @return the create_date
	 */
	public LocalDate getCreate_date() {
		return create_date;
	}
	/**
	 * @param create_date the create_date to set
	 */
	public void setCreate_date(LocalDate create_date) {
		this.create_date = create_date;
	}
	
}

	
	
	
	