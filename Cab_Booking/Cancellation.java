package Cab_Booking;

import java.time.LocalDate;

public class Cancellation {
	
	private String name_of_owner;
	private String cab_no;
	private String name_of_customer;
	private LocalDate from_date;
	private LocalDate to_date;
	private Integer booking_id;
	private long refund;
	private String driver_name;
	protected Cancellation(Integer booking_id,String name_of_owner,String cab_no,String name_of_customer,LocalDate from_date,LocalDate to_date, long refund_amount,String driver_name) {
		this.setCab_no(cab_no);
		this.setFrom_date(from_date);
		this.setName_of_customer(name_of_customer);
		this.setName_of_owner(name_of_owner);
		this.setTo_date(to_date);
		this.setBooking_id(booking_id);
		this.setRefund(refund_amount);
		this.setDriver_name(driver_name);
	}
	protected String getName_of_owner() {
		return name_of_owner;
	}
	protected void setName_of_owner(String name_of_owner) {
		this.name_of_owner = name_of_owner;
	}
	protected String getCab_no() {
		return cab_no;
	}
	protected void setCab_no(String cab_no) {
		this.cab_no = cab_no;
	}
	protected String getName_of_customer() {
		return name_of_customer;
	}
	protected void setName_of_customer(String name_of_customer) {
		this.name_of_customer = name_of_customer;
	}
	protected LocalDate getFrom_date() {
		return from_date;
	}
	protected void setFrom_date(LocalDate from_date) {
		this.from_date = from_date;
	}
	protected LocalDate getTo_date() {
		return to_date;
	}
	protected void setTo_date(LocalDate to_date) {
		this.to_date = to_date;
	}
	protected Integer getBooking_id() {
		return booking_id;
	}
	protected void setBooking_id(Integer booking_id) {
		this.booking_id = booking_id;
	}
	protected long getRefund() {
		return refund;
	}
	protected void setRefund(long refund) {
		this.refund = refund;
	}
	protected String getDriver_name() {
		return driver_name;
	}
	protected void setDriver_name(String driver_name) {
		this.driver_name = driver_name;
	}
	
}
