package Cab_Booking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Cab {
	
	protected HashMap<String,String> car_availability=new HashMap<>();
	protected static HashMap<String,ArrayList<String>> cab_feedback=new HashMap<>();
	protected static HashMap<String,ArrayList<String>> driver_feedback=new HashMap<>();
	static Scanner fb=new Scanner(System.in);
	
	private String car_number;
	private String cab_type;
	private Userclass owner;
	private String price_type;
	private int fixed_price;
	private int single_day_price;
	private int range_0_100;
	private int range_100_300;
	private int range_300_600;
	private int range_600above;
	private int advance;
	private int extra_amount;
	private ArrayList<String> feedback=new ArrayList<String>();
	protected Cab(String car_number,Userclass username,String cab_type,String price_type, int one_km, int one_day, int one_hun, int hun_threehun, int three_sixhun, int sixplus,int advance_amount,int extra_amount){
		this.setCar_number(car_number);
		this.setOwner(username);	
		this.setPrice_type(price_type);
		this.setCab_type(cab_type);
		this.setFixed_price(one_km);
		this.setSingle_day_price(one_day);
		this.setRange_0_100(one_hun);
		this.setRange_100_300(hun_threehun);
		this.setRange_300_600(three_sixhun);
		this.setRange_600above(sixplus);
		this.setAdvance(advance_amount);
		this.setExtra_amount(extra_amount);
	}
		
	protected Cab(String car_number,Userclass username,String cab_type,String price_type){
		this.setCar_number(car_number);
		this.setOwner(username);	
		this.setPrice_type(price_type);
		this.setCab_type(cab_type);
	}
	
	protected static void cab_App_details() {
		
		System.out.printf("%90s","----------------\n");
		System.out.printf("%90s","| POOVIGA CABS |\n");
		System.out.printf("%90s","----------------\n\n");
	
		
		System.out.println("CAB RENTAL IN CHENNAI\n---------------------\n");
		
		System.out.println("If you're planning a trip to South India, Chennai should be on your list. Chennai, Tamilnadu's capital city, is well-known for "
				+ "its tradition, rich culture, historical sites and city life. \nThe city is located on the Bay of Bengal's Coromandel coast and the iconic"
				+ " Marina beach is the world’s second-longest beach! If you are planning a trip, it would be a great idea to get a\ncar rental from Chennai for local"
				+ " sightseeing. In the city, remember to pay a visit to the Government Museum, Vivekananda House, Victory War Memorial, and the Triplicane Big Mosque.\n"
				+ "Chennai is also known for its exquisite Hindu temples built in the ancient years, which attract visitors. For a convenient trip, book Chennai Car"
				+ " Rental services to indulge in some\nshopping in Pondy Bazaar streets.\r\n"
				+ "\r\n"
				+ "Pooviga Car Rentals is an online cab booking aggregator, with over 16 years of expertise in local and intercity car rentals. Providing reliable "
				+ "and premium services in over 2000 cities,\nPooviga Cabs is the largest chauffeur-driven car rental company in India in terms of geographical reach. "
				+ "Our expert drivers will guide you through some of the best experiences Chennai has\nto offer. Hire a car in Chennai with us to make your holidays truly memorable.");
		
		System.out.println("\n\nCAB RENTAL FARE IN CHENNAI\n---------------------------\n");
		
		System.out.println("We endeavour to provide our customers in Chennai with a cab of their choice. We have all types of cars ranging from 4-seater and 7-seater"
				+ " and big Van\ntravellers in Chennai for large groups of people. We even offer luxury cars for rent in Chennai.\n\n");
		
		System.out.println("HOW TO HIRE A CAB IN CHENNAI\n------------------------------\n");
		
		System.out.println("Booking a trip with our application is easy and fast - it only takes minutes. You can make a booking for your Chennai cab rental using any of these channels:\n\n");
		System.out.println("1) WEBSITE :  Book your ride in 3 easy steps - enter your pick up and drop details, pick your preferred Chennai car hire type, and select your payment mode\n");
		System.out.println("2) CALL CENTER : Got questions? Give us a call on 8870221789 . Our executives will assist you with your itinerary and make the booking for you.(-> Make sure that you already have an account <-)\n");
		System.out.println("3) EMAIL ENQUIRES : Drop us a mail at spooviga01@gmail.com if you have any enquiries, or if you need assistance with planning your itinerary, our executives will respond immediately.\n\n");
		
		
	}

	private static void cab_feedback() {
		ArrayList<String> new_str=new ArrayList<String>();
		new_str.add("Booked car from Pooviga cabs on online. Such a wonderful car I received, very new with only 6000 km on the clock .\nWonderful service, car was ready and waiting for me to pick up . Will definitely use them in future.\n\nKrithika .");
		cab_feedback.put("123",new_str);
//		cab_feedback.put("234","I had an amazing experience with Pooviga cars with drop in at my loaction itself . The price are affordable and cars were in great conditions.\nBest recommended self-driving service in Chennai.");	
		System.out.println("Enter cab_number you drove : ");
		String cab_num=fb.nextLine();
		System.out.print("Add your feedback : ");
		System.out.println();
		String feedback=fb.nextLine();
		if(cab_feedback.containsKey(cab_num)) {
			ArrayList<String> new_arr=cab_feedback.get(cab_num);
			new_arr.add(feedback);
		}
		else {
			ArrayList<String> new_array=new ArrayList<>();
			new_array.add(feedback);
			cab_feedback.put(cab_num, new_array);
		}
		try {
			CabApplication.cab_details.get(cab_num).getFeedback().add(feedback);
			System.out.println("Your feedback has been added");
		}
		catch(Exception e) {
			System.out.println("Cab not found");
		}
		System.out.println();
	}
	

	private static void driver_feedback() {
		ArrayList<String> new_str=new ArrayList<String>();
		new_str.add("Your Driver Karthick was very professional, polite and well behaved. It was a very comfortable and hassle free drive for me and my family. ");
		driver_feedback.put("Karthick",new_str);
//		driver_feedback.put("Selvaraj","An exclusive professional driver (Selvaraj), who can make a wonderful difference in the trip... \nWe never felt he is a driver, He took extreme care of my family. Respect towards ladies is really high. Very accommodative. \nThank you Pooviga Cabs");	
		System.out.println("Enter drivers name who drove you : ");
		String driver_name=fb.nextLine();
		System.out.print("Add your feedback : ");
		System.out.println();
		String feedback=fb.nextLine();
		if(driver_feedback.containsKey(driver_name)) {
			ArrayList<String> get=driver_feedback.get(driver_name);
			get.add(feedback);
		}
		else {
			ArrayList<String> new_array=new ArrayList<>();
			new_array.add(feedback);
			driver_feedback.put(driver_name, new_array);

		}
		try {
			CabApplication.user_detail.get(driver_name).getFeedback().add(feedback);
			System.out.println("Your feedback has been added");
		}catch(Exception e) {
			System.out.println("Driver not found");
		}
		System.out.println();
	}
	

	private static void view_feedbacks() {
		System.out.println("FEEDBACK FOR CABS : ");
		System.out.println("---------------------");
		System.out.println();
		if(cab_feedback.size()==0) {
			System.out.println("No feedbacks have been added for any cabs");
			System.out.println();
		}
		else {
			for(Map.Entry<String,ArrayList< String>> entry : cab_feedback.entrySet()) {
				for(int i=0;i<entry.getValue().size();i++) {
					System.out.println("Cab number "+entry.getKey()+" : "+entry.getValue().get(i));
					System.out.println();
				}
			}
				
		}
		if(driver_feedback.size()==0) {
			System.out.println("No feedbacks have been added for any drivers");
			System.out.println();
		}
		else {
			System.out.println();
			System.out.println("FEEDBACK FOR DRIVERS : ");
			System.out.println("---------------------");
			System.out.println();
			for(Map.Entry<String, ArrayList<String>> entry : driver_feedback.entrySet()) {
				for(int i=0;i<entry.getValue().size();i++) {
					System.out.println("Driver "+entry.getKey()+" : "+entry.getValue().get(i));
					System.out.println();
				}
				
			}
		}
		
	}
	
	protected static void feedback() {
		
		Validate validate=new Validate();
		while(true) {
			System.out.println();
			System.out.println("Choose the options below");
			System.out.println();
			System.out.println("1) Add feedback for cabs");
			System.out.println("2) Add feedback for drivers");
			System.out.println("3) View feedbacks");
			System.out.println("4) Exit / Logout");
			System.out.println();
			System.out.println("Choose your choice :");
			String ch = fb.nextLine();
			if(validate.valid(ch)) {
				switch(Integer.parseInt(ch)) {

				case 1:
					cab_feedback();
					break;
				case 2:			
					driver_feedback();
					break;
					
				case 3:
					view_feedbacks();
					break;
				case 4:
					return;			
				default:
					System.out.println("Invalid choosed option");

				}
			}
			else {
				continue;
			}
			

		}
		
		
	}

	protected static void Contact_us() {
		
		System.out.println("CUSTOMER SUPPORT");
		System.out.println("-----------------\n");
		System.out.println("ADDRESS :\n");
		System.out.println("No 9, Ground foor , 6th Cross,\nHsr Layout,Chengalpattu,\nChennai - 600018\n\n");
		System.out.println("CONTACT NO :\n");
		System.out.println("9638527418 / 7412589635\n\n");
		System.out.println("SUPPORT : support@cabservice.in");
		System.out.println();
		
		
	}

	protected String getCar_number() {
		return car_number;
	}

	protected void setCar_number(String car_number) {
		this.car_number = car_number;
	}

	protected String getCab_type() {
		return cab_type;
	}

	protected void setCab_type(String cab_type) {
		this.cab_type = cab_type;
	}

	protected Userclass getOwner() {
		return owner;
	}

	protected void setOwner(Userclass owner) {
		this.owner = owner;
	}

	protected String getPrice_type() {
		return price_type;
	}

	protected void setPrice_type(String price_type) {
		this.price_type = price_type;
	}

	protected int getFixed_price() {
		return fixed_price;
	}

	protected void setFixed_price(int fixed_price) {
		this.fixed_price = fixed_price;
	}

	protected int getSingle_day_price() {
		return single_day_price;
	}

	protected void setSingle_day_price(int single_day_price) {
		this.single_day_price = single_day_price;
	}

	protected int getRange_0_100() {
		return range_0_100;
	}

	protected void setRange_0_100(int range_0_100) {
		this.range_0_100 = range_0_100;
	}

	protected int getRange_100_300() {
		return range_100_300;
	}

	protected void setRange_100_300(int range_100_300) {
		this.range_100_300 = range_100_300;
	}

	protected int getRange_300_600() {
		return range_300_600;
	}

	protected void setRange_300_600(int range_300_600) {
		this.range_300_600 = range_300_600;
	}

	protected int getRange_600above() {
		return range_600above;
	}

	protected void setRange_600above(int range_600above) {
		this.range_600above = range_600above;
	}

	protected int getAdvance() {
		return advance;
	}

	protected void setAdvance(int advance) {
		this.advance = advance;
	}

	protected int getExtra_amount() {
		return extra_amount;
	}

	protected void setExtra_amount(int extra_amount) {
		this.extra_amount = extra_amount;
	}

	public ArrayList<String> getFeedback() {
		return feedback;
	}

	public void setFeedback(ArrayList<String> feedback) {
		this.feedback = feedback;
	}

//	protected String getFeedback() {
//		return feedback;
//	}
//
//	protected void setFeedback(String feedback) {
//		this.feedback = feedback;
//	}
//	
}
	
