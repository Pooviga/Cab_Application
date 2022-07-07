package Cab_Booking;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


class Custom_Exception extends Exception{	
	private static final long serialVersionUID = 1L;

	public Custom_Exception(String str) {	//Overriding the exceptions in Exception class
		super(str);
	}
}

class Validate implements Serializable{

	private static final long serialVersionUID = 1L;

	protected boolean validateChoice(String choice) {
		try {
			boolean x= choice.matches("[0-9]+"); //with leading zeros
			if(x==false || (!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("4"))) {
				System.out.println();
				throw new Custom_Exception("Choose numbers from 1,2,3 and 4");	
				//throwing custom exception		
			}
			else {
				return true;
			}
		}
		catch(Custom_Exception e) {
			System.out.println(e);
			return false;
		}
		
	}

	protected boolean isvalidUsername(String string){
		try {
			boolean x= string.matches("^[a-zA-Z][a-zA-Z0-9_]{6,20}$"); 
			if(x==false) {
				System.out.println();
				System.out.println("Type correct username");
				System.out.println();
				throw new Custom_Exception("The length of username or name should range from 7 to 20,can only have underscore,first charcater should be alphabetic character");	
				
			}
			else {
				return true;
			}
		}
		catch(Custom_Exception e) {
			System.out.println(e);
			return false;
		}
	}
	protected boolean isvalidName(String string){
		try {
			boolean x= string.matches("[a-zA-Z]+"); 
			if(x==false) {
				System.out.println();
				System.out.println("Type correct username");
				System.out.println();
				throw new Custom_Exception("Enter valid name");	
				
			}
			else {
				return true;
			}
		}
		catch(Custom_Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	protected boolean isvalidPassword(String password,String username){
		try {
			boolean x= password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{3,15}$");
			if(x==false) {
				System.out.println();
				System.out.println("Type correct password");
				System.out.println();
				throw new Custom_Exception("Password must contain (one upper and lower case alphabet and one special character and acnnot contain 3 or more consecutive characters from your username)");	
				//throwing custom exception
				
			}
			else {
				for(int i=0;(i+2)<username.length();i++) {
					if(password.indexOf(username.substring(i,i+2))!=-1) {
						System.out.println();
						System.out.println("Cannot contain 3 or more consecutive characters from your full your username");
			            return false;
					}   	  
				} 
			    return true;
			}
		}
		catch(Custom_Exception e) {
			System.out.println(e);
			return false;
		}
		
	}
	
	protected boolean isvalidDate(String date){
		try {
			boolean x= date.matches("^\\d{4}\\-(0?[1-9]|1[012])\\-(0?[1-9]|[12][0-9]|3[01])$"); //with leading zeros

			if(x==false) {
				System.out.println();
				System.out.println("Type correct date");
				System.out.println();
				throw new Custom_Exception("Date should be in format yyyy-mm-dd");	
				//throwing custom exception		
			}
			else {
				return true;
			}
		}
		catch(Custom_Exception e) {
			System.out.println(e);
			return false;
		}
		
	}

	protected boolean isvalidString(String location) {
		try {
			boolean x= location.matches("[a-zA-Z]+"); //with leading zeros
			if(x==false) {
				System.out.println();
				throw new Custom_Exception("Location cannot be in numbers");	
				//throwing custom exception		
			}
			else {
				return true;
			}
		}
		catch(Custom_Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	protected boolean validateDecision(String decision) {
		try {
			boolean x= decision.matches("[0-9]+"); //with leading zeros
			if(x==false || (!decision.equals("1") && !decision.equals("2") && !decision.equals("3")) ) {
				System.out.println();
				throw new Custom_Exception("Choose numbers from 1,2 and 3");	
				//throwing custom exception		
			}
			else {
				return true;
			}
		}
		catch(Custom_Exception e) {
			System.out.println(e);
			return false;
		}
		
	}
	
	protected boolean validatechoice(String decision) {
		try {
			boolean x= decision.matches("[0-9]+"); //with leading zeros
			if(x==false || (!decision.equals("1") && !decision.equals("2") && !decision.equals("3") && !decision.equals("4") && !decision.equals("5") && !decision.equals("6") && !decision.equals("7"))) {
				System.out.println();
				throw new Custom_Exception("Choose numbers from 1 ,2 ,3 ,4 ,5 ,6 and 7");	
				//throwing custom exception		
			}
			else {
				return true;
			}
		}
		catch(Custom_Exception e) {
			System.out.println(e);
			return false;
		}
		
	}
	
	protected boolean ValidateChoicenum(String decision) {
		try {
			boolean x= decision.matches("[0-9]+"); //with leading zeros
			if(x==false || (!decision.equals("1") && !decision.equals("2") && !decision.equals("3") && !decision.equals("4") && !decision.equals("5") && !decision.equals("6") && !decision.equals("7") && !decision.equals("8"))) {
				System.out.println();
				throw new Custom_Exception("Choose numbers from 1 ,2 ,3 ,4 ,5 ,6 ,7 or 8");	
				//throwing custom exception		
			}
			else {
				return true;
			}
		}
		catch(Custom_Exception e) {
			System.out.println(e);
			return false;
		}
		
	}
	
	protected boolean Validatenine(String decision) {
		try {
			boolean x= decision.matches("[0-9]+"); //with leading zeros
			if(x==false || (!decision.equals("1") && !decision.equals("2") && !decision.equals("3") && !decision.equals("4") && !decision.equals("5") && !decision.equals("6") && !decision.equals("7") && !decision.equals("8") && !decision.equals("9"))) {
				System.out.println();
				throw new Custom_Exception("Choose numbers from 1 ,2 ,3 ,4 ,5 ,6 ,7 ,8 or 9");	
				//throwing custom exception		
			}
			else {
				return true;
			}
		}
		catch(Custom_Exception e) {
			System.out.println(e);
			return false;
		}
		
	}
	protected boolean Validateten(String decision) {
		try {
			boolean x= decision.matches("[0-9]+"); //with leading zeros
			if(x==false || (!decision.equals("1") && !decision.equals("2") && !decision.equals("3") && !decision.equals("4") && !decision.equals("5") && !decision.equals("6") && !decision.equals("7") && !decision.equals("8") && !decision.equals("9") && !decision.equals("10"))) {
				System.out.println();
				throw new Custom_Exception("Choose numbers from 1 ,2 ,3 ,4 ,5 ,6 ,7 ,8 ,9 or 10");	
				//throwing custom exception		
			}
			else {
				return true;
			}
		}
		catch(Custom_Exception e) {
			System.out.println(e);
			return false;
		}
		
	}
	
	protected boolean isvalidtodate(String from_date,String string,String to_date) {
		LocalDate localDate = LocalDate.now(ZoneId.systemDefault());

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(string);
		LocalDate inputDate = LocalDate.parse(from_date, dtf);

		boolean f1= inputDate.isBefore(localDate);
		
		LocalDate inputDate_to = LocalDate.parse(to_date, dtf);
		
		boolean isAfter=inputDate_to.isAfter(inputDate);
		
		boolean isEqual=inputDate_to.isEqual(inputDate);
		
		if(f1==false && (isAfter==true || isEqual==true)) {
			return false;
		}
		
		return true;
	}

	protected boolean isvalidFutureDate(String from_datestr, String string) {
		LocalDate localDate = LocalDate.now(ZoneId.systemDefault());

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(string);
		LocalDate inputDate = LocalDate.parse(from_datestr, dtf);

		return inputDate.isBefore(localDate);
	}
	
	
	protected boolean isvalidanswer(String role) {
		try {
			boolean x= role.matches("[a-zA-Z]+"); 
			if(x==false || (!role.equalsIgnoreCase("Yes") && !role.equalsIgnoreCase("No")) ) {
				System.out.println();
				throw new Custom_Exception("Type Yes or No");	
				//throwing custom exception		
			}
			else {
				return true;
			}
		}
		catch(Custom_Exception e) {
			System.out.println(e);
			return false;
		}
	}

	protected boolean valid(String choice) {
		try {
			boolean x= choice.matches("[0-9]+"); //with leading zeros
			if(x==false || (!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("4") && !choice.equals("5") && !choice.equals("6"))) {
				System.out.println();
				throw new Custom_Exception("Choose numbers from 1 ,2 ,3 ,4 ,5 and 6");	
			}
			else {
				return true;
			}
		}
		catch(Custom_Exception e) {
			System.out.println(e);
			return false;
		}
	}
	protected boolean validstring(String choice) {
		try {
			boolean x= choice.matches("[0-9]+"); //with leading zeros
			if(x==false || (!choice.equals("1") && !choice.equals("2") && !choice.equals("3") && !choice.equals("4") && !choice.equals("5"))) {
				System.out.println();
				throw new Custom_Exception("Choose numbers from 1 ,2 ,3 ,4 or 5");	
			}
			else {
				return true;
			}
		}
		catch(Custom_Exception e) {
			System.out.println(e);
			return false;
		}
	}

	protected boolean isvalidmail(String mail_id) {
		try {
			boolean x= mail_id.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
					+"[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"); 
			if(x==false) {
				System.out.println();
				throw new Custom_Exception("Enter valid email id");	
				//throwing custom exception		
			}
			else {
				return true;
			}
		}
		catch(Custom_Exception e) {
			System.out.println(e);
			return false;
		}
	}

	protected boolean isvalidage(int age) {
		try {
			
			if(age<18 || age>100) {
				System.out.println();
				throw new Custom_Exception("Enter your correct age, Your age should be greater than 18 and less than 100");	
				//throwing custom exception		
			}
			else {
				return true;
			}
		}
		catch(Custom_Exception e) {
			System.out.println(e);
			return false;
		}
	}

	protected boolean isvalidprice(int driver_price_for_a_day) {
		try {
			
			if(driver_price_for_a_day<0) {
				System.out.println();
				throw new Custom_Exception("Enter positive values");	
				//throwing custom exception		
			}
			else {
				return true;
			}
		}
		catch(Custom_Exception e) {
			System.out.println(e);
			return false;
		}
	}

}
