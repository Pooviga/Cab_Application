package Cab_Booking;

import java.time.LocalDate;

public class Booking{
	private String cust_name;
	private Integer id;
	private String cab_number;
	private int kilometer;
	private int price;
	private String owner_name;
	private String customer_choosed_Date;
	private String cab_type;
	private String cus_price;
	private int calculated_price=0;
	private String cab_number2;
	private LocalDate from_date;
	private LocalDate to_date;
	private String decide;
	private int no_of_days;
	private String destination;
	private String loc;
	private Userclass user;
	private String cus_mail;
	private String driver_name;
	private int driver_amount;
	

	protected Booking(Integer integer, String owner_name2, String username, String cab_number3, LocalDate from_dat,
			LocalDate to_dat, String cab_types, String cus_price2, String decid, int total_price,int kilo,String destination,String driver_name,int driver_amount,String location,String cus_mail) {
		this.setId(integer);
		this.setOwner_name(owner_name2);
		this.setCust_name(username);
		this.setCab_number2(cab_number3);
		this.setFrom_date(from_dat);
		this.setTo_date(to_dat);
		this.setCab_type(cab_types);
		this.setCus_price(cus_price2);
		this.setDecide(decid);
		this.setCalculated_price(total_price);
		this.setKilometer(kilo);
		this.setDestination(destination);
		this.setDriver_name(driver_name);
		this.setDriver_amount(driver_amount);
		this.setLoc(location);
		this.setCus_mail(cus_mail);
	}

	protected Booking(String username,String location) {
		this.setCust_name(username);
		this.setLoc(location);
	}

	protected Booking(String username, LocalDate f_d, LocalDate t_d, String location, String cab_types, String cus_pricee,
			String destination2,int kilo) {
		this.setCust_name(username);
		this.setFrom_date(f_d);
		this.setTo_date(t_d);
		this.setLoc(location);
		this.setCab_type(cab_types);
		this.setCus_price(cus_pricee);
		this.setKilometer(kilo);
	}

	protected Booking(Userclass loggedin_user,String cust_mail, LocalDate from_date, LocalDate to_date, String location) {
		this.setCust_name(loggedin_user.getUsername());
		this.setFrom_date(from_date);
		this.setTo_date(to_date);
		this.setLoc(location);
		this.setCus_mail(cust_mail);
	}

	protected String getCust_name() {
		return cust_name;
	}

	protected void setCust_name(String cust_name) {
		this.cust_name = cust_name;
	}

	protected Integer getId() {
		return id;
	}

	protected void setId(Integer id) {
		this.id = id;
	}

	protected String getCab_number() {
		return cab_number;
	}

	protected void setCab_number(String cab_number) {
		this.cab_number = cab_number;
	}

	protected int getKilometer() {
		return kilometer;
	}

	protected void setKilometer(int kilometer) {
		this.kilometer = kilometer;
	}

	protected int getPrice() {
		return price;
	}

	protected void setPrice(int price) {
		this.price = price;
	}

	protected String getOwner_name() {
		return owner_name;
	}

	protected void setOwner_name(String owner_name) {
		this.owner_name = owner_name;
	}

	protected String getCustomer_choosed_Date() {
		return customer_choosed_Date;
	}

	protected void setCustomer_choosed_Date(String customer_choosed_Date) {
		this.customer_choosed_Date = customer_choosed_Date;
	}

	protected String getCab_type() {
		return cab_type;
	}

	protected void setCab_type(String cab_type) {
		this.cab_type = cab_type;
	}

	protected String getCus_price() {
		return cus_price;
	}

	protected void setCus_price(String cus_price) {
		this.cus_price = cus_price;
	}

	protected int getCalculated_price() {
		return calculated_price;
	}

	protected void setCalculated_price(int calculated_price) {
		this.calculated_price = calculated_price;
	}

	protected String getCab_number2() {
		return cab_number2;
	}

	protected void setCab_number2(String cab_number2) {
		this.cab_number2 = cab_number2;
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

	protected String getDecide() {
		return decide;
	}

	protected void setDecide(String decide) {
		this.decide = decide;
	}

	protected int getNo_of_days() {
		return no_of_days;
	}

	protected void setNo_of_days(int no_of_days) {
		this.no_of_days = no_of_days;
	}

	protected String getDestination() {
		return destination;
	}

	protected void setDestination(String destination) {
		this.destination = destination;
	}

	protected String getLoc() {
		return loc;
	}

	protected void setLoc(String loc) {
		this.loc = loc;
	}

	protected Userclass getUser() {
		return user;
	}

	protected void setUser(Userclass user) {
		this.user = user;
	}

	protected String getCus_mail() {
		return cus_mail;
	}

	protected void setCus_mail(String cus_mail) {
		this.cus_mail = cus_mail;
	}

	protected String getDriver_name() {
		return driver_name;
	}

	protected void setDriver_name(String driver_name) {
		this.driver_name = driver_name;
	}

	protected int getDriver_amount() {
		return driver_amount;
	}

	protected void setDriver_amount(int driver_amount) {
		this.driver_amount = driver_amount;
	}
	


	

	
}