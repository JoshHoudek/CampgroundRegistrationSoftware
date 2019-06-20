package com.techelevator.campground;

import java.time.LocalDate;
import java.util.Date;

public class Park {
	
	private Long park_id;
	private String park_name;
	private String park_location;
	private Date establish_date;
	private int park_area;
	private int park_visitors;
	private String park_description;
	
	
	
	
	
	
	
	
	
	//////GETTERS AND SETTERS//////////
	
	public Long getPark_id() {
		return park_id;
	}
	public void setPark_id(Long park_id) {
		this.park_id = park_id;
	}
	public String getPark_name() {
		return park_name;
	}
	public void setPark_name(String park_name) {
		this.park_name = park_name;
	}
	public String getPark_location() {
		return park_location;
	}
	public void setPark_location(String park_location) {
		this.park_location = park_location;
	}
	public Date getEstablish_date() {
		return establish_date;
	}
	public void setEstablish_date(Date establish_date) {
		this.establish_date = establish_date;
	}
	public int getPark_area() {
		return park_area;
	}
	public void setPark_area(int park_area) {
		this.park_area = park_area;
	}
	public int getPark_visitors() {
		return park_visitors;
	}
	public void setPark_visitors(int park_visitors) {
		this.park_visitors = park_visitors;
	}
	public String getPark_description() {
		return park_description;
	}
	public void setPark_description(String park_description) {
		this.park_description = park_description;
	}
	
	

}
