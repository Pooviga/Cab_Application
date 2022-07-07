package Cab_Booking;

import java.util.ArrayList;

public class Userclass {

	private String name;
	private String username;
	private String password;
	private usertype role;
	private int wallet;
	private String mail_id;
	private int age;
	private int driver_price_per_day;
	private ArrayList<String> feedback=new ArrayList<String>();
	private int bonus_amount;
	protected Userclass(String name,String username,String password,usertype role,int wallet,String mail_id,int age,int driver_price,int bonus_amount){
		this.setName(name);
		this.setUsername(username);
		this.setPassword(password);
		this.setRole(role);
		this.setWallet(wallet);
		this.setMail_id(mail_id);
		this.setAge(age);
		this.setDriver_price_per_day(driver_price);
		this.setBonus_amount(bonus_amount);
	}
	protected Userclass(String name,String username,String password,usertype role,int wallet,String mail_id,int age,int driver_price){
		this.setName(name);
		this.setUsername(username);
		this.setPassword(password);
		this.setRole(role);
		this.setWallet(wallet);
		this.setMail_id(mail_id);
		this.setAge(age);
		this.setDriver_price_per_day(driver_price);
	}
	protected Userclass(String name,String username,String password,usertype role){
		this.setName(name);
		this.setUsername(username);
		this.setPassword(password);
		this.setRole(role);
	}
	protected String getName() {
		return name;
	}
	protected void setName(String name) {
		this.name = name;
	}
	protected String getUsername() {
		return username;
	}
	protected void setUsername(String username) {
		this.username = username;
	}
	protected String getPassword() {
		return password;
	}
	protected void setPassword(String password) {
		this.password = password;
	}
	protected usertype getRole() {
		return role;
	}
	protected void setRole(usertype role) {
		this.role = role;
	}
	protected int getWallet() {
		return wallet;
	}
	protected void setWallet(int wallet) {
		this.wallet = wallet;
	}
	protected String getMail_id() {
		return mail_id;
	}
	protected void setMail_id(String mail_id) {
		this.mail_id = mail_id;
	}
	protected int getAge() {
		return age;
	}
	protected void setAge(int age) {
		this.age = age;
	}
	protected int getDriver_price_per_day() {
		return driver_price_per_day;
	}
	protected void setDriver_price_per_day(int driver_price_per_day) {
		this.driver_price_per_day = driver_price_per_day;
	}
	protected ArrayList<String> getFeedback() {
		return feedback;
	}
	protected void setFeedback(ArrayList<String> feedback) {
		this.feedback = feedback;
	}
	protected int getBonus_amount() {
		return bonus_amount;
	}
	protected void setBonus_amount(int bonus_amount) {
		this.bonus_amount = bonus_amount;
	}



}
