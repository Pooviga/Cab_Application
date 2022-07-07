package Cab_Booking;

import java.net.UnknownHostException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;  


enum usertype{
	Customer, Owner, Driver ,Temporary_drivers;
}	

public class CabApplication{

	public static Scanner sc=new Scanner(System.in);
	Validate validate=new Validate();
	private Userclass loggedin_user;
	protected static HashMap<String,Userclass> user_detail=new HashMap<>();	
	protected static HashMap<String,Cab> cab_details=new HashMap<>();   //cab_number:Cab object
	protected static HashMap<String,ArrayList<String>> references=new HashMap<>();
	protected ArrayList<Cab> available_cabs;
	protected ArrayList<Booking> bookings=new ArrayList<>(); //Object of booking class	
	protected ArrayList<Cancellation> cancellation=new ArrayList<>(); //Object of cancellation class
	private HashSet<Booking> filter=new HashSet<>(); // set of single booking
	private Integer booking_id=1;
	private LocalDate f_d=null;
    private LocalDate t_d=null;
    private String des=null;
    private int no_of_days;
    
    private LinkedList<Booking> waiting_list =new LinkedList<Booking>();


	private boolean SignUp(String name,String username, String password, usertype role,int wallet,String mail_id,int age,int driver_price,int bonus_amount) {

		if(user_detail.containsKey(username)) {	//checks if hashmap(owners) already has this username.
			System.out.println("----------------------------------------------------------------");
			System.out.println("Please use another username,Username already taken");
			System.out.println("----------------------------------------------------------------");
		}
		else {					// username , object of Owner class(which has name and passwords) is mapped.
			if(role.equals(usertype.Customer)) {
				Userclass user=new Userclass(name,username,password,role,wallet,mail_id,age,driver_price,bonus_amount);
				user_detail.put(username,user);
			}
			else {
				Userclass user=new Userclass(name,username,password,role,wallet,mail_id,age,driver_price);
				user_detail.put(username,user);
			}
			send_welcome_mail(mail_id,role,username);
			System.out.println("----------------------------------------------------------------");
			System.out.println("Your have registered successfully");
			System.out.println("----------------------------------------------------------------");
			return true;
		}
		return false;

	}
	usertype SignIn(String username, String password) {
		
		if(user_detail.get(username).getPassword().equals(password)) { //checks if the password matches with username
			System.out.println("----------------------------------------------------------------");
			System.out.println("Loggedin Successfully");
			System.out.println("----------------------------------------------------------------");
			loggedin_user=user_detail.get(username);
			return user_detail.get(username).getRole();
		}
		else {
			System.out.println("----------------------------------------------------------------");
			System.out.println("Password mismatch");
			System.out.println("----------------------------------------------------------------");
			return null;
		}
	
	}


	private void addCar_Detail() {
		String price_type=null;
		String cab_type=null;
		System.out.println();
		System.out.print("Enter your cab number : ");
		String car_number=sc.nextLine();
		
		if(car_number=="") {
			System.out.println("It is an empty string");
		}
		else if(cab_details.containsKey(car_number)) {
			System.out.println("Use another cab number , this number is already taken");
			System.out.println();
		}
		else {
			System.out.println();	
			System.out.print("Choose your pricing type : ");
			System.out.println();
			
			boolean price_bool=false;
			String pricee = null;
			while(price_bool!=true) {
				System.out.println("1. fixed price : price for 1km."+"\n"+
						"2. price for a single day."+"\n"+
						"3. price varing");
				pricee=sc.nextLine();
				price_bool=validate.validateDecision(pricee);
			}
			
			
			if(pricee.equals("1")) {
				price_type="fixed_price";
			}
			else if(pricee.equals("2")) {
				price_type="Single_day";
			}
			else if(pricee.equals("3")) {
				price_type="Variable_day";
			}
			
			
			boolean cabtype_bool=false;
			String cabs=null;
			
			while(cabtype_bool!=true) {
				System.out.println();
				System.out.println("Enter your cab type : "+"\n"+"1) 4 seater"+"\n"+"2) 5 seater"+"\n"+"3) 7 seater"+"\n"+"4) Van(>=10 seater)");
				cabs=sc.nextLine();
				cabtype_bool=validate.validateChoice(cabs);
			}	
			if(cabs.equals("1")) {
				cab_type="4 seater";
			}
			else if(cabs.equals("2")) {
				cab_type="5 seater";
			}
			else if(cabs.equals("3")) {
				cab_type="7 seater";
			}
			else if(cabs.equals("4")) {
				cab_type="Van model";
			}
			int one_km;
			while(true) {
				try {
					System.out.println();
					System.out.println("Enter value for 1 km : ");
					one_km=Integer.parseInt(sc.nextLine());
					System.out.println();
					break;
				}catch(Exception e) {
					System.out.println(e+"Type integer value");
					System.out.println();
				}
			}
			int one_day;
			while(true) {
				try {
					System.out.println("Enter value for one day :");
					one_day=Integer.parseInt(sc.nextLine());
					System.out.println();
					break;
				}catch(Exception e) {
					System.out.println(e+"Type integer value");
					System.out.println();
				}
			}
			
			int one_hun;
			while(true) {
				try {
					System.out.println("Enter value for 0 to 100 km range : ");
					one_hun=Integer.parseInt(sc.nextLine());
					System.out.println();
					break;
				}catch(Exception e) {
					System.out.println(e+"Type integer value");
					System.out.println();
				}
			}
			
			int hun_threehun;
			while(true) {
				try {
					System.out.println("Enter value for 100 to 300 km range : ");
					hun_threehun=Integer.parseInt(sc.nextLine());
					System.out.println();
					break;
				}catch(Exception e) {
					System.out.println(e+"Type integer value");
					System.out.println();
				}
			}
			
			int three_sixhun;
			while(true) {
				try {
					System.out.println("Enter value for 300 to 600 km range : ");
					three_sixhun=Integer.parseInt(sc.nextLine());
					System.out.println();
					break;
				}catch(Exception e) {
					System.out.println(e+"Type integer value");
					System.out.println();
				}
			}
			
			int sixplus;
			while(true) {
				try {
					System.out.println("Enter value for 600+ km range : ");
					sixplus=Integer.parseInt(sc.nextLine());
					System.out.println();
					break;
				}catch(Exception e) {
					System.out.println(e+"Type integer value");
					System.out.println();
				}
			}
			
			int advance_amt;
			while(true) {
				try {
					System.out.println("Enter Advance amount");
					advance_amt=Integer.parseInt(sc.nextLine());
					System.out.println();
					break;
				}
				catch(Exception e) {
					System.out.println(e+"Type integer value");
				}
				
			}
			int extra_amount;
			while(true) {
				try {
					System.out.println("Enter the extra amount to include if the destination and pickup points are different : ");
					extra_amount=Integer.parseInt(sc.nextLine());	
					System.out.println();
					break;
					
				}catch(Exception e) {
					System.out.println(e+"Type integer value");
				}
			}
					
			
			Cab cab=new Cab(car_number,loggedin_user,cab_type,price_type,one_km,one_day,one_hun,hun_threehun,three_sixhun,sixplus,advance_amt,extra_amount);
			cab_details.put(car_number,cab);
	
			System.out.println();
			System.out.println("----------Your cab is added successfully-------------");
			System.out.println();
		}
		
		

	}	
	

	private void viewcarDetails(String username) {
		int flag=0;
		if(cab_details.size()==0) {
			System.out.println();
			System.out.println("No cabs are found");
			System.out.println();
		}
		else{
			System.out.printf("%20s%20s%20s","Cab_Number","Cab_Type","Price_Type");
			System.out.println();
			System.out.printf("%19s","-----------------------------------------------------------------");
			System.out.println();
			for(Map.Entry<String, Cab> entry : cab_details.entrySet()) {
				if(entry.getValue().getOwner().getUsername().equals(username)) {
					flag=1;
					System.out.printf("%20s%20s%20s",entry.getValue().getCar_number(),entry.getValue().getCab_type(),entry.getValue().getPrice_type());
					System.out.println();
				}
			}
			System.out.println();
			if(flag==0) {
				System.out.println();
				System.out.printf("%30s","You have not added any cabs");
				System.out.println();
			}
		}
		
	}
	
	private void addAvailable(String username, String cab_number, LocalDate from_date, LocalDate to_date,String location){
		
		if(!cab_details.containsKey(cab_number)) {
			System.out.println();
			System.out.println("Cab number is not found");
			System.out.println();
			return;
		}
		else {
				for (LocalDate date = from_date; date.isBefore(to_date.plusDays(1)); date = date.plusDays(1))
				{
				    HashMap<String,String> available=cab_details.get(cab_number).car_availability;
				    
				    available.put( date.format(formatter), location);
				    
				}		
				System.out.println("----------------------------------------------------------------");
				System.out.println("Availability of your Cab "+cab_number+" is added succefully");
				System.out.println("----------------------------------------------------------------");
				
				HashMap<String,String> thisone;
				thisone=cab_details.get(cab_number).car_availability;
				for(Map.Entry<String, String> entry : thisone.entrySet()) {
					System.out.println(entry.getKey()+"        "+entry.getValue());
				}
				System.out.println();
		}
		System.out.println();
		
		if(waiting_list.size()!=0) {
			
			for(int i=waiting_list.size()-1;i>=0;i--) {
				
				ArrayList<Cab> booking_late=findAvailable(waiting_list.get(i).getFrom_date(),waiting_list.get(i).getTo_date(),waiting_list.get(i).getLoc());
				if(booking_late.size()!=0) {
					try {
						send_bookingmail("19tucs144@skct.edu.in",waiting_list.get(i).getCus_mail(),waiting_list.get(i).getCust_name());
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
					
				}
				waiting_list.removeLast();
			}
		}
		else {
			return;
		}
		
		
	}
	
	
	private void send_bookingmail(String appowner_id,String customer_id,String customer_name) throws UnknownHostException {
		
		String to =customer_id;

        String from = appowner_id;

        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(appowner_id, "poovi123");

            }

        });
        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject("Pooviga Cabs!");

            message.setText("Hi "+customer_name+" ,"+"\n\n"+"Cab is available at your requested date and location . Hurry up and book your cab !!!"+"\n\n"+"With regards , "+"\n"+"Pooviga Cabs");

            System.out.println("Sending message to the respective customer in waiting list...");
            System.out.println();
            // Send message
            Transport.send(message);
            System.out.println("Message has been sent to the customer successfully....");
            System.out.println();
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
	}
	
	

	
	private void sendmail(String appowner_id,String owner_name,String owner_id,String cab_number,String customer_name,String customer_id,String pickup_location,String drop_location,LocalDate from_date,LocalDate to_date,String way_of_booking) throws UnknownHostException {
	
		
		
		String to =customer_id;

        String from = appowner_id;

        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(appowner_id, "poovi123");

            }

        });

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject("Booking details!");

            message.setText("Hi "+customer_name+",\n \n" +"~~~~ Your cab has been booked successfully ~~~~\n\n"+ "Thank you for booking cab in Pooviga Cabs \n\n"+"Booking details \n\n"+"You can pick up your cab with number  "+cab_number+"  in "+pickup_location+"\n"
            				+"Your journey is from "+from_date+" to "+to_date+"for "+way_of_booking+"\n\n"+"Please drop the car at "+drop_location+"\n			RIDE SAFELY !!!"+"\nThank you "+"\n\n"+"Regards ,"+"\n"+"Pooviga Cabs");

            System.out.println("Sending message to the booked customer...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
            System.out.println();
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

        //-----------sending mail to the owner------------
        
        String too =owner_id;

        String fromm = appowner_id;

        String hostt = "smtp.gmail.com";

        // Get system properties
        Properties propertiess = System.getProperties();

        // Setup mail server
        propertiess.put("mail.smtp.host", hostt);
        propertiess.put("mail.smtp.port", "465");
        propertiess.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        propertiess.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session sessionn = Session.getInstance(propertiess, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(appowner_id, "poovi123");

            }

        });

        try {
            MimeMessage message = new MimeMessage(sessionn);

            message.setFrom(new InternetAddress(fromm));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(too));

            message.setSubject("Booking details!");

            message.setText("Hi "+owner_name+", \n" + " \n\n"+"Your cab number  "+cab_number+" has been booked by  "+customer_name+" \n"+"from "+pickup_location+"\n"
    				+"And the journey is from "+from_date+" to "+to_date+"\n"+"and will be dropped at "+drop_location+"\nThank you "+"\n\n"+"Regards ,"+"\n"+"Pooviga Cabs");

            System.out.println("Sending message to the cab owner...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
		
	}
	

	private void stopAvailable(String cab_number1, LocalDate from_date,LocalDate to_date,String decide) {	
		HashMap<String,String> date_location; //gives date and location of that particular car_number
		if(decide==null) {
			date_location=cab_details.get(cab_number1).car_availability;
			System.out.println();
			for (LocalDate date = from_date; date.isBefore(to_date); date = date.plusDays(1))
			{
			    if(date_location.containsKey(date.format(formatter))) {
			    	date_location.remove(date.format(formatter));
			    }
			}
			
		}

		else if(decide.equals("for_a_week/month/days")) {
		
			date_location=cab_details.get(cab_number1).car_availability;
			System.out.println();
			for (LocalDate date = from_date; date.isBefore(to_date); date = date.plusDays(1))
			{
			    if(date_location.containsKey(date.format(formatter))) {
			    	cab_details.get(cab_number1).car_availability.remove(date.format(formatter));
			    }
			}	
			
		}
		else if(decide.equals("particular_days_of_a_week")) {
		
			date_location=cab_details.get(cab_number1).car_availability;
			System.out.println();
			for (LocalDate date = from_date; date.isBefore(to_date); date = date.plusDays(7))
			{
			    if(date_location.containsKey(date.format(formatter))) {
			    	cab_details.get(cab_number1).car_availability.remove(date.format(formatter));
			    }
			}
			
		}
		else if(decide.equals("Alternate_days_in_a_range")) {
		
				date_location=cab_details.get(cab_number1).car_availability;
				System.out.println();
				for (LocalDate date = from_date; date.isBefore(to_date); date = date.plusDays(2))
				{
				    if(date_location.containsKey(date.format(formatter))) {
				    	cab_details.get(cab_number1).car_availability.remove(date.format(formatter));
				    }
				}	
				
		}
		else if(decide.equals("Range_of_days")) {
	
				date_location=cab_details.get(cab_number1).car_availability;
				System.out.println();
				for (LocalDate date = from_date; date.isBefore(to_date); date = date.plusDays(1))
				{
				    if(date_location.containsKey(date.format(formatter))) {
				    	cab_details.get(cab_number1).car_availability.remove(date.format(formatter));
				    }
				}	
				
		}
		
	}

	private void owners_booking_details(String username) {
		if(bookings.size()==0) {
			System.out.println("-----No bookings have been made for any users-----");
			System.out.println();
		}
		else {
			System.out.printf("%75s","----------BOOKING HISTORY---------");
			System.out.println();
			System.out.println();
			System.out.printf("%20s%20s%20s%40s%20s%20s","Customer_name","Booked_from","Booked_to","Kilometers Travelled","Money paid","Driver name");	
			System.out.println();
			System.out.println("-----------------------------------------------------------------------------------------------------------");
			System.out.println();
			for(int i=0;i<bookings.size();i++) {				
				System.out.println();
				if(bookings.get(i).getOwner_name().equals(username)) {
					System.out.printf("%20s%20s%20s%40s%20s%20s",bookings.get(i).getCust_name(),bookings.get(i).getFrom_date(),bookings.get(i).getTo_date(),bookings.get(i).getKilometer(),bookings.get(i).getCalculated_price(),bookings.get(i).getDriver_name());
				}
			}
		}
		if(cancellation.size()!=0) {
			System.out.printf("%75s","----------CANCELLATION HISTORY---------");
			System.out.println();
			System.out.println();
			System.out.printf("%20s%20s%20s%20s%20s%20s","Onwer_name","Cab_number","Customer_name","Cancelled_from","Cancelled_to","Refund Amount");
			System.out.println();
			System.out.println("----------------------------------------------------------------------------------------------------------------------------------");
			System.out.println();
			for(int i=0;i<cancellation.size();i++) {
				if(cancellation.get(i).getName_of_owner().equals(username)) {
					System.out.println();					
					System.out.printf("%20s%20s%20s%20s%20s%20s",cancellation.get(i).getName_of_owner(),cancellation.get(i).getCab_no(),cancellation.get(i).getName_of_customer(),cancellation.get(i).getFrom_date(),cancellation.get(i).getTo_date(),cancellation.get(i).getRefund());
					System.out.println();
				}				
			}
		}
		else {
			System.out.println();
			System.out.println("-----No records of cancellation-----");
			System.out.println();
			return;
		}
			

	}
		
	private ArrayList<Cab> findAvailable(LocalDate from_date,LocalDate to_date,String location){
		available_cabs=new ArrayList<>();  
			for(Map.Entry<String, Cab> entry: cab_details.entrySet() ) {
				if(entry.getValue().car_availability.containsKey(from_date.format(formatter)) && entry.getValue().car_availability.get(from_date.format(formatter)).equals(location) ) {
					boolean flag=true;
					for (LocalDate date = from_date; date.isBefore(to_date); date = date.plusDays(1))
					{	
						//checks if the cabs are available in that date
					    if(!entry.getValue().car_availability.containsKey(date.format(formatter))) {
					    	flag=false;
					    	break;
					    }		    
					}
					if(flag==true) {
						available_cabs.add(entry.getValue());
					}
					
				}
			}
			if(available_cabs.size()==0) {
				System.out.println();
				System.out.println("Sorry , No cabs are available in this date and location");
				System.out.println();
				while(true) {
					System.out.print("Do you want us to notify when the cabs are available in same date and location ? (yes/no) ");
					String notify=sc.nextLine();
					if(validate.isvalidanswer(notify)) {
						if(notify.equalsIgnoreCase("yes")) {
							Booking addlist=new Booking(loggedin_user,loggedin_user.getMail_id(),from_date,to_date,location);					
							waiting_list.add(addlist);
							System.out.println();
							System.out.println("Your booking is in waiting list");
							
							break;
						}
						else {
							break;
						}
					}
				}				
			}
			else {
				System.out.println();
				System.out.println("These are the available cabs with the given date and location ");
				System.out.println();
				System.out.printf("%20s%20s%20s","Car number","Car owners-name","Price_type");
				System.out.println();
				System.out.printf("%20s","-------------------------------------------------------------------------------");
				System.out.println();
				for(int i=0;i<available_cabs.size();i++) {
					System.out.printf("%20s%20s%20s",available_cabs.get(i).getCar_number(),available_cabs.get(i).getOwner().getUsername(),available_cabs.get(i).getPrice_type(),available_cabs.get(i).getCab_type());
					System.out.println();
				}
			}
			return available_cabs;
	}
	
	
	 // < username of cab: cab_num > (has every available cabs with respective date and location)
	
	private ArrayList<Cab> findAvailable(LocalDate from_date,LocalDate to_date,String location,String cus_prices,String cab_types) {
		
			available_cabs=new ArrayList<>();  
			for(Map.Entry<String, Cab> entry: cab_details.entrySet() ) {
				if(entry.getValue().car_availability.containsKey(from_date.format(formatter)) && entry.getValue().car_availability.get(from_date.format(formatter)).equals(location) && entry.getValue().getCab_type().equals(cab_types) && entry.getValue().getPrice_type().equals(cus_prices)) {
					boolean flag=true;
					for (LocalDate date = from_date; date.isBefore(to_date); date = date.plusDays(1))
					{	
						//checks if the cabs are available in that date
					    if(!entry.getValue().car_availability.containsKey(date.format(formatter))) {
					    	flag=false;
					    	break;
					    }				    
					}
					if(flag==true) {
						available_cabs.add(entry.getValue());
					}
					
				}
			}
			System.out.println();
			if(available_cabs.size()==0) {
				System.out.println();
				System.out.println("No cabs are available with your requested constraints , Try searching again");
				System.out.println();			
			}
		return available_cabs;
		
	}

	private void booking(String user) {	
				
		    while(true) {
		    	String from_datestr=null;
				String to_datestr=null;	    
			    
			    boolean from_bool=true;
			    boolean to_bool=true;
			    System.out.println();
		    	System.out.println("Choose your way of bookings : ");
	    		System.out.println();
	    		System.out.println("1) For a week/month/days");
	    		System.out.println("2) For alternate week days");
	    		System.out.println("3) For a range of days");
	    		System.out.println("4) For alternate days in particular range");
	    		System.out.println();
	    		System.out.print("Choose your choice : ");
	    		String choice=sc.nextLine();
	    		System.out.println();
	    		if(validate.validateChoice(choice)) {
	    			if(Integer.parseInt(choice)==1) {
	    				
	    					des="for_a_week/month/days";
	    					
	    					while(true) {
	    						try {
	    							while(from_bool!=false) {
	    	        					System.out.print("Enter from_date ( yyyy-MM-dd ): "); 
	    	        					from_datestr=sc.nextLine();
	    	        					from_bool=validate.isvalidFutureDate(from_datestr,"yyyy-MM-dd");
	    	        					if(from_bool==true) {
	    	        						System.out.println("You cannot book for past dates");
	    	        					}
	    	        					System.out.println();
	    	        				}	
	    							break;
	    						}catch(Exception e) {
	    							System.out.println("Enter date in correct format(yyyy-MM-dd)");
	    							System.out.println();
	    						}
	    					}

	        				f_d = LocalDate.parse(from_datestr, formatter);
	        				int num=0;
	        				while(true) {
	        					try {
	        						System.out.print("Enter the duration(week-6/month-29/days) :");
	    	        				num=Integer.parseInt(sc.nextLine());
	    	        				break;
	        					}catch(Exception e) {
	        						System.out.println("Enter integer value");
	        						System.out.println();
	        						continue;
	        					}
	        				}
	        				
	        				t_d=f_d.plusDays(num);
	        				if(num==6) {
	        					no_of_days=7;
	        				}
	        				else if(num==29) {
	        					no_of_days=30;
	        				}
	        				else {
	        					no_of_days=num;
	        				}
	        				
	    			}
	    			else if(Integer.parseInt(choice)==2) {
	    				 des="particular_days_of_a_week";
	    				 
	    				 while(true) {
	    					 try {
	    						 while(from_bool!=false) {
	 	        					System.out.print("Enter from_date ( yyyy-MM-dd ): "); 
	 	        					from_datestr=sc.nextLine();
	 	        					from_bool=validate.isvalidFutureDate(from_datestr,"yyyy-MM-dd");
	 	        					if(from_bool==true) {
	 	        						System.out.println("You cannot book for past dates");
	 	        						System.out.println();
	 	        					}
	 	        					System.out.println();
	 	        				}
	    						 break;
	    					 }catch(Exception e) {
	    						 System.out.println("Enter date in correct format (yyyy-MM-dd) ");
	    						 System.out.println();
	    					 }
	    				 }
	    				 int x=0;
	    				 while(true) {
	    					 try {
	    						 System.out.print("Enter the duration(week-6/month-29/days) :");
			        			 x=Integer.parseInt(sc.nextLine());
			        			 break;
	    					 }
	    					 catch(Exception e) {
	    						 System.out.println("Enter integer value");
	    						 System.out.println();
	    						 continue;
	    					 }
	    					 
	    				 }

	        				f_d = LocalDate.parse(from_datestr, formatter);
	                        t_d=f_d.plusDays(x);
	    				  
	    			}
	    			else if(Integer.parseInt(choice)==3) {
	    				des="Range_of_days";
	    				
    					while(true) {
    						try {
    							while(from_bool!=false) {
    								System.out.print("Enter from_date ( yyyy-MM-dd ): "); 
    								from_datestr=sc.nextLine();
    								from_bool=validate.isvalidFutureDate(from_datestr,"yyyy-MM-dd");
    								if(from_bool==true) {
    									System.out.println("You cannot book for past dates and your to_date should be after from_date");
    								}
    								System.out.println();
    							}	
    							while(to_bool!=false) {
    								System.out.print("Enter to_date ( yyyy-MM-dd ): ");
    								to_datestr=sc.nextLine();
    								to_bool=validate.isvalidtodate(from_datestr,"yyyy-MM-dd",to_datestr);
    								if(to_bool==true) {
    									System.out.println("You cannot book for past dates");
    								}
    								System.out.println();
    							}	
    							break;
    						}catch(Exception e) {
    							System.out.println(e+"-->Enter valid date (yyyy-MM-dd) ");
    							System.out.println();
    							continue;
    						}
    					}
    					
    					f_d = LocalDate.parse(from_datestr, formatter);
    					t_d = LocalDate.parse(to_datestr, formatter);
    					
	    			}
	    			else if(Integer.parseInt(choice)==4) {
	    				des="Alternate_days_in_a_range";
    			        while(true) {
    						try {
    							while(from_bool!=false) {
    								System.out.print("Enter from_date ( yyyy-MM-dd ): "); 
    								from_datestr=sc.nextLine();
    								from_bool=validate.isvalidFutureDate(from_datestr,"yyyy-MM-dd");
    								if(from_bool==true) {
    									System.out.println("You cannot book for past dates");
    								}
    								System.out.println();
    							}	
    							while(to_bool!=false) {
    								System.out.print("Enter to_date ( yyyy-MM-dd ): ");
    								to_datestr=sc.nextLine();
    								to_bool=validate.isvalidtodate(from_datestr,"yyyy-MM-dd",to_datestr);
    								if(to_bool==true) {
    									System.out.println("You cannot book for past dates and your to_date should be after from_date");
    								}
    								System.out.println();
    							}	
    							break;
    						}catch(Exception e) {
    							System.out.println(e+"-->Enter valid date (yyyy-MM-dd) ");
    							System.out.println();
    							continue;
    						}
    					}
    					
    					f_d = LocalDate.parse(from_datestr, formatter);
    					t_d = LocalDate.parse(to_datestr, formatter);
    					
	    			}
	    			break;
	    		}
	    		else {
	    			continue;
	    		}
		        
		    }	
		    boolean location_bool=false;
		    
			String location=null;
			while(location_bool!=true) {
				System.out.println();
				System.out.print("Enter your from location : ");
				location=sc.nextLine();
				location_bool=validate.isvalidString(location);
			}
			
			
			boolean destination_bool=false;
			String destination=null;
			while(destination_bool!=true) {
				System.out.println();
				System.out.print("Enter your destination location : ");
				destination=sc.nextLine();
				destination_bool=validate.isvalidString(destination);
			}
			int pricing_type=0;
			while(true) {
				try {
					System.out.println();
					System.out.println("Choose your pricing type : ");
					System.out.println();
					
					System.out.println("1. fixed price : price for 1km."+"\n"+
							"2. price for a single day."+"\n"+
							"3. price varing");
					pricing_type=Integer.parseInt(sc.nextLine());
					if(pricing_type!=1 && pricing_type!=2 && pricing_type!=3) {
						System.out.println("Enter 1 or 2 or 3");
						continue;
					}
					break;
				}catch(Exception e) {
					System.out.println("Enter valid number 1 or 2 or 3");
					continue;
				}
			}
			
			
			String cus_pricee=null;
			if(pricing_type==1) {
				cus_pricee="fixed_price";
			}
			else if(pricing_type==2) {
				cus_pricee="Single_day";
			}
			else if(pricing_type==3) {
				cus_pricee="Variable_day";
			}
			System.out.println();
			String cab_types=null;
			System.out.println("Enter your cab type : "+"\n"+"1) 4 seater"+"\n"+"2) 5 seater"+"\n"+"3) 7 seater"+"\n"+"4) Van(>=10 seater)");
			int cabs=Integer.parseInt(sc.nextLine());
			if(cabs==1) {
				cab_types="4 seater";
			}
			else if(cabs==2) {
				cab_types="5 seater";
			}
			else if(cabs==3) {
				cab_types="7 seater";
			}
			else if(cabs==4) {
				cab_types="Van model";
			}
		    
		    ArrayList<Cab> needed_cabs=findAvailable(f_d, t_d, location,cus_pricee,cab_types);
			System.out.println();
						
			while(true) {
				try {
					if(needed_cabs.size()!=0) {
						System.out.printf("%45s","Filtered Cabs");
						System.out.println("\n\n");				
						System.out.printf("%20s%20s%20s%20s","Owner_name","Car_number","Price_type","Cab_type");
						System.out.println();
						System.out.println("----------------------------------------------------------------------------------------");
						for(int i=0;i<needed_cabs.size();i++) {		
							System.out.println();
							System.out.printf( "%20s%20s%20s%20s",needed_cabs.get(i).getOwner().getUsername(),needed_cabs.get(i).getCar_number(),needed_cabs.get(i).getPrice_type(),needed_cabs.get(i).getCab_type());
							System.out.println();
						}
					System.out.println();
					 String cab_num=null;
						while(true) {					
							System.out.print("Enter cab_number : ");						
							cab_num=sc.nextLine();			
							if(!cab_details.containsKey(cab_num)) {
								System.out.println();
								System.out.println("Please choose the cab_number from available list");
								System.out.println();
							}
							else {
								break;
							}
						}	
						
						boolean flag=false;
						for(int i=0;i<needed_cabs.size();i++) {
							if(needed_cabs.get(i).getCar_number().equals(cab_num)) {
								flag=true;
								break;
							}
						}
						if(flag==false) {
							System.out.println();
							System.out.println("Please choose cabs from the above list");
							break;
						}
						else {

							System.out.println();
							System.out.print("\n"+"Enter the approximate kilometer you would travel : ");
							int kilo=Integer.parseInt(sc.nextLine());
						    System.out.println();
						    int find_price=0;
				    		calc_fixed_price obj1=new calc_fixed_price();
				    		calc_singleday_price obj2=new calc_singleday_price();
				    		calc_variable_price obj3=new calc_variable_price();

						    if(des.equals("for_a_week/month/days")){
						    	if(cab_details.get(cab_num).getPrice_type().equals("fixed_price")) {
						    		find_price=obj1.calc_price(cab_num,cab_details.get(cab_num).getPrice_type(),kilo,no_of_days);
						    	}
						    	else if(cab_details.get(cab_num).getPrice_type().equals("Single_day")) {
						    		find_price=obj2.calc_price(cab_num,cab_details.get(cab_num).getPrice_type(),kilo,no_of_days);
						    	}
						    	else if(cab_details.get(cab_num).getPrice_type().equals("Variable_day")) {
						    		find_price=obj3.calc_price(cab_num,cab_details.get(cab_num).getPrice_type(),kilo,no_of_days);
						    	}
						    	if(!location.equals(destination)) {
						    		find_price=find_price+cab_details.get(cab_num).getExtra_amount();
						    	}
								
								String driver_needed=null;
								while(true) {
									System.out.print("Do you need a driver ? ");
									driver_needed=sc.nextLine();
									boolean driver_bool=validate.isvalidanswer(driver_needed);
									if(driver_bool!=true) {
										continue;
									}
									else {
										break;
									}
								}
								System.out.println();
								String driver_name=null;
								int driver_amount=0;
								if(driver_needed.equalsIgnoreCase("yes")) {
									driver_name=findDriver();
									if(driver_name==null) {
										System.out.println();
										System.out.println("------------------------------------");
										System.out.println("Sorry no drivers are available");
										System.out.println("------------------------------------");
										System.out.println();
									}
									else {
										System.out.println("Driver "+driver_name+" is ready for the journey");
										System.out.println();
										System.out.println("Since you have asked for driver , values for the drivers will be added along with the calculated price");
										driver_amount=user_detail.get(driver_name).getDriver_price_per_day()*no_of_days;
										System.out.println("Driver's amount for "+no_of_days+" days your journey includes : " +driver_amount);
										find_price=find_price+driver_amount;
										user_detail.get(driver_name).setWallet(user_detail.get(driver_name).getWallet()+driver_amount);
									}
								}
								else {
									driver_name="No drivers opted";
								}
								
								
								
								for(int i=0;i<bookings.size();i++) {
									if(bookings.get(i).getCust_name().equals(user)) {
										System.out.println("As this is not your first booking , would you like to reduce from your bonus points ?( yes/no ) ");
										String tell;
										while(true) {
											tell=sc.nextLine();
											boolean bool_tell=validate.isvalidanswer(tell);
											if(bool_tell==true) {
												break;
											}
										}
										if(tell.equalsIgnoreCase("yes") && user_detail.get(user).getBonus_amount()==0) {
											System.out.println("Sorry , your bonus balance is zero");
										}
										else if(user_detail.get(user).getBonus_amount()>find_price) {
											user_detail.get(user).setBonus_amount(user_detail.get(user).getBonus_amount()-find_price);
											find_price=0;
										}
										else if(tell.equalsIgnoreCase("yes") && user_detail.get(user).getBonus_amount()!=0) {
											System.out.println();
											System.out.println("Before reducing bonus points : "+find_price);
											System.out.println();
											System.out.println("Reduced bonus amount : "+user_detail.get(user).getBonus_amount());
											System.out.println();
											
											find_price=find_price-user_detail.get(user).getBonus_amount();						
										}
										else {
											user_detail.get(user).setBonus_amount(user_detail.get(user).getBonus_amount()+5);
										}
										
										break;
									}
								}
								
								System.out.println();
						        System.out.println("Your approximate value for your journey is "+find_price+" and your advance amount is "+cab_details.get(cab_num).getAdvance());
								System.out.println();
								
								String tell=null;
								while(true) {
									System.out.print("Do  you still wish to book this cab ? (yes/no)");
									tell=sc.nextLine();
									boolean tell_bool=validate.isvalidanswer(tell);
									if(tell_bool!=true) {
										continue;
									}
									else {
										break;
									}
									
								}		
								if(tell.equalsIgnoreCase("yes")) {
									
									find_price=find_price+cab_details.get(cab_num).getAdvance();
									
									for (LocalDate date = f_d; date.isBefore(t_d.plusDays(1)); date = date.plusDays(1))
									{
										price_booking(cab_details.get(cab_num).getOwner().getUsername(),cab_num, f_d,t_d,cab_types,cus_pricee,des,no_of_days,find_price,kilo,destination,driver_name,driver_amount,location,user_detail.get(user).getMail_id());
									    
									}	
									user_detail.get(user).setWallet(user_detail.get(user).getWallet()-find_price);
									cab_details.get(cab_num).getOwner().setWallet(cab_details.get(cab_num).getOwner().getWallet()+find_price);
									System.out.println();
									System.out.println("---------------------------------------------------");
									System.out.println("Your cab is booked successfully, Enjoy your ride");
									System.out.println();
									
									LocalDate f = f_d;
									
									LocalDate dateBefore = LocalDate.parse(LocalDate.now().format(formatter)); //from_date 
									LocalDate dateAfter = LocalDate.parse(f.format(formatter));

									//calculating number of days in between
									long no = ChronoUnit.DAYS.between(dateBefore, dateAfter);
									
									System.out.println("You have "+no+" days to go");
									
									System.out.println("---------------------------------------------------");
								
									
									sendmail("19tucs144@skct.edu.in",cab_details.get(cab_num).getOwner().getUsername(),cab_details.get(cab_num).getOwner().getMail_id(),cab_num,user,user_detail.get(user).getMail_id(),location,destination,f_d,t_d,des);
									
									
								}
								else {
									System.out.println();
						    		System.out.println("Thank you ! Come again to book your ride");
						    		System.out.println();
						    	}
						    }
						    else if(des.equals("particular_days_of_a_week")){
						    	no_of_days=0;
						        for (LocalDate date = f_d; date.isBefore(t_d.plusDays(1)); date = date.plusDays(7))
						        	
								{
						        	no_of_days++;
								}
						        if(cab_details.get(cab_num).getPrice_type().equals("fixed_price")) {
						    		find_price=obj1.calc_price(cab_num,cab_details.get(cab_num).getPrice_type(),kilo,no_of_days);
						    	}
						    	else if(cab_details.get(cab_num).getPrice_type().equals("Single_day")) {
						    		find_price=obj2.calc_price(cab_num,cab_details.get(cab_num).getPrice_type(),kilo,no_of_days);
						    	}
						    	else if(cab_details.get(cab_num).getPrice_type().equals("Variable_day")) {
						    		find_price=obj3.calc_price(cab_num,cab_details.get(cab_num).getPrice_type(),kilo,no_of_days);
						    	}						        
						        if(!location.equals(destination)) {
						    		find_price=find_price+cab_details.get(cab_num).getExtra_amount();
						    	}
								
								String driver_needed=null;
								while(true) {
									System.out.print("Do you need a driver ?(Yes/no) ");
									driver_needed=sc.nextLine();
									boolean driver_bool=validate.isvalidanswer(driver_needed);
									if(driver_bool!=true) {
										continue;
									}
									else {
										break;
									}
								}
								System.out.println();
								
								String driver_name=null;
								int driver_amount=0;
								if(driver_needed.equalsIgnoreCase("yes")) {
									driver_name=findDriver();
									if(driver_name==null) {
										System.out.println("Sorry no drivers are available");
									}
									else {
										System.out.println("Driver "+driver_name+" is ready for the journey");
										System.out.println();
										System.out.println("Since you have asked for driver , values for the drivers will be added along with the calculated price");
										driver_amount=user_detail.get(driver_name).getDriver_price_per_day()*no_of_days;
										System.out.println("Driver's amount for "+no_of_days+" days your journey includes : " +driver_amount);
										find_price=find_price+driver_amount;
										user_detail.get(driver_name).setWallet(user_detail.get(driver_name).getWallet()+driver_amount);
									}
								}
								else {
									driver_name="No drivers opted";
								}
								
								for(int i=0;i<bookings.size();i++) {
									if(bookings.get(i).getCust_name().equals(user)) {
										System.out.println("As this is not your first booking , would you like to reduce from your bonus points ?( yes/no ) ");
										String tell;
										while(true) {
											tell=sc.nextLine();
											boolean bool_tell=validate.isvalidanswer(tell);
											if(bool_tell==true) {
												break;
											}
										}
										if(tell.equalsIgnoreCase("yes") && user_detail.get(user).getBonus_amount()==0) {
											System.out.println("Sorry , your bonus balance is zero");
										}
										else if(user_detail.get(user).getBonus_amount()>find_price) {
											user_detail.get(user).setBonus_amount(user_detail.get(user).getBonus_amount()-find_price);
											find_price=0;
										}
										else if(tell.equalsIgnoreCase("yes") && user_detail.get(user).getBonus_amount()!=0) {
											System.out.println();
											System.out.println("Before reducing bonus points : "+find_price);
											System.out.println();
											System.out.println("Reduced bonus amount : "+user_detail.get(user).getBonus_amount());
											System.out.println();
											
											find_price=find_price-user_detail.get(user).getBonus_amount();						
										}
										else {
											user_detail.get(user).setBonus_amount(user_detail.get(user).getBonus_amount()+5);
										}
										break;
									}
								}
								
								System.out.println();
						        System.out.println("Your approximate value for your journey is "+find_price);
								System.out.println();
								
								String tell=null;
								while(true) {
									System.out.print("Do  you still wish to book this cab ? (yes/no)");
									tell=sc.nextLine();
									boolean tell_bool=validate.isvalidanswer(tell);
									if(tell_bool!=true) {
										continue;
									}
									else {
										break;
									}
									
								}
								
								if(tell.equalsIgnoreCase("yes")) {
									
									for (LocalDate date = f_d; date.isBefore(t_d.plusDays(1)); date = date.plusDays(7))
							        	
									{
							        	
										price_booking(cab_details.get(cab_num).getOwner().getUsername(),cab_num, f_d,t_d,cab_types,cus_pricee,des,no_of_days,find_price,kilo,destination,driver_name,driver_amount,location,user_detail.get(user).getMail_id());
									}
									user_detail.get(user).setWallet(user_detail.get(user).getWallet()-find_price);
									cab_details.get(cab_num).getOwner().setWallet(cab_details.get(cab_num).getOwner().getWallet()+find_price);
									System.out.println();
									System.out.println("---------------------------------------------------");
									System.out.println("Your cab is booked successfully, Enjoy your ride");
									System.out.println();
									
									LocalDate f = f_d;
									
									LocalDate dateBefore = LocalDate.parse(LocalDate.now().format(formatter)); //from_date 
									LocalDate dateAfter = LocalDate.parse(f.format(formatter));

									//calculating number of days in between
									long no = ChronoUnit.DAYS.between(dateBefore, dateAfter);
									
									System.out.println("You have "+no+" days to go");
									
									System.out.println("---------------------------------------------------");
									
									sendmail("19tucs144@skct.edu.in",cab_details.get(cab_num).getOwner().getUsername(),cab_details.get(cab_num).getOwner().getMail_id(),cab_num,user,user_detail.get(user).getMail_id(),location,destination,f_d,t_d,des);
								}
								else {
									System.out.println();
						    		System.out.println("Thank you ! Come again to book your ride");
						    		System.out.println();
						    	}
						        
						        
						    }
						    else if(des.equals("Range_of_days")){
						    	no_of_days=0;
						    	
						    	for (LocalDate date = f_d; date.isBefore(t_d.plusDays(1)); date = date.plusDays(1))
								{
						        	no_of_days++;
								    
								}
						    	if(cab_details.get(cab_num).getPrice_type().equals("fixed_price")) {
						    		find_price=obj1.calc_price(cab_num,cab_details.get(cab_num).getPrice_type(),kilo,no_of_days);
						    	}
						    	else if(cab_details.get(cab_num).getPrice_type().equals("Single_day")) {
						    		find_price=obj2.calc_price(cab_num,cab_details.get(cab_num).getPrice_type(),kilo,no_of_days);
						    	}
						    	else if(cab_details.get(cab_num).getPrice_type().equals("Variable_day")) {
						    		find_price=obj3.calc_price(cab_num,cab_details.get(cab_num).getPrice_type(),kilo,no_of_days);
						    	}
						    	if(!location.equals(destination)) {
						    		find_price=find_price+cab_details.get(cab_num).getExtra_amount();
						    	}
						    	
						    	String driver_needed=null;
								while(true) {
									System.out.print("Do you need a driver ? ");
									driver_needed=sc.nextLine();
									boolean driver_bool=validate.isvalidanswer(driver_needed);
									if(driver_bool!=true) {
										continue;
									}
									else {
										break;
									}
								}
								System.out.println();
								String driver_name=null;
								int driver_amount=0;
								if(driver_needed.equalsIgnoreCase("yes")) {
									driver_name=findDriver();
									if(driver_name==null) {
										System.out.println("Sorry no drivers are available");
									}
									else {
										System.out.println("Driver "+driver_name+" is ready for the journey");
										System.out.println();
										System.out.println("Since you have asked for driver , values for the drivers will be added along with the calculated price");
										driver_amount=user_detail.get(driver_name).getDriver_price_per_day()*no_of_days;
										System.out.println("Driver's amount for "+no_of_days+" days your journey includes : " +driver_amount);
										find_price=find_price+driver_amount;
										user_detail.get(driver_name).setWallet(user_detail.get(driver_name).getWallet()+driver_amount);
									}
								}
								else {
									driver_name="No drivers opted";
								}
								
								
								for(int i=0;i<bookings.size();i++) {
									if(bookings.get(i).getCust_name().equals(user)) {
										System.out.println("As this is not your first booking , would you like to reduce from your bonus points ?( yes/no ) ");
										String tell;
										while(true) {
											tell=sc.nextLine();
											boolean bool_tell=validate.isvalidanswer(tell);
											if(bool_tell==true) {
												break;
											}
										}
										if(tell.equalsIgnoreCase("yes") && user_detail.get(user).getBonus_amount()==0) {
											System.out.println("Sorry , your bonus balance is zero");
										}
										else if(user_detail.get(user).getBonus_amount()>find_price) {
											user_detail.get(user).setBonus_amount(user_detail.get(user).getBonus_amount()-find_price);
											find_price=0;
										}
										else if(tell.equalsIgnoreCase("yes") && user_detail.get(user).getBonus_amount()!=0) {
											System.out.println();
											System.out.println("Before reducing bonus points : "+find_price);
											System.out.println();
											System.out.println("Reduced bonus amount : "+user_detail.get(user).getBonus_amount());
											System.out.println();
											
											find_price=find_price-user_detail.get(user).getBonus_amount();						
										}
										else {
											user_detail.get(user).setBonus_amount(user_detail.get(user).getBonus_amount()+5);
										}
										break;
									}
								}
								
								System.out.println();
						        System.out.println("Your approximate value for your journey is "+find_price);
								System.out.println();
								
								String tell=null;
								while(true) {
									System.out.print("Do  you still wish to book this cab ? (yes/no)");
									tell=sc.nextLine();
									boolean tell_bool=validate.isvalidanswer(tell);
									if(tell_bool!=true) {
										continue;
									}
									else {
										break;
									}
									
								}
						    	if(tell.equalsIgnoreCase("yes")) {
						    		
						    		for (LocalDate date = f_d; date.isBefore(t_d.plusDays(1)); date = date.plusDays(1))
									{
										price_booking(cab_details.get(cab_num).getOwner().getUsername(),cab_num, f_d,t_d,cab_types,cus_pricee,des,no_of_days,find_price,kilo,destination,driver_name,driver_amount,location,user_detail.get(user).getMail_id());
									    
									}

						    		user_detail.get(user).setWallet(user_detail.get(user).getWallet()-find_price);
									cab_details.get(cab_num).getOwner().setWallet(cab_details.get(cab_num).getOwner().getWallet()+find_price);
									
									System.out.println();
									System.out.println("---------------------------------------------------");
									System.out.println("Your cab is booked successfully, Enjoy your ride");
									System.out.println();
									
									LocalDate f = f_d;
									
									LocalDate dateBefore = LocalDate.parse(LocalDate.now().format(formatter)); //from_date 
									LocalDate dateAfter = LocalDate.parse(f.format(formatter));

									//calculating number of days in between
									long no = ChronoUnit.DAYS.between(dateBefore, dateAfter);
									
									System.out.println("You have "+no+" days to go");
									
									System.out.println("---------------------------------------------------");
									
									sendmail("19tucs144@skct.edu.in",cab_details.get(cab_num).getOwner().getUsername(),cab_details.get(cab_num).getOwner().getMail_id(),cab_num,user,user_detail.get(user).getMail_id(),location,destination,f_d,t_d,des);
						    	}
						    	else {
						    		System.out.println();
						    		System.out.println("Thank you ! Come again to book your ride");
						    		System.out.println();
						    	}
						    	
						    }
						    else if(des.equals("Alternate_days_in_a_range")){
						    	no_of_days=0;
						    	for (LocalDate date = f_d; date.isBefore(t_d.plusDays(1)); date = date.plusDays(2))
								{
						        	no_of_days++;
								}
						    	if(cab_details.get(cab_num).getPrice_type().equals("fixed_price")) {
						    		find_price=obj1.calc_price(cab_num,cab_details.get(cab_num).getPrice_type(),kilo,no_of_days);
						    	}
						    	else if(cab_details.get(cab_num).getPrice_type().equals("Single_day")) {
						    		find_price=obj2.calc_price(cab_num,cab_details.get(cab_num).getPrice_type(),kilo,no_of_days);
						    	}
						    	else if(cab_details.get(cab_num).getPrice_type().equals("Variable_day")) {
						    		find_price=obj3.calc_price(cab_num,cab_details.get(cab_num).getPrice_type(),kilo,no_of_days);
						    	}
						    	if(!location.equals(destination)) {
						    		find_price=find_price+cab_details.get(cab_num).getExtra_amount();
						    	}
						    	
								String driver_needed=null;
								while(true) {
									System.out.print("Do you need a driver ? ");
									driver_needed=sc.nextLine();
									boolean driver_bool=validate.isvalidanswer(driver_needed);
									if(driver_bool!=true) {
										continue;
									}
									else {
										break;
									}
								}
								System.out.println();
								
								String driver_name=null;
								int driver_amount=0;
								if(driver_needed.equalsIgnoreCase("yes")) {
									driver_name=findDriver();
									if(driver_name==null) {
										System.out.println("Sorry no drivers are available");
									}
									else {
										System.out.println("Driver "+driver_name+" is ready for the journey");
										System.out.println();
										System.out.println("Since you have asked for driver , values for the drivers will be added along with the calculated price");
										driver_amount=user_detail.get(driver_name).getDriver_price_per_day()*no_of_days;
										System.out.println("Driver's amount for "+no_of_days+" days your journey includes : " +driver_amount);
										find_price=find_price+driver_amount;
										user_detail.get(driver_name).setWallet(user_detail.get(driver_name).getWallet()+driver_amount);
									}
								}
								else {
									driver_name="No drivers opted";
								}
								
								for(int i=0;i<bookings.size();i++) {
									if(bookings.get(i).getCust_name().equals(user)) {
										System.out.println("Would you like to reduce from your bonus points ?( yes/no ) ");
										String tell;
										while(true) {
											tell=sc.nextLine();
											boolean bool_tell=validate.isvalidanswer(tell);
											if(bool_tell==true) {
												break;
											}
										}
										if(tell.equalsIgnoreCase("yes") && user_detail.get(user).getBonus_amount()==0) {
											System.out.println("Sorry , your bonus balance is zero");
										}
										else if(user_detail.get(user).getBonus_amount()>find_price) {
											user_detail.get(user).setBonus_amount(user_detail.get(user).getBonus_amount()-find_price);
											find_price=0;
										}
										else if(tell.equalsIgnoreCase("yes") && user_detail.get(user).getBonus_amount()!=0) {
											System.out.println();
											System.out.println("Before reducing bonus points : "+find_price);
											System.out.println();
											System.out.println("Reduced bonus amount : "+user_detail.get(user).getBonus_amount());
											System.out.println();
											
											find_price=find_price-user_detail.get(user).getBonus_amount();						
										}
										else {
											user_detail.get(user).setBonus_amount(user_detail.get(user).getBonus_amount()+5);
										}
										break;
									}
								}
								
								System.out.println();
						    	System.out.println("Your approximate value for your journey is "+find_price);
								System.out.println();
								
						    		for (LocalDate date = f_d; date.isBefore(t_d.plusDays(1)); date = date.plusDays(2))
									{
										price_booking(cab_details.get(cab_num).getOwner().getUsername(),cab_num, f_d,t_d,cab_types,cus_pricee,des,no_of_days,find_price,kilo,destination,driver_name,driver_amount,location,user_detail.get(user).getMail_id());
									}
						    		user_detail.get(user).setWallet(user_detail.get(user).getWallet()-find_price);
									cab_details.get(cab_num).getOwner().setWallet(cab_details.get(cab_num).getOwner().getWallet()+find_price);
									System.out.println();
									System.out.println("---------------------------------------------------");
									System.out.println("Your cab is booked successfully, Enjoy your ride");
									System.out.println();
									
									LocalDate f = f_d;
									
									LocalDate dateBefore = LocalDate.parse(LocalDate.now().format(formatter)); //from_date 
									LocalDate dateAfter = LocalDate.parse(f.format(formatter));

									//calculating number of days in between
									long no = ChronoUnit.DAYS.between(dateBefore, dateAfter);
									
									System.out.println("You have "+no+" days to go");
									
									System.out.println("---------------------------------------------------");
									
									sendmail("19tucs144@skct.edu.in",cab_details.get(cab_num).getOwner().getUsername(),cab_details.get(cab_num).getOwner().getMail_id(),cab_num,user,user_detail.get(user).getMail_id(),location,destination,f_d,t_d,des);
						    	}
						    	else {
						    		System.out.println();
						    		System.out.println("Thank you ! Come again to book your ride");
						    		System.out.println();
						    	}
						    } 
						    break;
							
						}
					
					else {
						return;
					}
				}
				catch(Exception e) {
					System.out.println(e);
					continue;
				}
			}			
	}

	private void refer_mails() {
		String mail_id=null;
		while(true) {
			try {
				System.out.println();
				System.out.print("Enter the mail_id of your friends : ");
				mail_id=sc.nextLine();
				boolean mail=validate.isvalidmail(mail_id);
				if(mail==true)
					break;
			}catch(Exception e) {
				System.out.println(e);
				continue;
			}
		}
		if(references.containsKey(loggedin_user.getMail_id())) {
			ArrayList<String> get=references.get(loggedin_user.getMail_id());
			get.add(mail_id);
		}
		else {
			ArrayList<String> new_arr=new ArrayList<>();
			new_arr.add(mail_id);
			references.put(loggedin_user.getMail_id(),new_arr);
		}
		System.out.println();
		System.out.println("Thanks for recommending your friend");
		System.out.println();
		mail_to_friend(loggedin_user.getMail_id(),mail_id);
		System.out.println();		
	}
	private void mail_to_friend(String cus_id, String friends_mailid) {
		
		String to =friends_mailid;

        String from = "19tucs144@skct.edu.in";

        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(from, "poovi123");

            }

        });

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject("Cab service!");

            message.setText("Your friend "+loggedin_user.getUsername() +" has recommended you book cabs in our website .\n\nNote : You have got a bonus amount of 100 . \n\n Hurry up and book your ride !\n\nWith regards,\nPooviga Cabs.");
            System.out.println("Sending message to the customer's friend...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
            System.out.println();
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
		
		
	}
	private String findDriver() {
		ArrayList<Userclass> drivers_list=new ArrayList<>();
		
		for(Map.Entry<String, Userclass> entry: user_detail.entrySet()) {
			int flag1=0;
			if(entry.getValue().getRole().equals(usertype.Driver)) {
				flag1=1;				
			}
			int flag2=1;
			for(int i=0;i<bookings.size();i++) {
				
				if(bookings.get(i).getDriver_name().equals(entry.getValue().getUsername())) {
					flag2=0;
					break;
				}
			}
			if(flag1==1 && flag2==1) {
				drivers_list.add(entry.getValue());
			}
		}
		
		if(drivers_list.size()==0) {
			return null;
		}
		else {
			System.out.printf("%30s%30s","Driver's name","Driver's age");
			System.out.println();
			System.out.printf("%30s","---------------------------------------------------------------------");
			System.out.println();
			for(int i=0;i<drivers_list.size();i++) {
				System.out.printf("%30s%30s",drivers_list.get(i).getUsername(),drivers_list.get(i).getAge());
				System.out.println();
			}
		}
		System.out.println("Random drivers will be alloted");
		System.out.println();
		
		for (int i = 0; i < drivers_list.size();) 
        {
           // generating the index using Math.random()
            int index = (int)(Math.random() * drivers_list.size());  
            System.out.println("Randomly choosed driver's name :"+ drivers_list.get(index).getUsername());
            System.out.println();
            return drivers_list.get(index).getUsername();
        }	
		return null;
	}

	private void price_booking(String owner_name,String cab_number,LocalDate from_dat,LocalDate to_dat,String cab_types,String cus_price,String decid, int no_of_days2,int total_price, int kilo,String destination,String driver_name,int driver_amount,String location,String cus_mail) {  
		Booking books=new Booking(booking_id++,owner_name,loggedin_user.getUsername() ,cab_number,from_dat,to_dat,cab_types,cus_price,decid,total_price,kilo,destination,driver_name,driver_amount,location,cus_mail);		
		filter.add(books);
		stopAvailable(cab_number,from_dat,to_dat.plusDays(1),decid);			
	}

	private void seebooked_transactions(Userclass loggedin_user) {		
		if(bookings.size()!=0) {
			System.out.printf("%75s","--BOOKING HISTORY--");
			System.out.println();
			System.out.println();
			System.out.printf("%20s%20s%20s%20s%20s%30s%20s%20s","Booking id","Owner name","Cab_number","Booked from","Booked to","Way of booking","Total_Price","Drivers name");
			System.out.println();
			System.out.printf("%20s","-----------------------------------------------------------------------------------------------------------------------------------------------------------------");
			System.out.println();
			for(int i=0;i<bookings.size();i++) {
				if(bookings.get(i).getCust_name().equals(loggedin_user.getUsername()) ){				
					System.out.println();
					System.out.printf("%20s%20s%20s%20s%20s%30s%20s%20s",bookings.get(i).getId(),bookings.get(i).getOwner_name(),bookings.get(i).getCab_number2(),bookings.get(i).getFrom_date(),bookings.get(i).getTo_date(),bookings.get(i).getDecide(),bookings.get(i).getCalculated_price(),bookings.get(i).getDriver_name());
					System.out.println();
				}
			}
		}
		else {
			System.out.println();
			System.out.println("--------No records of Booking----------");
			System.out.println();
		}		
		if(cancellation.size()!=0) {
			System.out.printf("%75s","--CANCELLATION HISTORY--");
			System.out.println();
			System.out.println();
			System.out.printf("%20s%20s%20s%20s%20s%20s","Onwer_name","Cab_number","Customer_name","Cancelled_from","Cancelled_to","Refund Amount");
			System.out.println();
			System.out.printf("%20s","------------------------------------------------------------------------------------------------------------------------------");
			System.out.println();
			for(int i=0;i<cancellation.size();i++) {
				if(cancellation.get(i).getName_of_customer().equals(loggedin_user.getUsername())) {
					System.out.println();					
					System.out.printf("%20s%20s%20s%20s%20s%20s",cancellation.get(i).getName_of_owner(),cancellation.get(i).getCab_no(),cancellation.get(i).getName_of_customer(),cancellation.get(i).getFrom_date(),cancellation.get(i).getTo_date(),cancellation.get(i).getRefund());
					System.out.println();
				}				
			}
		}
		else {
			System.out.println();
			System.out.println("---------No records of cancellation--------");
			System.out.println();
		}		
	}

	private void cancelCabs() {	
		System.out.println();
		System.out.println("Enter the cab_number to cancel :");
		String cab_number=sc.nextLine();
		Integer booking_no;		
		while(true) {
			try {
				System.out.println("Enter your booking id : ");
				booking_no=Integer.parseInt(sc.nextLine());
				break;
			}
			catch(Exception e){
				System.out.println(e+"---->Type integer value");
			}
			
		}
		
		if(!cab_details.containsKey(cab_number)) {
			System.out.println("Cab not found");
		}
		else {
				long refund_amount=0;
				int flag=0;
				for(int i=0;i<bookings.size();i++) {					
					if(bookings.get(i).getId()==booking_no) {
						flag=1;
						System.out.println();
						System.out.println("------------Cab has been canceled------------");
						System.out.println();
						System.out.println("Your bonus amount is also reduced");
						System.out.println();
						loggedin_user.setBonus_amount(loggedin_user.getBonus_amount() - 5);
						user_detail.get(bookings.get(i).getDriver_name()).setWallet(user_detail.get(bookings.get(i).getDriver_name()).getWallet()-bookings.get(i).getDriver_amount()+100);
						
						LocalDate from_datee = bookings.get(i).getFrom_date();
//						LocalDate to_datee = bookings.get(i).to_date;
						
						LocalDate dateBefore = LocalDate.parse(LocalDate.now().format(formatter)); //from_date 
						LocalDate dateAfter = LocalDate.parse(from_datee.format(formatter));

						//calculating number of days in between
						long noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);
						System.out.println();
						System.out.println("Before refunding wallet amount : "+loggedin_user.getWallet());
						System.out.println();
						
						try {
							if(noOfDaysBetween==0) {
								System.out.println("( Since the cancellation is done on the booking day itself ,total amount will be refunded )");
								System.out.println();
								for(int j=0;j<bookings.size();j++) {
									if(bookings.get(i).getCab_number2().equals(cab_number)) {
										long price=bookings.get(j).getCalculated_price();
										loggedin_user.setWallet(loggedin_user.getWallet()+(int)price+cab_details.get(bookings.get(i).getCab_number2()).getAdvance());
										cab_details.get(cab_number).getOwner().setWallet(cab_details.get(cab_number).getOwner().getWallet()-(int)price-cab_details.get(bookings.get(i).getCab_number2()).getAdvance());
										System.out.println("Refund amount : "+ price);
										refund_amount=price;
										System.out.println();
										System.out.println("---------------------------------");
										System.out.println("Wallet of customer : "+loggedin_user.getWallet());
										System.out.println("--------------------------");
										System.out.println();
									}
								}
							}
							else if(noOfDaysBetween>(long)10) {
								for(int j=0;j<bookings.size();j++) {
									if(bookings.get(i).getCab_number2().equals(cab_number)) {
										long price=bookings.get(j).getCalculated_price();
										long res=price-noOfDaysBetween*2;
										loggedin_user.setWallet(loggedin_user.getWallet()+(int)res+cab_details.get(bookings.get(i).getCab_number2()).getAdvance());
										cab_details.get(cab_number).getOwner().setWallet(cab_details.get(cab_number).getOwner().getWallet()-(int)res-cab_details.get(bookings.get(i).getCab_number2()).getAdvance());
										System.out.println("Refund amount : "+ res);
										refund_amount=res;
										System.out.println();
										System.out.println("--------------------------");
										System.out.println("Wallet of customer : "+loggedin_user.getWallet());
										System.out.println("--------------------------");
										System.out.println();
									}
								}
							}
							else if(noOfDaysBetween<(long)10) {
								for(int j=0;j<bookings.size();j++) {
									if(bookings.get(j).getCab_number2().equals(cab_number)) {
										long price=bookings.get(j).getCalculated_price();
										long res=price-noOfDaysBetween*3;
										loggedin_user.setWallet(loggedin_user.getWallet()+(int)res+cab_details.get(bookings.get(i).getCab_number2()).getAdvance());
										cab_details.get(cab_number).getOwner().setWallet(cab_details.get(cab_number).getOwner().getWallet()-(int)res-cab_details.get(bookings.get(i).getCab_number2()).getAdvance());
										System.out.println("Refund amount : "+ res);
										refund_amount=res;
										System.out.println();
										System.out.println("--------------------------");
										System.out.println("Wallet of customer : "+loggedin_user.getWallet());
										System.out.println("--------------------------");
										System.out.println();
									}
								}
							}
						}catch(Exception e) {
							System.out.println(e);
						}	
						
						Cancellation cancel=new Cancellation(bookings.get(i).getId(),bookings.get(i).getOwner_name() , bookings.get(i).getCab_number2() , bookings.get(i).getCust_name() , bookings.get(i).getFrom_date() , bookings.get(i).getTo_date(),refund_amount,bookings.get(i).getDriver_name());
						cancellation.add(cancel);
						
						addAvailable(loggedin_user.getUsername(),bookings.get(i).getCab_number2(),bookings.get(i).getFrom_date(),bookings.get(i).getTo_date(),bookings.get(i).getLoc());
						
						cancellation_mail(loggedin_user.getMail_id(),cab_details.get(cab_number).getOwner().getMail_id(),cab_number,loggedin_user.getUsername(),cab_details.get(cab_number).getOwner().getUsername());
						
						bookings.remove(i);
						
					}
				}
				if(flag==0) {
					System.out.println("Booking id is not found");
				}
			}
		}	
	
	private void cancellation_mail(String customer_id,String owner_id,String cab_number,String customer_name,String owner_name) {
		
		String to =customer_id;
        String from = "19tucs144@skct.edu.in";
        String host = "smtp.gmail.com";
        // Get system properties
        Properties properties = System.getProperties();
        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");
        // Get the Session object.// and pass username and password -->response handling
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, "poovi123");
            }
        });
        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject("Booking details!");

            message.setText("Hi "+customer_name+" ,\n\n"+"Your booking has been cancelled . "+"\n"+"Cab "+cab_number+" is no more available\n\n"+"With regards ,\nPooviga Cabs");

            System.out.println("Sending message to the booked customer...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
            System.out.println();
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

        //-----------sending mail to the owner------------
        
        String too =owner_id;

        String fromm = "19tucs144@skct.edu.in";

        String hostt = "smtp.gmail.com";

        // Get system properties
        Properties propertiess = System.getProperties();

        // Setup mail server
        propertiess.put("mail.smtp.host", hostt);
        propertiess.put("mail.smtp.port", "465");
        propertiess.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        propertiess.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session sessionn = Session.getInstance(propertiess, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(fromm, "poovi123");

            }

        });

        try {
            MimeMessage message = new MimeMessage(sessionn);

            message.setFrom(new InternetAddress(fromm));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(too));

            message.setSubject("Booking details!");

            message.setText("Hi "+owner_name+" ,\n\n"+"Customer who booked your cab "+cab_number+" has cancelled their booking, please proceed with your duty");

            System.out.println("Sending message to the cab owner...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
		
	}
	private void changedetails() {
		System.out.println("Do you want to change your name ? ");
		String say=sc.nextLine();
		if(validate.isvalidanswer(say)) {
			if(say.equalsIgnoreCase("yes")) {
				String new_name=null;
				while(true) {
					System.out.println("Enter your new name : ");
					new_name=sc.nextLine();
					boolean find=validate.isvalidString(new_name);
					if(find==true) {
						break;
					}
				}			
				loggedin_user.setName(new_name);
				System.out.println("Your new name is changed successfully");
			}
		}					
		System.out.println();

		System.out.println("Do you want to change your password ? ");
		String say2=sc.nextLine();
		if(validate.isvalidanswer(say2)) {
			if(say2.equalsIgnoreCase("yes")) {
				String new_pass=null;
				while(true) {
					System.out.println("Enter your new password : ");
					new_pass=sc.nextLine();
					boolean find=validate.isvalidPassword(new_pass,loggedin_user.getUsername());
					if(find==true) {
						break;
					}
				}			
				loggedin_user.setPassword(new_pass);
				System.out.println("Your new password is changed successfully");
			}						
		}	
		return;
	}
	

	private void view_application_details() {
		
		ArrayList<Userclass> owner_list=new ArrayList<>();
		ArrayList<Userclass> customer_list=new ArrayList<>();
		ArrayList<Userclass> driver_list=new ArrayList<>();
		ArrayList<Userclass> temp_driver_list=new ArrayList<>();
		
		System.out.printf("%50s","OWNER DETAILS");		
		System.out.println("\n\n");
		
		System.out.printf("%20s%20s%40s%20s","Owner's_Username","Owner's_Name","Mail_id","Age");
		System.out.println();
		System.out.println("----------------------------------------------------------------------------------------------------------------");
		System.out.println();
		
		for(Map.Entry<String, Userclass> entry : user_detail.entrySet()) {
			if(entry.getValue().getRole().equals(usertype.Owner)) {
				owner_list.add(entry.getValue());
				System.out.printf("%20s%20s%40s%20s",entry.getKey(),entry.getValue().getName(),entry.getValue().getMail_id(),entry.getValue().getAge());
				System.out.println();
			}
		}
		if(owner_list.size()==0) {
			System.out.printf("%20s","No owner have registered");
		}
		System.out.println("\n\n");
		
		System.out.printf("%52s","DRIVER DETAILS");		
		System.out.println("\n\n");
		
		System.out.printf("%20s%20s%40s%20s%20s","Driver's_Username","Driver's_Name","Mail_id","Age","Price_perday");
		System.out.println();
		System.out.println("-----------------------------------------------------------------------------------------------------------------------");
		System.out.println();
		
		for(Map.Entry<String, Userclass> entry : user_detail.entrySet()) {
			if(entry.getValue().getRole().equals(usertype.Driver)) {
				driver_list.add(entry.getValue());
				System.out.printf("%20s%20s%40s%20s%20s",entry.getKey(),entry.getValue().getName(),entry.getValue().getMail_id(),entry.getValue().getAge(),entry.getValue().getDriver_price_per_day());
				System.out.println();
			}
		}
		if(driver_list.size()==0) {
			System.out.printf("%20s","No driver have registered");
		}
		
		System.out.println("\n\n");
		System.out.printf("%53s","CUSTOMER DETAILS");		
		System.out.println("\n\n");
		
		System.out.printf("%20s%20s%40s%20s","Customer's_Username","Customer's_Name","Mail_id","Age");
		System.out.println();
		System.out.println("----------------------------------------------------------------------------------------------------------------");
		System.out.println();
		
		for(Map.Entry<String, Userclass> entry : user_detail.entrySet()) {
			if(entry.getValue().getRole().equals(usertype.Customer)) {
				customer_list.add(entry.getValue());
				System.out.printf("%20s%20s%40s%20s",entry.getKey(),entry.getValue().getName(),entry.getValue().getMail_id(),entry.getValue().getAge());
				System.out.println();
			}
		}
		if(customer_list.size()==0) {
			System.out.printf("%20s","No customer have registered");
		}
		
		System.out.println("\n\n");
		System.out.printf("%52s","TEMPORARY DRIVER DETAILS");		
		System.out.println("\n\n");
		
		System.out.printf("%20s%20s%40s%20s%20s","Driver's_Username","Driver's_Name","Mail_id","Age","Price_perday");
		System.out.println();
		System.out.println("--------------------------------------------------------------------------------------------------------------------------");
		System.out.println();
		
		for(Map.Entry<String, Userclass> entry : user_detail.entrySet()) {
			if(entry.getValue().getRole().equals(usertype.Temporary_drivers)) {
				temp_driver_list.add(entry.getValue());
				System.out.printf("%20s%20s%40s%20s%20s",entry.getKey(),entry.getValue().getName(),entry.getValue().getMail_id(),entry.getValue().getAge(),entry.getValue().getDriver_price_per_day());
				System.out.println();
			}
		}
		if(temp_driver_list.size()==0) {
			System.out.printf("%20s","No temporary driver have registered");
		}
		
		System.out.println("\n\n");
		System.out.println("Total number of registered Owners : "+owner_list.size());
		System.out.println();
		System.out.println("Total number of registered Drivers : "+driver_list.size());
		System.out.println();
		System.out.println("Total number of registered Temporary Drivers : "+temp_driver_list.size());
		System.out.println();
		System.out.println("Total number of registered Customers :"+customer_list.size());
		System.out.println();
	}
	

	private void drivercancelling() {
		
		System.out.println("Enter the cab number you have alloted : ");
		String cab_num=sc.nextLine();
		System.out.println();
		String driver_name=loggedin_user.getUsername();
		int flag=0;
		for(int i=0;i<bookings.size();i++) {
			if(bookings.get(i).getCab_number2().equals(cab_num) && bookings.get(i).getDriver_name().equals(driver_name)) {
				flag=1;
				String gettemporarydrivername=get_tempdriver();
				if(gettemporarydrivername!=null) {
					bookings.get(i).setDriver_name(gettemporarydrivername);
				}
				sendmail_replaceddriver(user_detail.get(bookings.get(i).getCust_name()).getMail_id(),gettemporarydrivername,user_detail.get(gettemporarydrivername).getMail_id());
				System.out.println();
				System.out.println("----Since you have cancelled the ride at last moment you are charged with a penalty of 500 rupees----");
				System.out.println();
				user_detail.get(gettemporarydrivername).setWallet(bookings.get(i).getDriver_amount());
				loggedin_user.setWallet(loggedin_user.getWallet()-bookings.get(i).getDriver_amount()-500);			
				break;
								
			}
			
		}
		if(flag==0) {
			System.out.println("No cabs are found");
		}
		
	}
	

	private void sendmail_replaceddriver(String customer_id, String gettemporarydrivername,String driver_id) {
		String to =customer_id;

        String from = "19tucs144@skct.edu.in";

        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(from, "poovi123");

            }

        });

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject("Booking details!");

            message.setText("Sorry for our inconvinience , since your alloted driver is in emergency situation he is unable to make it ,\nWe have alloted new driver for you\n\n"
            		+ "Your new driver name is "+gettemporarydrivername);

            System.out.println("Sending message to the booked customer...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
            System.out.println();
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
		//------------------------------
        String too = driver_id;

        String fromm = "19tucs144@skct.edu.in";

        String hostt = "smtp.gmail.com";

        // Get system properties
        Properties propertiess = System.getProperties();

        // Setup mail server
        propertiess.put("mail.smtp.host", hostt);
        propertiess.put("mail.smtp.port", "465");
        propertiess.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        propertiess.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session sessionn = Session.getInstance(propertiess, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromm, "poovi123");
            }

        });

        try {
            MimeMessage message = new MimeMessage(sessionn);

            message.setFrom(new InternetAddress(fromm));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(too));

            message.setSubject("Booking details!");

            message.setText("Since another driver didn't show up in last minute , we have appointed you to the same customer , Please reach out the customer through "+customer_id);

            System.out.println("Sending message to the cab owner...");
            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
	      
	}
	private String get_tempdriver() {
		
		ArrayList<Userclass> temp_drivers_list=new ArrayList<>();
		
		for(Map.Entry<String, Userclass> entry: user_detail.entrySet()) {
			int flag1=0;
			if(entry.getValue().getRole().equals(usertype.Temporary_drivers)) {
				flag1=1;				
			}
			int flag2=1;
			for(int i=0;i<bookings.size();i++) {
				
				if(bookings.get(i).getDriver_name().equals(entry.getValue().getUsername())) {
					flag2=0;
					break;
				}
			}
			if(flag1==1 && flag2==1) {
				temp_drivers_list.add(entry.getValue());
			}
		}
		
		if(temp_drivers_list.size()==0) {
			return null;
		}
		else {
			System.out.printf("%30s%30s","Driver's name","Driver's age");
			System.out.println();
			System.out.printf("%30s","---------------------------------------------------------------------");
			System.out.println();
			for(int i=0;i<temp_drivers_list.size();i++) {
				System.out.printf("%30s%30s",temp_drivers_list.get(i).getUsername(),temp_drivers_list.get(i).getAge());
				System.out.println();
			}
		}
		System.out.println();
		System.out.println("Random drivers will be alloted");
		System.out.println();
		
		for (int i = 0; i < temp_drivers_list.size();) 
        {
           // generating the index using Math.random()
            int index = (int)(Math.random() * temp_drivers_list.size());
  
            System.out.println("Randomly choosed driver's name :"+ temp_drivers_list.get(index).getUsername());
            System.out.println();
            
            return temp_drivers_list.get(index).getUsername();
        }
		
		return null;
	}
	private void view_driverbookings() {
		ArrayList<Booking> view=new ArrayList<>();
		for(int i=0;i<bookings.size();i++) {
			if(bookings.get(i).getDriver_name()!=null && bookings.get(i).getDriver_name().equals(loggedin_user.getUsername())) {
				view.add(bookings.get(i));
			}
		}
		if(view.size()==0) {
			System.out.println("Yet ,you have not been choosed");
			System.out.println();
		}
		else {
			System.out.printf("%75s","----------BOOKING HISTORY---------");
			System.out.println();
			System.out.println();
			System.out.printf("%20s%20s%20s%40s%20s","Customer_name","Booked_from","Booked_to","Kilometers Travelled","Money paid");	
			System.out.println();
			System.out.println("-----------------------------------------------------------------------------------------------------------");
			System.out.println();
			for(int i=0;i<view.size();i++) {
				System.out.printf("%20s%20s%20s%40s%20s",view.get(i).getCust_name(),view.get(i).getFrom_date(),view.get(i).getTo_date(),view.get(i).getKilometer(),view.get(i).getCalculated_price());
				System.out.println();
			}
		}
		int flag=0;
		for(int i=0;i<cancellation.size();i++) {
			System.out.printf("%75s","----------CANCELLATION HISTORY---------");
			System.out.println();
			System.out.println();
			System.out.printf("%30s%40s%30s","Cancelled customer name","From_date","To_date");
			System.out.println("----------------------------------------------------------------------");
			System.out.println();
			if(cancellation.get(i).getDriver_name().equals(loggedin_user.getUsername())) {
				flag=1;
				System.out.printf("%30s%40s%30s",cancellation.get(i).getName_of_customer(),cancellation.get(i).getFrom_date(),cancellation.get(i).getTo_date());
				System.out.println();
			}
			
		}
		if(flag==0) {
			System.out.println();
			System.out.println("No records of cancellation");
			System.out.println();
		}
	}
	

	private void view_tempdriverbookings() {
		ArrayList<Booking> view=new ArrayList<>();
		for(int i=0;i<bookings.size();i++) {
			if(bookings.get(i).getDriver_name()!=null && bookings.get(i).getDriver_name().equals(loggedin_user.getUsername())) {
				view.add(bookings.get(i));
			}
		}
		if(view.size()==0) {
			System.out.println("Yet ,you have not been choosed");
			System.out.println();
		}
		else {
			System.out.printf("%75s","----------BOOKING HISTORY---------");
			System.out.println();
			System.out.println();
			System.out.printf("%20s%20s%20s%40s%20s","Customer_name","Booked_from","Booked_to","Kilometers Travelled","Money paid");	
			System.out.println();
			System.out.println("-----------------------------------------------------------------------------------------------------------");
			System.out.println();
			for(int i=0;i<view.size();i++) {
				System.out.printf("%20s%20s%20s%40s%20s",view.get(i).getCust_name(),view.get(i).getFrom_date(),view.get(i).getTo_date(),view.get(i).getKilometer(),view.get(i).getCalculated_price());
				System.out.println();
			}
		}		
	}
	

	private void view_my_feedbacks(usertype role) {
		if(role==usertype.Temporary_drivers) {
			int flag=0;
			for(Map.Entry<String, Userclass>  entry : user_detail.entrySet()) {
				if(entry.getValue().getRole().equals(usertype.Temporary_drivers) && entry.getValue().getFeedback()!=null) {
					flag=1;
					for(int i=0;i<entry.getValue().getFeedback().size();i++) {
						System.out.println(entry.getValue().getFeedback().get(i));
						System.out.println();
					}
					
				}
			
			}
			if(flag==0) {
				System.out.println("Driver not found or else feedback has not been added");
				System.out.println();
			}
		}
		else {
			int flag=0;
			for(Map.Entry<String, Userclass>  entry : user_detail.entrySet()) {
				if(entry.getValue().getRole().equals(usertype.Driver) && entry.getValue().getFeedback()!=null) {
					flag=1;
					for(int i=0;i<entry.getValue().getFeedback().size();i++) {
						System.out.println(entry.getValue().getFeedback().get(i));
						System.out.println();
					}
				}
			
			}
			if(flag==0) {
				System.out.println("Driver not found or else feedback has not been added");
				System.out.println();
			}
		}
		
		
	}
	

	private void booking_via_phonecall() {
		String customer=null;
		
		while(true) {
			try {
				System.out.println("Enter the username of the customer : ");
				customer=sc.nextLine();
				boolean cus=validate.isvalidUsername(customer);
				if(cus==true) {
					break;
				}
			}
			catch(Exception e) {
				System.out.println(e);
				continue;
			}
		}
		if(!user_detail.containsKey(customer) || user_detail.get(customer).getRole()!=usertype.Customer) {
			System.out.println("Please create an account and intimate us");
			System.out.println();
		}
		else
			booking(customer);
			
			try {
				ArrayList<Booking> temp=new ArrayList<>(filter);
				if(temp.size()!=0) {
					bookings.add(temp.get(0));
					System.out.println("Availability of the cab has been stopped for the booked dates");
					System.out.println();
				}
					
				filter.clear();
			}
			catch(Exception e) {
				System.out.println(e);
			}
	}
	

	private void addwallet() {
		System.out.println("Your wallet balance : "+loggedin_user.getWallet());
		System.out.println();
		System.out.println("Your leftout bonus amount : "+loggedin_user.getBonus_amount());
		System.out.println();
		String ans=null;
		while(true) {
			try {
				System.out.println("Do you need to add your wallet aount ? (yes / no) ");
				ans=sc.nextLine();
				boolean boolean_ans=validate.isvalidanswer(ans);
				if(boolean_ans==true)
					break;
			}
			catch(Exception e) {
				System.out.println(e);
				continue;
			}
		}
		if(ans.equalsIgnoreCase("yes")) {
			try {
				System.out.println("Enter wallet amount to be added : ");
				int amount=Integer.parseInt(sc.nextLine());
				loggedin_user.setWallet(loggedin_user.getWallet()+amount);
				System.out.println();
				System.out.println("Wallet-Balance : "+loggedin_user.getWallet());
				System.out.println();
			}
			catch(Exception e) {
				System.out.println("Enter an integer value");
			}
		}	
	}
	private void removecab_permanently() {
		String cab_number=null;
		while(true) {
			try {
				System.out.print("Enter the cab number to stop it permanently : ");
				cab_number=sc.nextLine();	
				break;
			}
			catch(Exception e) {
				System.out.println(e);
				continue;
			}
		} 
		if(!cab_details.containsKey(cab_number)) {
			System.out.println();
			System.out.println("Cab number is not found");
			System.out.println();
		}
		else if(!cab_details.containsKey(cab_number) && !loggedin_user.getUsername().equals(cab_details.get(cab_number).getOwner().getUsername())) {
			System.out.println();
			System.out.println("Cab number is not found with this owner name");
			System.out.println();
		}
		else {
			cab_details.remove(cab_number);
			System.out.println();
			System.out.println(cab_number+ " has been permanently stopped");
			System.out.println();
		}	
	}
	

	private void send_welcome_mail(String customer_id,usertype role,String username) { 
		
		String to =customer_id;

        String from = "19tucs144@skct.edu.in";

        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication(from, "poovi123");

            }

        });

        try {
            MimeMessage message = new MimeMessage(session);

            message.setFrom(new InternetAddress(from));

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject("Greetings from Pooviga Cabs!");
            if(role==usertype.Customer) {
                message.setText("Hi "+username+" ,\n\n"+"Thank you for registering in our website . Book your favourite cabs and enjoy your rides safely\n\nRegards,\nPooviga Cabs");
            }
            else if(role==usertype.Owner) {
                message.setText("Hi "+username+" ,\n\n"+"Thank you for registering in our website . Add your available cabs and make your profits\n\nRegards,\nPooviga Cabs");
            }
            else if(role==usertype.Driver || role==usertype.Temporary_drivers) {
                message.setText("Hi "+username+" ,\n\n"+"Thank you for registering in our website . Join our cab service for better income . We look forward to work with you\n\nRegards,\nPooviga Cabs");
            }
            System.out.println();
            System.out.println("Sending welcoming mail to the logged-in user...");
            // Send message
            Transport.send(message);
            System.out.println();
            System.out.println("Sent message successfully....");
            System.out.println();
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }
		
	}
	
	private void view_cab_feedback() {
		ArrayList<String> my_cab_list=new ArrayList<String>();
			
		for(Map.Entry<String, Cab> entry : cab_details.entrySet()) {
			if(entry.getValue().getOwner().getUsername().equals(loggedin_user.getUsername())) {
				my_cab_list.add(entry.getValue().getCar_number());
			}
		}
		if(my_cab_list.size()==0) {
			System.out.println("You have not added any cabs");
			System.out.println();
		}
		else {
			int flag=0;
			for(Map.Entry<String, ArrayList<String>> entry : Cab.cab_feedback.entrySet()) {
				if(my_cab_list.contains(entry.getKey())) {
					flag=1;
					for(int i=0;i<entry.getValue().size();i++) {
						System.out.println(entry.getKey()+" : "+entry.getValue().get(i));
						System.out.println();
					}
					
				}
			}
			if(flag==0) {
				System.out.println("No records of feedback for any of your cabs");
				System.out.println();
			}
		}
		
		
		
	}
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	String ch;
	private void handleOwner() {
		while(true) {
			System.out.println("Choose the options below");
			System.out.println();
			System.out.println("1) Add car details or view car details");
			System.out.println("2) Add/Edit availability dates");
			System.out.println("3) See booked and cancellation details");
			System.out.println("4) Stop availability");
			System.out.println("5) Stop availability of cab permanently");
			System.out.println("6) Check wallet balance");
			System.out.println("7) Edit personal details");
			System.out.println("8) View feedback of my cabs");
			System.out.println("9) Return advance");
			System.out.println("10) Exit / Logout");
			System.out.println();
			System.out.println("Choose your choice :");
			ch=sc.nextLine();
			if(validate.Validateten(ch)) {
				switch(Integer.parseInt(ch)) {

				case 1:
					while(true) {
						System.out.println("Do you wish to add car details ? (yes/no) ");
						String s=sc.nextLine();
						if(validate.isvalidanswer(s)) {
							if(s.equalsIgnoreCase("yes")) {
								addCar_Detail();
								System.out.println();
								break;
							}	
							else {
								System.out.println();
								System.out.println("Do you want to view your cabs ? (yes/no) ");
								String s2=sc.nextLine();
								if(validate.isvalidanswer(s2)) {
									if(s2.equalsIgnoreCase("yes"))
										viewcarDetails(loggedin_user.getUsername());
										break;
									
								}	
							}	
						}		
					}
					
					
					break;
				case 2:
					System.out.print("Enter your cabnumber : ");
					String cab_number=sc.nextLine();
					System.out.println();
					
					if(!cab_details.containsKey(cab_number)) {
						System.out.println("Cab not found , add cab to add availability");
						System.out.println();
					}
					else {
						boolean from_bool=true;
						boolean to_bool=true;
						String to_datestr=null;
						String from_datestr = null;
						while(true) {
							try {
								while(from_bool!=false) {
									System.out.println("Enter from_date ( yyyy-MM-dd ): "); 
									from_datestr=sc.nextLine();
									from_bool=validate.isvalidFutureDate(from_datestr,"yyyy-MM-dd");
									if(from_bool==true) {
										System.out.println("You cannot add availability for past dates");
										System.out.println();
									}
								}					
								while(to_bool!=false) {
									System.out.println("Enter to_date ( yyyy-MM-dd ): ");
									to_datestr=sc.nextLine(); 
									to_bool=validate.isvalidtodate(from_datestr,"yyyy-MM-dd",to_datestr);
									if(to_bool==true) {
										System.out.println("You cannot add availability for past dates and your to_date should be after from_date");
										System.out.println();
									}
								}
								LocalDate from_date = LocalDate.parse(from_datestr, formatter);
								LocalDate to_date = LocalDate.parse(to_datestr, formatter);
								
								boolean location_bool=false;
								String location=null;
								while(location_bool!=true) {
									System.out.println();
									System.out.print("Enter your location to add availability : ");
									location=sc.nextLine();
									location_bool=validate.isvalidString(location);
								}
								addAvailable(loggedin_user.getUsername(),cab_number,from_date,to_date,location);
								break;
							}
							catch(Exception e) {
								System.out.println(e+"---> Enter date in correct format (yyyy-MM-dd)");
								System.out.println();
								continue;
							}
						}
					}				
					break;
				case 3:
					owners_booking_details(loggedin_user.getUsername());
					System.out.println();
					break;

				case 4:
					System.out.print("Enter your cabnumber : ");
					String cab_number1=sc.nextLine();
					System.out.println();
					
					if(!cab_details.containsKey(cab_number1)) {
						System.out.println("Cab not found with the given cab number");
						System.out.println();
						break;
					}
					else {
						boolean fromm_bool=true;
						boolean too_bool=true;
		
						String too_datestr=null;
						String fromm_datestr = null;
						while(true) {
							try {
								while(fromm_bool!=false) {
									System.out.println("Enter from_date ( yyyy-MM-dd ): "); 
									fromm_datestr=sc.nextLine();
									fromm_bool=validate.isvalidFutureDate(fromm_datestr,"yyyy-MM-dd");
									if(fromm_bool==true) {
										System.out.println("You cannot add availability for past dates");
										System.out.println();
									}
								}								
								while(too_bool!=false) {
									System.out.println("Enter to_date ( yyyy-MM-dd ): ");
									too_datestr=sc.nextLine(); 
									too_bool=validate.isvalidtodate(fromm_datestr,"yyyy-MM-dd",too_datestr);
									if(too_bool==true) {
										System.out.println("You cannot add availability for past dates and your to_date should be after from_date");
										System.out.println();
									}
								}
								LocalDate fromm_date = LocalDate.parse(fromm_datestr, formatter);
								LocalDate too_date = LocalDate.parse(too_datestr, formatter);
								stopAvailable(cab_number1, fromm_date,too_date.plusDays(1),null);
								System.out.println("Availability of the cab has been stopped");
								System.out.println();
								break;
							}
							catch(Exception e) {
								System.out.println(e+"---> Enter date in correct format (yyyy-MM-dd)");
								System.out.println();
								continue;
							}
						}					
					}
					break;
				case 5:
					removecab_permanently();
					break;
				case 6:
					System.out.print("Wallet balance : ");
					System.out.println(loggedin_user.getWallet());
					System.out.println();
					break;
					
				case 7:	
					changedetails();					
					break;
				case 8:
					view_cab_feedback();
					break;
				case 9:
					int flag=0;
					for(int i=0;i<bookings.size();i++) {
						if(bookings.get(i).getOwner_name().equals(loggedin_user.getUsername())) {
							if(LocalDate.now().equals(bookings.get(i).getTo_date())) {
								flag=1;
								user_detail.get(bookings.get(i).getCust_name()).setWallet(user_detail.get(bookings.get(i).getCust_name()).getWallet()+cab_details.get(bookings.get(i).getCab_number2()).getAdvance());
								cab_details.get(bookings.get(i).getCab_number2()).setAdvance(0);
								
							}
						}
					}
					if(flag==1) {
						System.out.println("Your advance has been returned");
						System.out.println();
					}
					else {
						System.out.println("No customers are left to refund");
						System.out.println();
					}
					
					break;			
				case 10:
					System.out.println();
					System.out.print("---------Logging out from owner id---------");
					System.out.println();
					System.out.println();
					return;
				default:
					System.out.println("Invalid choosed option");
					System.out.println();

				}
			}
			else {
				continue;
			}
		}
	}


	private void handleCustomer() {
		
		while(true) {
			System.out.println();
			System.out.println("Choose the options below");
			System.out.println();
			System.out.println("1) Check the availabilities");
			System.out.println("2) Choose the type of pricing and book cabs");
			System.out.println("3) See booked  and cancelled transactions");
			System.out.println("4) Refer your friend");
			System.out.println("5) Cancel cabs");
			System.out.println("6) View or Add Wallet / View bonus amount");
			System.out.println("7) Edit details");
			System.out.println("8) Exit / Logout");
			System.out.println();
			System.out.println("Choose your choice :");
			ch=sc.nextLine();
			if(validate.ValidateChoicenum(ch)) {
				switch(Integer.parseInt(ch)) {

				case 1:
					try {
						
						boolean fro_bool=true;
						boolean t_bool=true;
		
						String t_datestr=null;
						String f_datestr = null;
						while(true) {
							try {
								while(fro_bool!=false) {
									System.out.print("Enter from_date ( yyyy-MM-dd ): "); 
									f_datestr=sc.nextLine();
									fro_bool=validate.isvalidFutureDate(f_datestr,"yyyy-MM-dd");
									if(fro_bool==true) {
										System.out.println("You cannot add availability for past dates");
										System.out.println();
									}
								}
								
								System.out.println();
								while(t_bool!=false) {
									System.out.print("Enter to_date ( yyyy-MM-dd ): ");
									t_datestr=sc.nextLine(); 
									t_bool=validate.isvalidtodate(f_datestr,"yyyy-MM-dd",t_datestr);
								
									if(t_bool==true) {
										System.out.println("You cannot add availability for past dates and your to_date should be after from_date");
										System.out.println();
									}
								}
								
								System.out.println();
								break;
							}
							catch(Exception e) {
								System.out.println(e+"---> Enter date in correct format (yyyy-MM-dd)");
								System.out.println();
								continue;
							}
						}
						
						LocalDate f_dates = LocalDate.parse(f_datestr, formatter);
						LocalDate t_dates = LocalDate.parse(t_datestr, formatter);
	
						
						LocalDate dateBefore = LocalDate.parse(LocalDate.now().format(formatter)); //from_date 
						LocalDate dateAfter = LocalDate.parse(f_dates.format(formatter));	
						//calculating number of days in between
						long noOfDaysBetween = ChronoUnit.DAYS.between(dateBefore, dateAfter);
						if(noOfDaysBetween>=(long)91) {
							System.out.println("You cannot book cabs with dates after 3 months");
							System.out.println();
						}
						else {
							boolean location_bool=false;
							String location=null;
							while(location_bool!=true) {
								System.out.print("Enter your location : ");
								location=sc.nextLine();
								location_bool=validate.isvalidString(location);
							}
							ArrayList<Cab> avai=findAvailable(f_dates,t_dates.plusDays(1), location);
							avai.clear();	
							System.out.println();
							
						}
					}
					catch(Exception e) {
						System.out.println(e);
					}
					break;
				case 2:			
					booking(loggedin_user.getUsername());
					try {
						ArrayList<Booking> temp=new ArrayList<>(filter);
						if(temp.size()!=0)
							bookings.add(temp.get(0));
						filter.clear();
					}
					catch(Exception e) {
						System.out.println(e);
					}
					break;
					
				case 3:
					seebooked_transactions(loggedin_user);
					break;
				case 4:
					System.out.println("You have got an amazing offer , If you refer our website to your friends , for each booking you'll get a bonus of 100 points");
					String ans=null;
					while(true) {
						try {
							System.out.println();
							System.out.println("Do you wish to recommed your friends ? (yes / no)");
							ans=sc.nextLine();
							boolean bool_ans=validate.isvalidanswer(ans);
							if(bool_ans==true) {
								break;
							}
							break;
						}
						catch(Exception e) {
							System.out.println(e);
							continue;
						}
					}
					if(ans.equalsIgnoreCase("yes")) {
						refer_mails();
					}
					else {
						System.out.println();
					}
					break;
				case 5:
					cancelCabs();
					break;					
				case 6:
					addwallet();
					break;
				case 7:
					changedetails();									
					break;
				case 8:
					System.out.println();
					System.out.print("Logging out from customer id");
					System.out.println();
					return;
				default:
					System.out.println("Invalid choosed option");
					System.out.println();
				}
			}
			else {
				continue;
			}
			

		}
	}

	private void handleDriver() {
				
		while(true) {
			System.out.println();
			System.out.println("Choose the options below");
			System.out.println();
			System.out.println("1) Cancel my booking");
			System.out.println("2) Show my booked or cancelled details");
			System.out.println("3) Wallet");
			System.out.println("4) Edit details");
			System.out.println("5) View feedbacks");
			System.out.println("6) Exit / Logout");
			System.out.println();
			System.out.println("Choose your choice :");
			ch=sc.nextLine();
			if(validate.valid(ch)) {
				switch(Integer.parseInt(ch)) {
					case 1:
						drivercancelling();
						break;
					case 2:
						view_driverbookings();
						break;
					case 3:			
						System.out.println("Wallet : "+loggedin_user.getWallet());
						break;					
					case 4:
						changedetails();
						break;
					case 5:
						view_my_feedbacks(loggedin_user.getRole());
						break;
					case 6:
						return;			
					default:
						System.out.println("Invalid choosed option");
						System.out.println();

				}
			}
			else {
				continue;
			}
			

		}
		
	}
	
	private void handleTemporarydriver() {
		
		while(true) {
			System.out.println();
			System.out.println("Choose the options below");
			System.out.println();
			System.out.println("1) Show my booked or cancelled details");
			System.out.println("2) Wallet");
			System.out.println("3) Edit details");
			System.out.println("4) View feedbacks");
			System.out.println("5) Exit / Logout");
			System.out.println();
			System.out.println("Choose your choice :");
			ch=sc.nextLine();
			if(validate.validstring(ch)) {
				switch(Integer.parseInt(ch)) {					
					case 1:
						view_tempdriverbookings();
						break;
					case 2:			
						System.out.println("Wallet : "+loggedin_user.getWallet());
						break;					
					case 3:
						changedetails();
						break;
					case 4:
						view_my_feedbacks(loggedin_user.getRole());
						break;
					case 5:
						return;			
					default:
						System.out.println("Invalid choosed option");
						System.out.println();

				}
			}
			else {
				continue;
			}
			

		}
	}

	private void handleSignIn(String username,String password){
		
		usertype type=SignIn(username,password);
		if(type==null) {
			return;
		}
		else {
			if(type==usertype.Owner) {
				handleOwner();
			}
			else if(type==usertype.Customer){
				handleCustomer();
			}
			else if(type==usertype.Driver){
				handleDriver();
			}
			else {
				handleTemporarydriver();
			}
		}
								
	}
	
	private void handleSignUp() {
		
		boolean username_bool=false;
		String username=null;
		String name=null;
		String password=null;
		int age = 0;
		int wallet;
		String mail_id=null;
		System.out.println("Continue with Sigup process....");
		boolean role_bool=false;
		String role=null;
		while(role_bool!=true) {
			System.out.println();
			System.out.println("Enter your role "+"\n"+"1) Owner"+"\n"+"2) Customer"+"\n"+"3) Driver"+"\n"+"4) Temporary drivers");	
			role=sc.nextLine();
			role_bool=validate.validateChoice(role);
		}
		
		usertype type=null;
		while(type==null) {
			if(Integer.parseInt(role)==1) {
				type=usertype.Owner;
				break;
			}
			else if(Integer.parseInt(role)==2){
				type=usertype.Customer;
				break;
			}
			else if(Integer.parseInt(role)==3){
				type=usertype.Driver;
				break;
			}
			else if(Integer.parseInt(role)==4) {
				type=usertype.Temporary_drivers;
			}
			else {
				type=null;
				System.out.println("Choose 1 or 2 or 3 or 4");
			}
		}
		
		while(username_bool!=true) {
			System.out.println();
			System.out.print("Enter your username : ");
			username=sc.nextLine();
			username_bool=validate.isvalidUsername(username);
		}
		boolean name_bool=false;

		while(name_bool!=true) {
			System.out.println();
			System.out.print("Enter your name : ");
			name=sc.nextLine();
			name_bool=validate.isvalidName(name);
		}
		boolean password_bool=false;

		while(password_bool!=true) {
			System.out.println();
			System.out.print("Enter your password : ");
			password=sc.nextLine();
			password_bool=validate.isvalidPassword(password,username);
		}
		System.out.println();
		
		boolean age_bool=false;

		while(age_bool!=true) {
			try {
				System.out.print("Enter your age : ");
				age=Integer.parseInt(sc.nextLine());
				age_bool=validate.isvalidage(age);
			}
			catch(Exception e) {
				System.out.println();
				System.out.println("Enter integer value");
				System.out.println();
				age_bool=false;
			}
			
		}
		System.out.println();
		
		while(true) {
			try {			
				System.out.print("Enter wallet amount : ");
				wallet=Integer.parseInt(sc.nextLine());
				if(wallet<10000 && Integer.parseInt(role)==2) {
					System.out.println();
					System.out.println("Your wallet should contain minimum of 10000 rupees");
					System.out.println();
					continue;
				}
				else if(wallet<0) {
					System.out.println("Your cannot have negative amount");
					System.out.println();
					continue;
				}
				System.out.println();
				break;
			}
			catch(Exception e) {
				System.out.println(e+"---> Enter integer value");
				System.out.println();
				continue;
			}
		}	

		
		boolean mail_bool=false;
		
		
		while(mail_bool!=true) {
			System.out.print("Enter your mail id : ");
			mail_id=sc.nextLine();
			mail_bool=validate.isvalidmail(mail_id);
		}
		
		int driver_price_for_a_day=0;
		if(Integer.parseInt(role)==3) {
			
			while(true) {
				try {
					System.out.println();
					System.out.println("Enter your price for a day : ");
					driver_price_for_a_day=Integer.parseInt(sc.nextLine());
					boolean price=validate.isvalidprice(driver_price_for_a_day);
					if(price!=true) {
						continue;
					}
					else {
						break;
					}
				}
				catch(Exception e) {
					System.out.println("Enter integer value");
					continue;
				}
				
			}
			
			
		}
		int bonus_amount=5;
		boolean signup=SignUp(name,username,password,type,wallet,mail_id,age,driver_price_for_a_day,bonus_amount);
		
		if(Integer.parseInt(role)==2) {
			String tell_=null;
			while(true) {
				try {
					System.out.println("Did you reach us through any referral ? ( yes / no )");
					tell_=sc.nextLine();
					boolean boolean_tell=validate.isvalidanswer(tell_);
					if(boolean_tell==true) {
						break;
					}	
				}
				catch(Exception e) {
					System.out.println(e);
					continue;
				}
			}
			String referred_mail=null;
			if(tell_.equalsIgnoreCase("yes")) {
				while(true) {
					try {
						System.out.print("Enter mail id of the person who referred you : ");
						referred_mail=sc.nextLine();
						boolean mail=validate.isvalidmail(referred_mail);
						if(mail==true) {
							break;
						}
					}
					catch(Exception e) {
						System.out.println();
						continue;
					}
				}
				String friends_username=null;
				while(true) {
					try {
						System.out.println("Enter your friends username : ");
						friends_username=sc.nextLine();
						boolean frnd_name=validate.isvalidUsername(friends_username);
						if(frnd_name==true) {
							break;
						}
					}catch(Exception e) {
						System.out.println(e);
						continue;
					}
				}
				
				if(references.containsKey(referred_mail) && references.get(referred_mail).contains(mail_id)) {
					
					if(!user_detail.get(friends_username).getMail_id().equals(referred_mail)) {
						System.out.println("Username and mail id are different");
					}
					else {
						user_detail.get(friends_username).setBonus_amount(user_detail.get(friends_username).getBonus_amount()+100);
						user_detail.get(username).setBonus_amount(user_detail.get(username).getBonus_amount()+100);
						System.out.println("Bonus points of 100 is added to you and your friend.");
						System.out.println();
					}	
				}
				else if(!references.containsKey(referred_mail)){
					System.out.println("Sorry , We dont find your friends mail_id");
					System.out.println();
				}
				else if(!references.get(referred_mail).contains(mail_id)) {
					System.out.println("Sorry , your friend has not referred you . Check again");
					System.out.println();
				}
				
			}
		}
		
		if(signup==true) {
			System.out.println();
			System.out.println("Thanks for registering !!!");
			handleSignIn(username,password);
		}
			
		
	}

	private void handleAppStart(){
		while(true) {			
			String decision;
			while(true) {
				System.out.println("Choose your option below :");
				System.out.println();
				System.out.println("1) About us"+"\n"+"2) SignIn"+"\n"+"3) SignUp"+"\n"+"4) Booking via phone call"+"\n"+"5) View details"+"\n"+"6) Feebacks"+"\n"+"7) Contact us"+"\n"+"8) Exit");
				System.out.println();
				System.out.print("Enter value : ");
				decision=sc.nextLine();
				if(validate.ValidateChoicenum(decision)) {
					System.out.println();
					switch(Integer.parseInt(decision)) {
					case 1:
						Cab.cab_App_details();
						break;
					case 2:
						System.out.print("Enter username : ");
						String username=sc.nextLine();
						if(!user_detail.containsKey(username)) {
							System.out.println("----------------------------------------------------------------");
							System.out.println("Username not found");
							System.out.println("----------------------------------------------------------------");
								
						}else {
							System.out.println();
							System.out.print("Enter password : ");
							String password=sc.nextLine();
							handleSignIn(username,password);
						}
						
						break;

					case 3:
						handleSignUp();	
						break;
					case 4:
						
						booking_via_phonecall();
						try {
							ArrayList<Booking> temp=new ArrayList<>(filter);
							if(temp.size()!=0)
								bookings.add(temp.get(0));
							filter.clear();
						}
						catch(Exception e) {
							System.out.println(e);
						}
						break;
					case 5:
						view_application_details();
						break;
					case 6:
						Cab.feedback();
						break;
					case 7:
						Cab.Contact_us();
						break;
					case 8:
						System.out.println("Exiting the application");
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
	}

	public static void main(String args[]){
		System.out.println();
		System.out.println("----------------Welcome to my application---------------");
		System.out.println();
		CabApplication obj=new CabApplication();
		try {
			Userclass t1=new Userclass("Tempdriver","TempDriver1","Tempdriver1@123",usertype.Temporary_drivers,0,"tempdriver1@gmail.com",23,200);
			user_detail.put("TempDriver1",t1);
			Userclass t2=new Userclass("Tempdriver2","TempDriver2","Tempdriver2@123",usertype.Temporary_drivers,0,"tempdriver2@gmail.com",26,300);
			user_detail.put("TempDriver2",t2);
			Userclass t3=new Userclass("Driver1","Driver1","driver1@123",usertype.Driver,0,"driver1@gmail.com",22,200);
			user_detail.put("Driver1",t3);
			obj.handleAppStart();
		}
		catch(Exception e) {
			System.out.println();
			System.out.println("Restarting the app");
			obj.handleAppStart();
		}
		
	}

}
