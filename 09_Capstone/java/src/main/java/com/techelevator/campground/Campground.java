package com.techelevator.campground;

import java.math.BigDecimal;
import java.util.Date;

public class Campground {

	private Long campground_id;
	private Long park_id;
	private String campground_name;
	private int open_from_mm;
	private int open_to_mm;
	private BigDecimal daily_fee;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public Long getCampground_id() {
		return campground_id;
	}
	public void setCampground_id(Long campground_id) {
		this.campground_id = campground_id;
	}
	public Long getPark_id() {
		return park_id;
	}
	public void setPark_id(Long park_id) {
		this.park_id = park_id;
	}
	public String getCampground_name() {
		return campground_name;
	}
	public void setCampground_name(String campground_name) {
		this.campground_name = campground_name;
	}
	public int getOpen_from_mm() {
		return open_from_mm;
	}
	public void setOpen_from_mm(int open_from_mm) {
		this.open_from_mm = open_from_mm;
	}
	public int getOpen_to_mm() {
		return open_to_mm;
	}
	public void setOpen_to_mm(int open_to_mm) {
		this.open_to_mm = open_to_mm;
	}
	public BigDecimal getDaily_fee() {
		return daily_fee;
	}
	public void setDaily_fee(BigDecimal daily_fee) {
		this.daily_fee = daily_fee;
	}
	
	
}
